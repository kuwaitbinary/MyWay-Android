package com.pifss.myway;


public class ParentMonitoring_Report_ListModel {
	private  String ReportField="";
	private  String Image=""; 
	private  String Date="";
	private  String Time="";
	
	/*********** Set Methods ******************/
	public void setReportField(String ReportField)
	{
		this.ReportField = ReportField;
	}
	
	public void setImage(String Image)
	{
		this.Image = Image;
	}
	
	public void setDate(String date) {
		Date = date;
	}	
	public void setTime(String time) {
		Time = time;
	}
	
	
	/*********** Get Methods ****************/
	public String getReportField()
	{
		return this.ReportField;
	}
	
	public String getImage()
	{
		return this.Image;
	}

	public String getDate() {
		return Date;
	}

	public String getTime() {
		return Time;
	}

	

	
}
