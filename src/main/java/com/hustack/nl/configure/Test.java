package com.hustack.nl.configure;

import org.springframework.cache.annotation.Cacheable;

public class Test {

	private String id;
	
	public Test() {
	}
	
	public Test(String id) {
		this.id = id;
	}
	
	@Cacheable("test")
	public String cache(String str){
		System.out.println("breakdown cache");
		return str;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + "]" + this.hashCode();
	}
}
