package com.hustack.nl.domain;

public class Report {

	private General general;
	
	private Requests requests;

	public General getGeneral() {
		return general;
	}

	public void setGeneral(General general) {
		this.general = general;
	}

	public Requests getRequests() {
		return requests;
	}

	public void setRequests(Requests requests) {
		this.requests = requests;
	}

	@Override
	public String toString() {
		return "Report [general=" + general + ", requests=" + requests + "]";
	}
	
}
