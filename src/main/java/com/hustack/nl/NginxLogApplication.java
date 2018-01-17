package com.hustack.nl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hustack.nl.scheduled.LogScheduled;

@RestController
@EnableScheduling
@SpringBootApplication
public class NginxLogApplication {
	
	@Autowired
	private LogScheduled fetchLogScheduled;

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
}
