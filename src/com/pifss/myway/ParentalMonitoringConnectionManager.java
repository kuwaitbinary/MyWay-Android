package com.pifss.myway;

import java.util.ArrayList;

public class ParentalMonitoringConnectionManager {
	
	private static ArrayList<Driver> list = new ArrayList<Driver>();
	
	protected static void removeDriver(Driver driver) {
		// get parent username form login shared preferences
		list.remove(driver);
	}
	
	protected static ArrayList<Driver> getDriverList() {
		
		// get parent username form login shared preferences
		
		// request the latest list from the backend server then return the list
		
		if (list.size() == 0) {
			return null;
		} else {
			return list;
		}
		
	}
	
	protected static void addDriver(Driver driver) {
		// send pairing request to the backend
		list.add(driver);
	}
	
}
