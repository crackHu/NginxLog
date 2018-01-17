package com.hustack.nl.scheduled;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ParseScheduled {
	private Logger log = LoggerFactory.getLogger(ParseScheduled.class);
	
	public String parse() {
		Runtime runtime = Runtime.getRuntime();
		String response = "NONE";
		try {
			String [] cmd={"./goaccess"}; 
			Process exec = runtime.exec(cmd);
			InputStream inputStream = exec.getInputStream();
			response = IOUtils.toString(inputStream, Charset.forName("GBK"));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			response = e.getMessage();
		}
		return response;
	}
	
	public static void main(String[] args) {
		new ParseScheduled().parse();
	}
}
