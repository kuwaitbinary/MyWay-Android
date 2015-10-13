package com.pifss.myway;


public class Driver {
	
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStartlon() {
		return startlon;
	}
	public void setStartlon(String startlon) {
		this.startlon = startlon;
	}
	public String getStartlat() {
		return startlat;
	}
	public void setStartlat(String startlat) {
		this.startlat = startlat;
	}
	public String getEndlon() {
		return endlon;
	}
	public void setEndlon(String endlon) {
		this.endlon = endlon;
	}
	public String getEndlat() {
		return endlat;
	}
	public void setEndlat(String endlat) {
		this.endlat = endlat;
	}
	public String getDashBoardlat() {
		return dashBoardlat;
	}
	public void setDashBoardlat(String dashBoardlat) {
		this.dashBoardlat = dashBoardlat;
	}
	public String getDashBoardlon() {
		return dashBoardlon;
	}
	public void setDashBoardlon(String dashBoardlon) {
		this.dashBoardlon = dashBoardlon;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getStops() {
		return stops;
	}
	public void setStops(String stops) {
		this.stops = stops;
	}
	public String getReasonlat() {
		return reasonlat;
	}
	public void setReasonlat(String reasonlat) {
		this.reasonlat = reasonlat;
	}
	public String getReasonlon() {
		return reasonlon;
	}
	public void setReasonlon(String reasonlon) {
		this.reasonlon = reasonlon;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "Driver [day=" + day + ", startlon=" + startlon + ", startlat="
				+ startlat + ", endlon=" + endlon + ", endlat=" + endlat
				+ ", dashBoardlat=" + dashBoardlat + ", dashBoardlon="
				+ dashBoardlon + ", Time=" + Time + ", speed=" + speed
				+ ", stops=" + stops + ", reasonlat=" + reasonlat
				+ ", reasonlon=" + reasonlon + ", dName=" + dName
				+ ", userName=" + userName + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Time == null) ? 0 : Time.hashCode());
		result = prime * result + ((dName == null) ? 0 : dName.hashCode());
		result = prime * result
				+ ((dashBoardlat == null) ? 0 : dashBoardlat.hashCode());
		result = prime * result
				+ ((dashBoardlon == null) ? 0 : dashBoardlon.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((endlat == null) ? 0 : endlat.hashCode());
		result = prime * result + ((endlon == null) ? 0 : endlon.hashCode());
		result = prime * result
				+ ((reasonlat == null) ? 0 : reasonlat.hashCode());
		result = prime * result
				+ ((reasonlon == null) ? 0 : reasonlon.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result
				+ ((startlat == null) ? 0 : startlat.hashCode());
		result = prime * result
				+ ((startlon == null) ? 0 : startlon.hashCode());
		result = prime * result + ((stops == null) ? 0 : stops.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (Time == null) {
			if (other.Time != null)
				return false;
		} else if (!Time.equals(other.Time))
			return false;
		if (dName == null) {
			if (other.dName != null)
				return false;
		} else if (!dName.equals(other.dName))
			return false;
		if (dashBoardlat == null) {
			if (other.dashBoardlat != null)
				return false;
		} else if (!dashBoardlat.equals(other.dashBoardlat))
			return false;
		if (dashBoardlon == null) {
			if (other.dashBoardlon != null)
				return false;
		} else if (!dashBoardlon.equals(other.dashBoardlon))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (endlat == null) {
			if (other.endlat != null)
				return false;
		} else if (!endlat.equals(other.endlat))
			return false;
		if (endlon == null) {
			if (other.endlon != null)
				return false;
		} else if (!endlon.equals(other.endlon))
			return false;
		if (reasonlat == null) {
			if (other.reasonlat != null)
				return false;
		} else if (!reasonlat.equals(other.reasonlat))
			return false;
		if (reasonlon == null) {
			if (other.reasonlon != null)
				return false;
		} else if (!reasonlon.equals(other.reasonlon))
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		if (startlat == null) {
			if (other.startlat != null)
				return false;
		} else if (!startlat.equals(other.startlat))
			return false;
		if (startlon == null) {
			if (other.startlon != null)
				return false;
		} else if (!startlon.equals(other.startlon))
			return false;
		if (stops == null) {
			if (other.stops != null)
				return false;
		} else if (!stops.equals(other.stops))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
	private String day ;
	private String startlon ;
	private String startlat ;
	private String endlon ;
	private String endlat ;
	private String dashBoardlat ;
	private String dashBoardlon   ;
	private String Time ;
	private String speed ;
	private String stops ;
	private String reasonlat ;
	private String reasonlon ;
	private String dName;
	private String userName;
	
	
	
}
