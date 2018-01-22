package com.hustack.nl.scheduled.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

// @Component
public class ScheduledTasks extends BaseJob {


	@Override
	public void exec(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Scheduling Tasks Examples By Cron: The time is now " + dateFormat().format(new Date()));
	}

	private SimpleDateFormat dateFormat() {
		return new SimpleDateFormat("HH:mm:ss");
	}

	@Override
	public String getCronExpression() {
		return "0/5 * * * * ?";
	}

	@Override
	public void initSpringProperties() {
	}
}
