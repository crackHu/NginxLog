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
	
	public void parse() {
		Runtime runtime = Runtime.getRuntime();
		try {
			String [] cmd={"ipconfig"}; 
			Process exec = runtime.exec(cmd);
			InputStream inputStream = exec.getInputStream();
			String response = IOUtils.toString(inputStream, Charset.forName("GBK"));
			System.out.println(response);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		new ParseScheduled().parse();
	}
}
