package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.LogJob;

@Component
public class LogCronTrigger extends BaseCronTrigger<LogJob> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public LogCronTrigger(LogJob job) {
		super(job);
	}

}