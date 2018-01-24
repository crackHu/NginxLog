package com.hustack.nl.scheduled;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.hustack.nl.configure.HuStackProperties;
import com.hustack.nl.util.RedisUtils;

@Configuration
@EnableScheduling
public class ScheduleRefresh {

	private final Logger log = LoggerFactory.getLogger(ScheduleRefresh.class);

	@Autowired
	private CronTrigger[] cronJobTrigger;

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
	private HuStackProperties properties;
	
	@PostConstruct
	public void initCron() {
		String logCron = properties.getLog().getCron();
		String parseCron = properties.getParse().getCron();
		String noticeCron = properties.getNotice().getCron();
		
		Map<String, String> cronMap = Maps.newHashMap();
		cronMap.put("LogJob", logCron);
		cronMap.put("ParseJob", parseCron);
		cronMap.put("NoticeJob", noticeCron);
		
		cronMap.forEach((jobName, cron) -> {
			String cronKey = properties.getCronKey(jobName);
			boolean exists = redisUtils.exists(cronKey);
			if (!exists) {
				redisUtils.set(cronKey, cron);
			}
		});;
	}

	@Scheduled(fixedRate = 1000)
	public void rescheduleJob() throws SchedulerException {
		
		for (CronTrigger trigger : cronJobTrigger) {
			TriggerKey key = trigger.getKey();
			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(key);
			if (cronTrigger == null) {
				continue;
			}

			JobKey jobKey = cronTrigger.getJobKey();
			String lastestCron = fetchLastestCron(jobKey);
			String cron = cronTrigger.getCronExpression();
			if (StringUtils.equals(lastestCron, cron)) {
				continue;
			}
			
			@SuppressWarnings("rawtypes")
			ScheduleBuilder scheduleBuilder = null;
			try {
				scheduleBuilder= CronScheduleBuilder.cronSchedule(lastestCron);
			} catch (Exception e) {
				log.error("Oops, CronScheduleBuilder.cronSchedule has error: {}", e.getMessage());
			}
			if (scheduleBuilder == null) {
				continue;
			}
			
			@SuppressWarnings("unchecked")
			Trigger newTrigger = cronTrigger.getTriggerBuilder().withIdentity(key).withSchedule(scheduleBuilder).build();
			
			Date date = scheduler.rescheduleJob(key, newTrigger);
			log.info("Refresh Schedule Job: {}, cron: [{}] -> [{}] next time: {}", jobKey, cron, lastestCron, date);
		}
	}

	private String fetchLastestCron(JobKey jobKey) {
		Assert.notNull(jobKey, "fetchLastestCron [jobKey] can't be null");
		String name = jobKey.getName();
		String cronKey = properties.getCronKey(name);
		String cron = redisUtils.get(cronKey).toString();
		return cron;
	}
}