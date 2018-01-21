package com.hustack.nl.scheduled.trigger;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

public abstract class BaseCronTrigger extends CronTriggerFactoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Job job;
	
	private String cornExpression;
	
	public void init() {
		this.job = getJob();
		this.cornExpression = getCronExpression();
		
		Class<? extends Job> jobClass = this.job.getClass();
		final String jobName = jobClass.getSimpleName();
		final String jobGroup = "group1";
		
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
		this.setJobDetail(jobDetail);
		this.setCronExpression(cornExpression);
	}

	public abstract Job getJob();

	public abstract String getCronExpression();

}
