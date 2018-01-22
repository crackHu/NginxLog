package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.ParseJob;

@Component
public class ParseCronTrigger extends BaseCronTrigger<ParseJob> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ParseCronTrigger(ParseJob job) {
		super(job);
	}

}