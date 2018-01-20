package com.hustack.nl.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

public class DDRobot {

	private String msgtype;
	
	private Markdown markdown;
	
	private String at;
	
	@JsonIgnore
	private List<String> atMobiles;
	
	@JsonIgnore
	private Boolean isAtAll;
	
	public DDRobot() {
		// TODO Auto-generated constructor stub
	}

	public String getMsgtype() {
		String msgtype = null;
		if (this.markdown != null) {
			msgtype = "markdown";
		}
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Markdown getMarkdown() {
		return markdown;
	}

	public void setMarkdown(Markdown markdown) {
		this.markdown = markdown;
	}

	public List<String> getAtMobiles() {
		return atMobiles;
	}

	public void setAtMobiles(List<String> atMobiles) {
		this.atMobiles = atMobiles;
	}

	public Boolean getIsAtAll() {
		return isAtAll;
	}

	public void setIsAtAll(Boolean isAtAll) {
		this.isAtAll = isAtAll;
	}

	public void getAt() {
		Map<String, Object> at = Maps.newHashMap();
		at.put("at", this.at);
		at.put("isAtAll", this.isAtAll);
	}

	@Override
	public String toString() {
		return "DDRobot [msgtype=" + msgtype + ", markdown=" + markdown + ", at=" + at + ", atMobiles=" + atMobiles
				+ ", isAtAll=" + isAtAll + "]";
	}
}
