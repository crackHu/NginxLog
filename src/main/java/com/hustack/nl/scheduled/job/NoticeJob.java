package com.hustack.nl.scheduled.job;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.io.Files;
import com.hustack.nl.configure.HuStackProperties;
import com.hustack.nl.configure.HuStackProperties.Notice;
import com.hustack.nl.domain.DDRobot;
import com.hustack.nl.domain.Markdown;
import com.hustack.nl.domain.Report;
import com.hustack.nl.domain.ReportContent;
import com.hustack.nl.util.JSONUtils;
import com.hustack.nl.util.RedisUtils;
import com.hustack.nl.util.SpringUtils;

@Component
public class NoticeJob extends BaseJob {

	@Value("${hustack.notice.cron}")
	private String cron;
	
	@Value("hustack.notice.webhook")
	private String webhook;
	
	private String noticeReqKey;
	
	private String noticeResKey;
	
	private RedisUtils redisUtils;
	
	private final boolean dev = false;
	
	private final long DEFAULT_EXPIRATION = 60 * 60 * 24 * 32;

	@Override
	public void initSpringProperties() {
		HuStackProperties properties = super.getProperties();
		
		String date = super.getLogDate();
		noticeReqKey = properties.getNoticeReqKey(date);
		noticeResKey = properties.getNoticeResKey(date);
		
		Notice notice = properties.getNotice();
		cron = notice.getCron();
		
		redisUtils = SpringUtils.getBean(RedisUtils.class);
		webhook = (String) redisUtils.get("webhook");
		if (StringUtils.isBlank(webhook)) {
			webhook = notice.getWebhook();
		}
	}

	@Override
	public void exec(JobExecutionContext context) throws JobExecutionException {
	
		long begin = System.currentTimeMillis();
		
		Report result = getReport();
		Report lastReport = getReport(-2);
		ReportContent reportContent = new ReportContent(result, lastReport);
		
		String totalRequests = reportContent.getTotalRequests();
		String validRequests = reportContent.getValidRequests();
		String failedRequests = reportContent.getFailedRequests();
		String uniqueVisitors = reportContent.getUniqueVisitors();
		String logSize = reportContent.getLogSize();
		String bandwidth = reportContent.getBandwidth();
		String logDate = dev ? "2018-01-21" : super.getLogDate();
		String detail = "http://120.78.94.189/report/" + logDate;
		
		DDRobot ddRobot = new DDRobot();
		Markdown markdown = new Markdown();
		markdown.setTitle("昨日统计");
		markdown.setText("#### **昨日统计(总)**\n" +
                 String.format("> Total Requests: %s\n\n", totalRequests) + // (总请求)：
                 String.format("> Valid Requests: %s\n\n", validRequests) + // (有效的请求)：
                 String.format("> Failed Requests: %s\n\n", failedRequests) + // (失败的请求)：
                 String.format("> Unique Visitors: %s\n\n", uniqueVisitors) + // (独立访客)：
                 String.format("> Log Size: %s\n\n", logSize) + // (日志大小)：
                 String.format("> Bandwidth: %s\n", bandwidth) + // (带宽)：
				 "***\n" +
				 String.format("###### *%s [详情](%s)  Cost: %s ms* \n", logDate, detail, (System.currentTimeMillis() - begin)));
		
		ddRobot.setMarkdown(markdown);
		notice(ddRobot);
	}

	private void notice(DDRobot request) {
		redisUtils.set(noticeReqKey, request, DEFAULT_EXPIRATION);
		super.logger.info("ddRobot send logDate report: {}", request);
		
		String response = null;
		ResponseEntity<String> entity = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (dev) {
				webhook = "https://oapi.dingtalk.com/robot/send?access_token=b271dcd03cceddca8509a3d0efd29b7a88bf86e05f63daa06dd35feb44a24b07";
			}
			entity = restTemplate.postForEntity(webhook, request, String.class);
			response = entity.getBody();
		} catch (Exception e) {
			response = e.getMessage();
			super.logger.error("Oops, notice error: {}", e.getMessage(), e);
		}

		redisUtils.set(noticeResKey, response, DEFAULT_EXPIRATION);
		super.logger.info("ddRobot receive response: {}", response);
	}

	public static void main(String[] args) {
		try {
			new NoticeJob().exec(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Report getReport() {
		return getReport(-1);
	}
	
	private Report getReport(int amount) {
		String logDate = super.getLogDate(amount);
		String reportKey = properties.getReportKey(logDate);
		boolean exists = redisUtils.exists(reportKey);
		if (exists) {
			return (Report) redisUtils.get(reportKey);
		}
		
		String logName = super.getLogName(amount);
		Path reportJsonPath = dev ? Paths.get("index" + amount + ".json") : super.getReportJsonPath(logName);
		Report report = parseJson(reportJsonPath);
		
		redisUtils.set(reportKey, report, DEFAULT_EXPIRATION);
		return report;
	}
	
	public Report parseJson(Path jsonFile) {
		Report report = null;
		String json = null;
		try {
			File file = jsonFile.toFile();
			json = Files.asCharSource(file, StandardCharsets.UTF_8).read();
		} catch (Exception e) {
			super.logger.error("Oops, parseJson has error: {}", e.getMessage(), e);
			return new Report();
		}
		try {
			report = JSONUtils.json2pojo(json, Report.class);
		} catch (Exception e) {
			report = new Report();
			super.logger.error("Oops, parseJson has error: {}", e.getMessage(), e);
		}
		return report;
	}

	public String getCron() {
		return cron;
	}

	@Override
	public String getCronExpression() {
		return cron;
	}
}
