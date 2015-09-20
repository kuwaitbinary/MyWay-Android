package com.pifss.myway;

public class Driver {
	
	private String username;
	
	// or protected?
	public Driver(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
