package com.hustack.nl.domain;

import java.text.NumberFormat;

public class ReportContent {

	private static NumberFormat numberFormat = NumberFormat.getNumberInstance();

	private String totalRequests;

	private String validRequests;

	private String failedRequests;

	private String uniqueVisitors;

	private String logSize;

	private String bandwidth;

	public ReportContent(Report curReport, Report lastReport) {
		General curGeneral = curReport.getGeneral();
		General lastGeneral = lastReport.getGeneral();

		if (curGeneral == null) {
			curGeneral = new General();
		}
		if (lastGeneral == null) {
			lastGeneral = new General();
		}

		Long curtotalRequests = curGeneral.getTotalRequests();
		Long curValidRequests = curGeneral.getValidRequests();
		Long curFailedRequests = curGeneral.getFailedRequests();
		Long curUniqueVisitors = curGeneral.getUniqueVisitors();
		Long curLogSize = curGeneral.getLogSize();
		Long curBandwidth = curGeneral.getBandwidth();

		Long lastTotalRequests = lastGeneral.getTotalRequests();
		Long lastValidRequests = lastGeneral.getValidRequests();
		Long lastFailedRequests = lastGeneral.getFailedRequests();
		Long lastUniqueVisitors = lastGeneral.getUniqueVisitors();
		Long lastLogSize = lastGeneral.getLogSize();
		Long lastBandwidth = lastGeneral.getBandwidth();

		setTotalRequests(compareFormat(curtotalRequests, lastTotalRequests));
		setValidRequests(compareFormat(curValidRequests, lastValidRequests));
		setFailedRequests(compareFormat(curFailedRequests, lastFailedRequests));
		setUniqueVisitors(compareFormat(curUniqueVisitors, lastUniqueVisitors));
		setLogSize(getPrintSize(curLogSize));
		setBandwidth(getPrintSize(curBandwidth));
	}

	private String getPrintSize(Long b) {
		if (b == null) {
			return null;
		}
		Double bit = new Double(b);
		if (bit < 1024) {
			return String.format("%.2f B", bit);
		} else {
			bit = bit / 1024;
		}
		if (bit < 1024) {
			return String.format("%.2f KB", bit);
		} else {
			bit = bit / 1024;
		}
		if (bit < 1024) {
			return String.format("%.2f MiB", bit);
		} else {
			bit = bit / 1024;
			return String.format("%.2f GiB", bit);
		}
	}
	
	public static void main(String[] args) {
		Long current = 123L;
		Long target = 123L;
		System.out.println(current == target);
	}

	private String compareFormat(Long current, Long target) {
		if (current == null) {
			current = 0L;
		}
		if (target == null) {
			target = 0L;
		}
		if (current == target) {
			return current.toString();
		} else {
			String value = numberFormat.format(current);
			String diffValue = numberFormat.format(current - target);
			String plusOrMinus = getPlusOrMinus(current, target);
			return String.format("%s %s%s", value, plusOrMinus, diffValue);
		}
	}

	private String getPlusOrMinus(Long current, Long target) {
		if (current > target) {
			return "+";
		}
		return "";
	}

	public String getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(String totalRequests) {
		this.totalRequests = totalRequests;
	}

	public String getValidRequests() {
		return validRequests;
	}

	public void setValidRequests(String validRequests) {
		this.validRequests = validRequests;
	}

	public String getFailedRequests() {
		return failedRequests;
	}

	public void setFailedRequests(String failedRequests) {
		this.failedRequests = failedRequests;
	}

	public String getUniqueVisitors() {
		return uniqueVisitors;
	}

	public void setUniqueVisitors(String uniqueVisitors) {
		this.uniqueVisitors = uniqueVisitors;
	}

	public String getLogSize() {
		return logSize;
	}

	public void setLogSize(String logSize) {
		this.logSize = logSize;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

}
