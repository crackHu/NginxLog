package com.hustack.nl.configure;

public class Test {

	private String id;
	
	public Test() {
	}
	
	public Test(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + "]" + this.hashCode();
	}
}
