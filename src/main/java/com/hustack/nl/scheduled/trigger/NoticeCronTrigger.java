package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hustack.nl.scheduled.job.NoticeJob;

@Component
public class NoticeCronTrigger extends BaseCronTrigger implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private NoticeJob noticeJob;
	
	public NoticeCronTrigger(NoticeJob noticeJob) {
		this.noticeJob = noticeJob;
		super.init();
	}

	@Override
	public Job getJob() {
		return noticeJob;
	}

	@Override
	public String getCronExpression() {
		return noticeJob.getCron();
	}

}