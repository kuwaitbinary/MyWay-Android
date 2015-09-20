package com.pifss.myway;

public class Settings {
	boolean enableReports;
	boolean enableNearbyPOI;
	boolean enableOverspeedAlert;
	boolean enableVoiceAlert;
	boolean loggedIn;
	String currentLanguage;
	
	public Settings() {
		// TODO Auto-generated constructor stub
		
	}

	public boolean isEnableReports() {
		return enableReports;
	}

	public void setEnableReports(boolean enableReports) {
		this.enableReports = enableReports;
	}

	public boolean isEnableNearbyPOI() {
		return enableNearbyPOI;
	}

	public void setEnableNearbyPOI(boolean enableNearbyPOI) {
		this.enableNearbyPOI = enableNearbyPOI;
	}

	public boolean isEnableOverspeedAlert() {
		return enableOverspeedAlert;
	}

	public void setEnableOverspeedAlert(boolean enableOverspeedAlert) {
		this.enableOverspeedAlert = enableOverspeedAlert;
	}

	public boolean isEnableVoiceAlert() {
		return enableVoiceAlert;
	}

	public void setEnableVoiceAlert(boolean enableVoiceAlert) {
		this.enableVoiceAlert = enableVoiceAlert;
	}

	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
}
