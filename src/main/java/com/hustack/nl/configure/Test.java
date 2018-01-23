package com.hustack.nl.configure;

import java.io.Serializable;

import org.springframework.cache.annotation.Cacheable;

public class Test implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public Test() {
	}
	
	public Test(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Cacheable("test111")
	public Test cache(String str){
		System.out.println("breakdown cache");
		return new Test(str);
	}

	@Override
	public String toString() {
		return "Test [id=" + id + "]" + this.hashCode();
	}
}
