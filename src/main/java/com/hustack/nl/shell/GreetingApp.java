package com.hustack.nl.shell;

// @ShellComponent
public class GreetingApp {

	// @ShellMethod("Say hi")
	public String sayHi(String name) {
		return String.format("Hi %s", name);
	}
}