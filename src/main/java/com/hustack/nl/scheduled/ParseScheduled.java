package com.hustack.nl.scheduled;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.io.Files;
import com.hustack.nl.domain.DDRobot;
import com.hustack.nl.domain.General;
import com.hustack.nl.domain.Markdown;
import com.hustack.nl.domain.Report;
import com.hustack.nl.util.JSONUtils;

@Component
public class ParseScheduled {
	
	private final static Logger log = LoggerFactory.getLogger(ParseScheduled.class);
	
	private final static Runtime runtime = Runtime.getRuntime();
	
	// goaccess -a -d -f /logs/access.2018-01-19.log --time-format='%H:%M:%S' --date-format='%d/%b/%Y' --log-format='%h %^ %^ [%d:%t %^] "%r" %s %b %^"%R" "%u" "%^" %T' -o /html/index.html
	private final String CMD_PARSE = "goaccess -a -d -f %s --time-format='%s' --date-format='%s' --log-format='%s' -o %s";
	
	@Value("${hustack.parse.timeFormat}")
	private String timeFormat;
	
	@Value("${hustack.parse.dateFormat}")
	private String dateFormat;
	
	@Value("${hustack.parse.logFormat}")
	private String logFormat;

	@Value("${hustack.log.savePath}")
	private String logSavePath;
	
	@Value("${hustack.parse.savePath}")
	private String parseSavePath;
	
	@Autowired
	private LogScheduled logScheduled;

	public String parseHtml() {
		
		String logName = logScheduled.getLogName();
		String logNameWithoutExt = Files.getNameWithoutExtension(logName);
		Path parseLog = Paths.get(logSavePath, logName);
		Path reportHtml = Paths.get(parseSavePath, logNameWithoutExt + ".html");
		Path reportJson = Paths.get(parseSavePath, logNameWithoutExt + ".json");
		
		// parse html
		String parseHtmlCMD = String.format(CMD_PARSE, parseLog, timeFormat, dateFormat, logFormat, reportHtml);
		exec(new String[] { "sh", "-c", parseHtmlCMD });

		// parse json
		String parseJsonCMD = String.format(CMD_PARSE, parseLog, timeFormat, dateFormat, logFormat, reportJson);
		exec(new String[] { "sh", "-c", parseJsonCMD });
		
		return parseJson(reportJson).toString();
	}
	
	public Report parseJson(Path jsonFile) {
		String json = null;
		try {
			File file = jsonFile.toFile();
			json = Files.asCharSource(file, StandardCharsets.UTF_8).read();
			log.info("parseJson: {}", json);
		} catch (Exception e) {
			log.error("Oops, parseJson has error: {}", e.getMessage(), e);
		}
		Report report = null;
		try {
			report = JSONUtils.json2pojo(json, Report.class);
		} catch (Exception e) {
			report = new Report();
			log.error("Oops, parseJson has error: {}", e.getMessage(), e);
		}
		return report;
	}

	public static void main(String[] args) {
		Path path = Paths.get("H:", "crack", "Desktop", "index.json");
		System.out.println(path);
		long begin = System.currentTimeMillis();
		Report result = new ParseScheduled().parseJson(path);
		General general = result.getGeneral();
		long end = System.currentTimeMillis();
		
		DDRobot ddRobot = new DDRobot();
		Markdown markdown = new Markdown();
		markdown.setTitle("昨日统计");
		markdown.setText("#### 昨日统计\n" +
                 String.format("total requests (总请求)：%s\n\n", general.getTotalRequests()) +
                 String.format("valid requests (有效的请求)：%s\n\n", general.getTotalRequests()) +
                 String.format("failed requests (失败的请求)：%s\n\n", general.getFailedRequests()) +
                 String.format("unique visitors (独立访客)：%s\n\n", general.getValidRequests()) +
                 String.format("log size (日志大小)：%s\n\n", getPrintSize(general.getLogSize())) +
                 String.format("bandwidth (带宽)：%s\n", getPrintSize(general.getBandwidth())) +
				String.format("###### %s [详情](%s) cost: %s ms \n", "2018-01-19",
						"http://120.78.94.189/report/2018-01-19", (end - begin)));
		ddRobot.setMarkdown(markdown);
		
		log.info("ddRobot send: {}", ddRobot);
		
		RestTemplate restTemplate = new RestTemplate();
		final String PUSH_URL = "https://oapi.dingtalk.com/robot/send?access_token=b271dcd03cceddca8509a3d0efd29b7a88bf86e05f63daa06dd35feb44a24b07";
		ResponseEntity<String> entity = restTemplate.postForEntity(PUSH_URL, ddRobot, String.class);
		String body = entity.getBody();
		log.info("ddRobot send: {}", body);
	}
	
	
	private static String getPrintSize(Long b) {
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

	public String exec(String... command) {
		String response = "no exec response.";
		Process exec;
		try {
			exec = runtime.exec(command);
			InputStream inputStream = exec.getInputStream();
			IOUtils.toString(inputStream, Charset.forName("GBK"));
			log.info("execute command: {}, response: {}", command, response);
		} catch (IOException e) {
			response = e.getMessage();
			log.error("Oops, execute has error: {}", e.getMessage(), e);
		}
		return response;
	}
}
