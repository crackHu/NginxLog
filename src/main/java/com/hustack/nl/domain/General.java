package com.hustack.nl.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class General {

	private Long bandwidth;

	@JsonProperty("date_time")
	private String dateTime;

	@JsonProperty("excluded_hits")
	private Integer excludedHits;

	@JsonProperty("failed_requests")
	private Integer failedRequests;

	@JsonProperty("generation_time")
	private Integer generationTime;

	@JsonProperty("log_path")
	private List<String> logPath;

	@JsonProperty("log_size")
	private Long logSize;

	@JsonProperty("start_date")
	private String startDate;

	@JsonProperty("total_requests")
	private Integer totalRequests;

	@JsonProperty("unique_files")
	private Integer uniqueFiles;

	@JsonProperty("unique_not_found")
	private Integer uniqueNotFound;

	@JsonProperty("unique_referrers")
	private Integer uniqueReferrers;

	@JsonProperty("unique_static_files")
	private Integer uniqueStaticFiles;

	@JsonProperty("unique_visitors")
	private Integer uniqueVisitors;

	@JsonProperty("valid_requests")
	private Integer validRequests;

	public Long getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Long bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getExcludedHits() {
		return excludedHits;
	}

	public void setExcludedHits(Integer excludedHits) {
		this.excludedHits = excludedHits;
	}

	public Integer getFailedRequests() {
		return failedRequests;
	}

	public void setFailedRequests(Integer failedRequests) {
		this.failedRequests = failedRequests;
	}

	public Integer getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(Integer generationTime) {
		this.generationTime = generationTime;
	}

	public List<String> getLogPath() {
		return logPath;
	}

	public void setLogPath(List<String> logPath) {
		this.logPath = logPath;
	}

	public Long getLogSize() {
		return logSize;
	}

	public void setLogSize(Long logSize) {
		this.logSize = logSize;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(Integer totalRequests) {
		this.totalRequests = totalRequests;
	}

	public Integer getUniqueFiles() {
		return uniqueFiles;
	}

	public void setUniqueFiles(Integer uniqueFiles) {
		this.uniqueFiles = uniqueFiles;
	}

	public Integer getUniqueNotFound() {
		return uniqueNotFound;
	}

	public void setUniqueNotFound(Integer uniqueNotFound) {
		this.uniqueNotFound = uniqueNotFound;
	}

	public Integer getUniqueReferrers() {
		return uniqueReferrers;
	}

	public void setUniqueReferrers(Integer uniqueReferrers) {
		this.uniqueReferrers = uniqueReferrers;
	}

	public Integer getUniqueStaticFiles() {
		return uniqueStaticFiles;
	}

	public void setUniqueStaticFiles(Integer uniqueStaticFiles) {
		this.uniqueStaticFiles = uniqueStaticFiles;
	}

	public Integer getUniqueVisitors() {
		return uniqueVisitors;
	}

	public void setUniqueVisitors(Integer uniqueVisitors) {
		this.uniqueVisitors = uniqueVisitors;
	}

	public Integer getValidRequests() {
		return validRequests;
	}

	public void setValidRequests(Integer validRequests) {
		this.validRequests = validRequests;
	}

	@Override
	public String toString() {
		return "Generals [bandwidth=" + bandwidth + ", dateTime=" + dateTime + ", excludedHits=" + excludedHits
				+ ", failedRequests=" + failedRequests + ", generationTime=" + generationTime + ", logPath=" + logPath
				+ ", logSize=" + logSize + ", startDate=" + startDate + ", totalRequests=" + totalRequests
				+ ", uniqueFiles=" + uniqueFiles + ", uniqueNotFound=" + uniqueNotFound + ", uniqueReferrers="
				+ uniqueReferrers + ", uniqueStaticFiles=" + uniqueStaticFiles + ", uniqueVisitors=" + uniqueVisitors
				+ ", validRequests=" + validRequests + "]";
	}
}
