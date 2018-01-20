package com.hustack.nl.domain;

import java.util.List;

public class Requests {
	
	private List<Object> data;
	
	private Metadata metadata;

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "Requests [data=" + data + ", metadata=" + metadata + "]";
	}
}
