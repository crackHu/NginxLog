package com.hustack.nl.scheduled.job;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.text.StrSubstitutor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.hustack.nl.util.IOUtils;

@Component
public class LogJob implements Job {
	
	private Logger log = LoggerFactory.getLogger(LogJob.class);

	@Value("${hustack.log.endpoint}")
	private String endpoint;

	@Value("${hustack.log.dateFormat:yyyy-MM-dd}")
	private String dateFormat;

	@Value("${hustack.log.nameFormat:access.%date%.log}")
	private String nameFormat;

	@Value("${hustack.log.savePath}")
	private String logSavePath;
	
	@Value("${hustack.parse.savePath}")
	private String parseSavePath;

	@Value("${hustack.log.cron}")
	private String cron;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		// 1. download log file
		String logName = getLogName(nameFormat, dateFormat);
		String url = endpoint + logName;
		URI resourceUri = URI.create(url);
		File download = IOUtils.download(resourceUri);
		
		// 2. save to local
		File saveFile = getLogSavePath(logName).toFile();
		try {
			FileCopyUtils.copy(download, saveFile);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public String getLogDate() {
		return getLogDate(dateFormat);
	}
	
	private String getLogDate(String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return DateFormatUtils.format(calendar, dateFormat);
	}
	
	public String getLogName() {
		return getLogName(nameFormat, dateFormat);
	}

	private String getLogName(String nameFormat, String dateFormat) {
		final String prefix = "%";
		final String suffix = "%";

		Map<String, Object> map = Maps.newHashMap();
		String yesterday = getLogDate(dateFormat);
		map.put("date", yesterday);
		return StrSubstitutor.replace(nameFormat, map, prefix, suffix);
	}
	
	public Path getLogSavePath() {
		String logName = getLogName();
		return Paths.get(logSavePath, logName);
	}
	
	public Path getLogSavePath(String logName) {
		return Paths.get(logSavePath, logName);
	}
	
	public Path getReportHtmlPath() {
		String logName = getLogName();
		String logNameWithoutExt = Files.getNameWithoutExtension(logName);
		return getReportHtmlPath(logNameWithoutExt);
	}
	
	public Path getReportHtmlPath(String logNameWithoutExt) {
		return Paths.get(parseSavePath, logNameWithoutExt + ".html");
	}
	
	public Path getReportJsonPath() {
		String logName = getLogName();
		String logNameWithoutExt = Files.getNameWithoutExtension(logName);
		return getReportJsonPath(logNameWithoutExt);
	}
	
	public Path getReportJsonPath(String logNameWithoutExt) {
		return Paths.get(parseSavePath, logNameWithoutExt + ".json");
	}

	public String getCron() {
		return cron;
	}
}
