package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.quartz.Job;
import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.LogJob;

@Component
public class LogCronTrigger extends BaseCronTrigger implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LogJob logJob;
	
	public LogCronTrigger(LogJob logJob) {
		this.logJob = logJob;
		super.init();
	}

	@Override
	public Job getJob() {
		return logJob;
	}

	@Override
	public String getCronExpression() {
		return logJob.getCron();
	}

}