package com.hustack.nl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hustack.nl.configure.Test;
import com.hustack.nl.scheduled.LogScheduled;
import com.hustack.nl.scheduled.ParseScheduled;

@RestController
@EnableScheduling
@SpringBootApplication
public class NginxLogApplication {
	
	@Autowired
	private LogScheduled fetchLogScheduled;
	@Autowired
	private ParseScheduled parseScheduled;
	@Autowired
	@Qualifier("test2")
	private Test test;

	public static void main(String[] args) {
		SpringApplication.run(NginxLogApplication.class, args);
	}
	
	@GetMapping("/")
	public String hello() {
		return "hello";
	}

	@GetMapping("/log")
	public String log() {
		return fetchLogScheduled.fetchLog();
	}

	@GetMapping("/parse")
	public String parse() {
		return parseScheduled.parseHtml();
	}

	@GetMapping("/exec/{cmd}")
	public String exec(@PathVariable String cmd) {
		return parseScheduled.exec(cmd);
	}

	@GetMapping("/goaccess")
	public String goaccess() {
		fetchLogScheduled.fetchLog();
		return parseScheduled.parseHtml();
	}
}
