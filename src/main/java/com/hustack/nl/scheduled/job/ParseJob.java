package com.hustack.nl.scheduled.job;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParseJob implements Job {
	
	private final static Logger log = LoggerFactory.getLogger(ParseJob.class);
	
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
	
	@Autowired
	private LogJob logJob;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Path parseLog = logJob.getLogSavePath();
		Path reportHtml = logJob.getReportHtmlPath();
		Path reportJson = logJob.getReportJsonPath();
		
		// parse html
		String parseHtmlCMD = String.format(CMD_PARSE, parseLog, timeFormat, dateFormat, logFormat, reportHtml);
		exec(new String[] { "sh", "-c", parseHtmlCMD });

		// parse json
		String parseJsonCMD = String.format(CMD_PARSE, parseLog, timeFormat, dateFormat, logFormat, reportJson);
		exec(new String[] { "sh", "-c", parseJsonCMD });
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

	public String getCron() {
		return cron;
	}
}
