package com.pifss.myway;

import java.io.Serializable;

public class Comment implements Serializable{

	String comment;
	String commentType;
	int id;
	double lon;
	double lat;
	public Comment(String comment, String commentType) {
		super();
		this.comment = comment;
		this.commentType = commentType;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
