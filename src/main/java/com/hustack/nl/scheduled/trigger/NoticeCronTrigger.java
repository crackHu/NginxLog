package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.NoticeJob;

@Component
public class NoticeCronTrigger extends BaseCronTrigger<NoticeJob> implements Serializable {

	private static final long serialVersionUID = 1L;

	public NoticeCronTrigger(NoticeJob job) {
		super(job);
	}

}