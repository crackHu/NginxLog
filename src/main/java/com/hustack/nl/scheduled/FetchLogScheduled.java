package com.hustack.nl.scheduled;

import java.io.File;
import java.net.URI;
import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.hustack.nl.util.DownloadUtils;

@Component
public class FetchLogScheduled {
	
	private final String URL = "https://www.peoplesmedic.com/logs/error.log";
	
	public String fetchLog() {
		File download = DownloadUtils.down(URI.create(URL));
		System.out.println(download);
		return null;
	}
	
	
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String format = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(calendar);
		System.out.println(format);
	}

}
