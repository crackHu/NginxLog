package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import com.hustack.nl.scheduled.job.BaseJob;
import com.hustack.nl.scheduled.job.ParseJob;

public abstract class BaseCronTrigger<T extends BaseJob> extends CronTriggerFactoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final static Logger log = LoggerFactory.getLogger(ParseJob.class);
	
	private T job;
	
	private String cornExpression;
	
	public BaseCronTrigger(T job) {
		this.job = job;
		init();
	}
	
	public void init() {
		if(job == null) {
			log.info("BaseCronTrigger job is null");
			throw new NullPointerException();
		}
		this.cornExpression = job.getCronExpression();
		
		final Class<? extends BaseJob> jobClass = job.getClass(); 
		final String jobName = jobClass.getSimpleName();
		final String jobGroup = "group1";
		
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
		this.setJobDetail(jobDetail);
		this.setCronExpression(cornExpression);
	}

}
