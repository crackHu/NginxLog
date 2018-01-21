package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.quartz.Job;
import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.ScheduledTasks;

// @Component
public class InitializingCronTrigger extends BaseCronTrigger implements Serializable {

	private static final long serialVersionUID = 1L;

	public InitializingCronTrigger() {
		init();
	}

	@Override
	public String getCronExpression() {
		return "0/5 * * * * ?";
	}

	@Override
	public Job getJob() {
		return new ScheduledTasks();
	}

}