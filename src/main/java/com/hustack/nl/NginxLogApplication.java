package com.hustack.nl;

import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hustack.nl.configure.Test;
import com.hustack.nl.configure.WebMVCConfiguration;
import com.hustack.nl.scheduled.job.LogJob;
import com.hustack.nl.scheduled.job.ParseJob;
import com.hustack.nl.util.RedisUtils;

@RestController
@SpringBootApplication
public class NginxLogApplication {

	@Autowired
	private LogJob logJob;
	@Autowired
	private ParseJob parseJob;
	
	@Autowired
	private Test test1;
	
	@Autowired
	@Qualifier("test2")
	private Test test3;

	public static void main(String[] args) {
		SpringApplication.run(NginxLogApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "hello";
	}

	@GetMapping("/redis/{str}")
	public Test redis(@PathVariable String str) {
		// return new Test(str);
		return test1.cache(str);
	}

	@GetMapping("/log")
	public void log() {
		try {
			logJob.execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/parse")
	public void parse() {
		try {
			parseJob.execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/exec/{cmd}")
	public String exec(@PathVariable String cmd) {
		return parseJob.execCMD(cmd);
	}

	@GetMapping("/goaccess")
	public void goaccess() {
		try {
			logJob.execute(null);
			parseJob.execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
