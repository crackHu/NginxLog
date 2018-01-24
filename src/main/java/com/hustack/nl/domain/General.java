package com.hustack.nl.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class General {

	private Long bandwidth;

	@JsonProperty("date_time")
	private String dateTime;

	@JsonProperty("excluded_hits")
	private Long excludedHits;

	@JsonProperty("failed_requests")
	private Long failedRequests;

	@JsonProperty("generation_time")
	private Long generationTime;

	@JsonProperty("log_path")
	private List<String> logPath;

	@JsonProperty("log_size")
	private Long logSize;

	@JsonProperty("start_date")
	private String startDate;

	@JsonProperty("total_requests")
	private Long totalRequests;

	@JsonProperty("unique_files")
	private Long uniqueFiles;

	@JsonProperty("unique_not_found")
	private Long uniqueNotFound;

	@JsonProperty("unique_referrers")
	private Long uniqueReferrers;

	@JsonProperty("unique_static_files")
	private Long uniqueStaticFiles;

	@JsonProperty("unique_visitors")
	private Long uniqueVisitors;

	@JsonProperty("valid_requests")
	private Long validRequests;

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

	public Long getExcludedHits() {
		return excludedHits;
	}

	public void setExcludedHits(Long excludedHits) {
		this.excludedHits = excludedHits;
	}

	public Long getFailedRequests() {
		return failedRequests;
	}

	public void setFailedRequests(Long failedRequests) {
		this.failedRequests = failedRequests;
	}

	public Long getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(Long generationTime) {
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

	public Long getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(Long totalRequests) {
		this.totalRequests = totalRequests;
	}

	public Long getUniqueFiles() {
		return uniqueFiles;
	}

	public void setUniqueFiles(Long uniqueFiles) {
		this.uniqueFiles = uniqueFiles;
	}

	public Long getUniqueNotFound() {
		return uniqueNotFound;
	}

	public void setUniqueNotFound(Long uniqueNotFound) {
		this.uniqueNotFound = uniqueNotFound;
	}

	public Long getUniqueReferrers() {
		return uniqueReferrers;
	}

	public void setUniqueReferrers(Long uniqueReferrers) {
		this.uniqueReferrers = uniqueReferrers;
	}

	public Long getUniqueStaticFiles() {
		return uniqueStaticFiles;
	}

	public void setUniqueStaticFiles(Long uniqueStaticFiles) {
		this.uniqueStaticFiles = uniqueStaticFiles;
	}

	public Long getUniqueVisitors() {
		return uniqueVisitors;
	}

	public void setUniqueVisitors(Long uniqueVisitors) {
		this.uniqueVisitors = uniqueVisitors;
	}

	public Long getValidRequests() {
		return validRequests;
	}

	public void setValidRequests(Long validRequests) {
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
