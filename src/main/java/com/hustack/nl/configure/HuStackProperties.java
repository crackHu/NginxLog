package com.hustack.nl.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hustack", ignoreUnknownFields = false)
public class HuStackProperties {
	
	private Boolean enable;
	
	private final Log log = new Log();
	
	public static class Log {
		private String endpoint;
		private String dateFormat;
		private String nameFOrmat;
		private String savePath;
		private String cron;
		public String getEndpoint() {
			return endpoint;
		}
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}
		public String getDateFormat() {
			return dateFormat;
		}
		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}
		public String getNameFOrmat() {
			return nameFOrmat;
		}
		public void setNameFOrmat(String nameFOrmat) {
			this.nameFOrmat = nameFOrmat;
		}
		public String getSavePath() {
			return savePath;
		}
		public void setSavePath(String savePath) {
			this.savePath = savePath;
		}
		public String getCron() {
			return cron;
		}
		public void setCron(String cron) {
			this.cron = cron;
		}
	}
	
	private final Parse parse = new Parse();
	
	public static class Parse {
		private String timeFormat;
		private String dateFormat;
		private String logFormat;
		private String savePath;
		private String cron;
		public String getTimeFormat() {
			return timeFormat;
		}
		public void setTimeFormat(String timeFormat) {
			this.timeFormat = timeFormat;
		}
		public String getDateFormat() {
			return dateFormat;
		}
		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}
		public String getLogFormat() {
			return logFormat;
		}
		public void setLogFormat(String logFormat) {
			this.logFormat = logFormat;
		}
		public String getSavePath() {
			return savePath;
		}
		public void setSavePath(String savePath) {
			this.savePath = savePath;
		}
		public String getCron() {
			return cron;
		}
		public void setCron(String cron) {
			this.cron = cron;
		}
	}
	
	private final Notice notice = new Notice();
	
	public static class Notice {
		private String cron;
		private String webhook;
		public String getCron() {
			return cron;
		}
		public void setCron(String cron) {
			this.cron = cron;
		}
		public String getWebhook() {
			return webhook;
		}
		public void setWebhook(String webhook) {
			this.webhook = webhook;
		}
	}

	public Log getLog() {
		return log;
	}

	public Parse getParse() {
		return parse;
	}

	public Notice getNotice() {
		return notice;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
