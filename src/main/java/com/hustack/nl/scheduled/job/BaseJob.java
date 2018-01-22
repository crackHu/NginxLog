package com.hustack.nl.scheduled.job;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.text.StrSubstitutor;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.hustack.nl.configure.HuStackProperties;
import com.hustack.nl.configure.HuStackProperties.Log;
import com.hustack.nl.configure.HuStackProperties.Parse;
import com.hustack.nl.util.SpringUtils;

public abstract class BaseJob implements Job {
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public volatile static HuStackProperties properties;
	
	private static Log log;
	
	private static Parse parse;
	
	public abstract void exec(JobExecutionContext context) throws JobExecutionException ;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (context == null) {
			execute(null);
			return;
		}
		JobDetail jobDetail = context.getJobDetail();
		JobKey key = jobDetail.getKey();
		String name = key.getName();
		String group = key.getGroup();
		String description = jobDetail.getDescription();
		logger.info("BaseJob execute: name = {}, group = {}, description = {}", name, group, description);
		try {
			long begin = System.currentTimeMillis();
			initSpringProperties();
			exec(context);
			long end = System.currentTimeMillis();
			logger.info("BaseJob execute [{}] success, const {} ms", name, end - begin);
		} catch (Exception e) {
			logger.error("Oops, BaseJob execute has error: {}", e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	};
	
	public abstract void initSpringProperties();
	
	public abstract String getCronExpression();
	
	protected static HuStackProperties getProperties(){
		if (properties == null) {
			properties = SpringUtils.getBean(HuStackProperties.class);
		}
		log = properties.getLog();
		parse = properties.getParse();
		return properties;
    }
	
	protected String getLogDate() {
		final String dateFormat = log.getDateFormat();
		return getLogDate(dateFormat);
	}
	
	protected String getLogDate(String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return DateFormatUtils.format(calendar, dateFormat);
	}
	
	protected String getLogName() {
		final String dateFormat = log.getDateFormat();
		final String nameFormat = log.getNameFOrmat();
		return getLogName(nameFormat, dateFormat);
	}

	protected String getLogName(String nameFormat, String dateFormat) {
		final String prefix = "%";
		final String suffix = "%";

		Map<String, Object> map = Maps.newHashMap();
		String yesterday = getLogDate(dateFormat);
		map.put("date", yesterday);
		return StrSubstitutor.replace(nameFormat, map, prefix, suffix);
	}
	
	protected Path getLogSavePath() {
		final String logSavePath = log.getSavePath();
		String logName = getLogName();
		return Paths.get(logSavePath, logName);
	}
	
	protected Path getLogSavePath(String logName) {
		final String logSavePath = log.getSavePath();
		return Paths.get(logSavePath, logName);
	}
	
	protected Path getReportHtmlPath() {
		String logName = getLogName();
		String logNameWithoutExt = Files.getNameWithoutExtension(logName);
		return getReportHtmlPath(logNameWithoutExt);
	}
	
	protected Path getReportHtmlPath(String logNameWithoutExt) {
		final String parseSavePath = parse.getSavePath();
		return Paths.get(parseSavePath, logNameWithoutExt + ".html");
	}
	
	protected Path getReportJsonPath() {
		String logName = getLogName();
		String logNameWithoutExt = Files.getNameWithoutExtension(logName);
		return getReportJsonPath(logNameWithoutExt);
	}
	
	protected Path getReportJsonPath(String logNameWithoutExt) {
		final String parseSavePath = parse.getSavePath();
		return Paths.get(parseSavePath, logNameWithoutExt + ".json");
	}
	
}
