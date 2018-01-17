package com.hustack.nl.scheduled;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.google.common.collect.Maps;
import com.hustack.nl.util.IOUtils;

@Component
public class LogScheduled {
	
	private Logger log = LoggerFactory.getLogger(LogScheduled.class);

	@Value("${hustack.log.endpoint}")
	private String endpoint;

	@Value("${hustack.log.dateFormat:yyyy-MM-dd}")
	private String dateFormat;

	@Value("${hustack.log.nameFormat:access.%date%.log}")
	private String nameFormat;
	
	@Value("${hustack.log.savePath}")
	private String savePath;

	public String fetchLog() {
		// 1. download log file
		String logName = getLogName(nameFormat, dateFormat);
		String url = endpoint + logName;
		URI resourceUri = URI.create(url);
		File download = IOUtils.download(resourceUri);
		
		// 2. save to local
		File saveFile = Paths.get(savePath, logName).toFile();
		try {
			FileCopyUtils.copy(download, saveFile);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private String getLogName(String nameFormat, String dateFormat) {
		final String prefix = "%";
		final String suffix = "%";
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String yesterday = DateFormatUtils.format(calendar, dateFormat);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("date", yesterday);
		return StrSubstitutor.replace(nameFormat, map, prefix, suffix);
	}
}
