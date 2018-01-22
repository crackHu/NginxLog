package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.ScheduledTasks;

// @Component
public class ScheduledTasksCronTrigger extends BaseCronTrigger<ScheduledTasks> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ScheduledTasksCronTrigger(ScheduledTasks job) {
		super(job);
	}

}