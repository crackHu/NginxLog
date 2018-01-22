package com.hustack.nl.scheduled.job;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import com.hustack.nl.domain.General;
import com.hustack.nl.domain.Markdown;
import com.hustack.nl.domain.Report;
import com.hustack.nl.util.JSONUtils;

@Component
public class NoticeJob extends BaseJob {

	@Value("${hustack.notice.cron}")
	private String cron;
	
	@Value("hustack.notice.webhook")
	private String webhook;
	
	private final boolean dev = false;

	@Override
	public void initSpringProperties() {
		HuStackProperties properties = super.getProperties();
		Notice notice = properties.getNotice();
		cron = notice.getCron();
		webhook = notice.getWebhook();
	}

	@Override
	public void exec(JobExecutionContext context) throws JobExecutionException {
	
		long begin = System.currentTimeMillis();
		
		Path reportJsonPath = dev ? Paths.get("index.json") : super.getReportJsonPath();
		
		Report result = parseJson(reportJsonPath);
		General general = result.getGeneral();
		if (general == null) {
			return;
		}
		
		Integer totalRequests = general.getTotalRequests();
		Integer validRequests = general.getValidRequests();
		Integer failedRequests = general.getFailedRequests();
		Integer uniqueVisitors = general.getUniqueVisitors();
		Long logSize = general.getLogSize();
		Long bandwidth = general.getBandwidth();
		String logDate = dev ? "2018-01-21" : super.getLogDate();
		String detail = "http://120.78.94.189/report/" + logDate;
		
		DDRobot ddRobot = new DDRobot();
		Markdown markdown = new Markdown();
		markdown.setTitle("昨日统计");
		markdown.setText("#### **昨日统计**\n" +
                 String.format("> Total Requests (总请求)：%s\n\n", totalRequests) +
                 String.format("> Valid Requests (有效的请求)：%s\n\n", validRequests) + 
                 String.format("> Failed Requests (失败的请求)：%s\n\n", failedRequests) +
                 String.format("> Unique Visitors (独立访客)：%s\n\n", uniqueVisitors) +
                 String.format("> Log Size (日志大小)：%s\n\n", getPrintSize(logSize)) +
                 String.format("> Bandwidth (带宽)：%s\n", getPrintSize(bandwidth)) +
				 "***\n" +
				 String.format("###### *%s [详情](%s)  Cost: %s ms* \n", logDate, detail, (System.currentTimeMillis() - begin)));
		
		ddRobot.setMarkdown(markdown);
		
		super.logger.info("ddRobot send logDate report: {}", ddRobot);
		
		RestTemplate restTemplate = new RestTemplate();
		if (dev) {
			webhook = "https://oapi.dingtalk.com/robot/send?access_token=b271dcd03cceddca8509a3d0efd29b7a88bf86e05f63daa06dd35feb44a24b07";
		}
		ResponseEntity<String> entity = restTemplate.postForEntity(webhook, ddRobot, String.class);
		String body = entity.getBody();
		
		super.logger.info("ddRobot receive response: {}", body);
	}

	public static void main(String[] args) {
		try {
			new NoticeJob().execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getPrintSize(Long b) {
		if (b == null) {
			return null;
		}
		Double bit = new Double(b);
		if (bit < 1024) {
			return String.format("%.2f B", bit);
		} else {
			bit = bit / 1024;
		}
		if (bit < 1024) {
			return String.format("%.2f KB", bit);
		} else {
			bit = bit / 1024;
		}
		if (bit < 1024) {
			return String.format("%.2f MiB", bit);
		} else {
			bit = bit / 1024;
			return String.format("%.2f GiB", bit);
		}
	}
	
	public Report parseJson(Path jsonFile) {
		Report report = null;
		String json = null;
		try {
			File file = jsonFile.toFile();
			json = Files.asCharSource(file, StandardCharsets.UTF_8).read();
			super.logger.info("parseJson: {}", json);
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
