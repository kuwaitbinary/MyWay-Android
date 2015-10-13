package com.pifss.myway;

public class User {
	
	private String username;
	private String password;
	private String email;
	private String profilePicture;
	
	public User() {
		username = "";
		password = "";
		email = "";
		profilePicture = "";
	}
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User(String username, String password, String email, String profilePicture) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.profilePicture = profilePicture;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
}