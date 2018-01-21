package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.quartz.Job;
import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.ParseJob;

@Component
public class ParseCronTrigger extends BaseCronTrigger implements Serializable {

	private static final long serialVersionUID = 1L;

	private ParseJob parseJob;
	
	public ParseCronTrigger(ParseJob parseJob) {
		this.parseJob = parseJob;
		super.init();
	}

	@Override
	public Job getJob() {
		return parseJob;
	}

	@Override
	public String getCronExpression() {
		return parseJob.getCron();
	}

}