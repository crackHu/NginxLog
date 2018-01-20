package com.hustack.nl.domain;

import com.hustack.nl.domain.metadata.Avgts;
import com.hustack.nl.domain.metadata.Bytes;
import com.hustack.nl.domain.metadata.Cumts;
import com.hustack.nl.domain.metadata.Data;
import com.hustack.nl.domain.metadata.Hits;
import com.hustack.nl.domain.metadata.Maxts;
import com.hustack.nl.domain.metadata.Visitors;

public class Metadata {

	private Avgts avgts;

	private Bytes bytes;

	private Cumts cumts;

	private Data data;

	private Hits hits;

	private Maxts maxts;

	private Visitors visitors;

	public Avgts getAvgts() {
		return avgts;
	}

	public void setAvgts(Avgts avgts) {
		this.avgts = avgts;
	}

	public Bytes getBytes() {
		return bytes;
	}

	public void setBytes(Bytes bytes) {
		this.bytes = bytes;
	}

	public Cumts getCumts() {
		return cumts;
	}

	public void setCumts(Cumts cumts) {
		this.cumts = cumts;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Hits getHits() {
		return hits;
	}

	public void setHits(Hits hits) {
		this.hits = hits;
	}

	public Maxts getMaxts() {
		return maxts;
	}

	public void setMaxts(Maxts maxts) {
		this.maxts = maxts;
	}

	public Visitors getVisitors() {
		return visitors;
	}

	public void setVisitors(Visitors visitors) {
		this.visitors = visitors;
	}

	@Override
	public String toString() {
		return "Metadata [avgts=" + avgts + ", bytes=" + bytes + ", cumts=" + cumts + ", data=" + data + ", hits="
				+ hits + ", maxts=" + maxts + ", visitors=" + visitors + "]";
	}
}
