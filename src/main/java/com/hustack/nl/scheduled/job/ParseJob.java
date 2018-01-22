package com.hustack.nl.scheduled.job;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hustack.nl.configure.HuStackProperties;
import com.hustack.nl.configure.HuStackProperties.Parse;

@Component
public class ParseJob extends BaseJob {

	private final static Runtime runtime = Runtime.getRuntime();
	
	// goaccess -a -d -f /logs/access.2018-01-19.log --time-format='%H:%M:%S' --date-format='%d/%b/%Y' --log-format='%h %^ %^ [%d:%t %^] "%r" %s %b %^"%R" "%u" "%^" %T' -o /html/index.html
	private final String CMD_PARSE = "goaccess -a -d -f %s --time-format='%s' --date-format='%s' --log-format='%s' -o %s";
	
	@Value("${hustack.parse.timeFormat}")
	private String timeFormat;
	
	@Value("${hustack.parse.dateFormat}")
	private String dateFormat;
	
	@Value("${hustack.parse.logFormat}")
	private String logFormat;

	@Value("${hustack.parse.cron}")
	private String cron;

	@Override
	public void initSpringProperties() {
		HuStackProperties properties = super.getProperties();
		Parse parse = properties.getParse();
		timeFormat = parse.getTimeFormat();
		dateFormat = parse.getDateFormat();
		logFormat = parse.getLogFormat();
		cron = parse.getCron();
	}

	@Override
	public void exec(JobExecutionContext context) throws JobExecutionException {
		Path parseLog = super.getLogSavePath();
		Path reportHtml = super.getReportHtmlPath();
		Path reportJson = super.getReportJsonPath();
		
		// parse html
		String parseHtmlCMD = String.format(CMD_PARSE, parseLog, timeFormat, dateFormat, logFormat, reportHtml);
		execCMD(new String[] { "sh", "-c", parseHtmlCMD });

		// parse json
		String parseJsonCMD = String.format(CMD_PARSE, parseLog, timeFormat, dateFormat, logFormat, reportJson);
		execCMD(new String[] { "sh", "-c", parseJsonCMD });
	}
	
	public String execCMD(String... command) {
		String response = "no exec response.";
		Process exec;
		try {
			exec = runtime.exec(command);
			InputStream inputStream = exec.getInputStream();
			IOUtils.toString(inputStream, Charset.forName("GBK"));
			logger.info("execute command: {}, response: {}", command, response);
		} catch (IOException e) {
			response = e.getMessage();
			logger.error("Oops, execute has error: {}", e.getMessage(), e);
		}
		return response;
	}

	@Override
	public String getCronExpression() {
		return cron;
	}
}
