package com.hustack.nl.scheduled.job;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.hustack.nl.configure.HuStackProperties;
import com.hustack.nl.configure.HuStackProperties.Log;
import com.hustack.nl.configure.HuStackProperties.Parse;
import com.hustack.nl.util.IOUtils;

@Component
public class LogJob extends BaseJob {

	@Value("${hustack.log.endpoint}")
	private String endpoint;

	@Value("${hustack.log.dateFormat:yyyy-MM-dd}")
	private String dateFormat;

	@Value("${hustack.log.nameFormat:access.%date%.log}")
	private String nameFormat;

	@Value("${hustack.log.savePath}")
	private String logSavePath;
	
	@Value("${hustack.log.cron}")
	private String cron;
	
	@Value("${hustack.parse.savePath}")
	private String parseSavePath;

	@Override
	public void initSpringProperties() {
		HuStackProperties properties = super.getProperties();
		Log log = properties.getLog();
		Parse parse = properties.getParse();
		endpoint = log.getEndpoint();
		dateFormat = log.getDateFormat();
		nameFormat = log.getNameFOrmat();
		logSavePath = log.getSavePath();
		parseSavePath = parse.getSavePath();
	}
	
	@Override
	public void exec(JobExecutionContext context) throws JobExecutionException {
		initSpringProperties();
		// 1. download log file
		String logName = getLogName();
		String url = endpoint + logName;
		URI resourceUri = URI.create(url);
		File download = IOUtils.download(resourceUri);
		
		// 2. save to local
		File saveFile = getLogSavePath(logName).toFile();
		try {
			FileCopyUtils.copy(download, saveFile);
		} catch (IOException e) {
			super.logger.error(e.getMessage(), e);
		}
	}

	@Override
	public String getCronExpression() {
		return cron;
	}
}
