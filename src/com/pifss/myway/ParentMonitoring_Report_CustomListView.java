package com.pifss.myway;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

import android.location.GpsStatus.Listener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;


import android.view.Menu;


public class ParentMonitoring_Report_CustomListView extends Activity {

	
	ListView list;
	ParentMonitoring_Report_CustomAdapter adapter = null;
	public  ParentMonitoring_Report_CustomListView CustomListView = null;
	public  ArrayList<ParentMonitoring_Report_ListModel> CustomListViewValuesArr = new ArrayList<ParentMonitoring_Report_ListModel>();
	//final ParentMonitoring_Report_ListModel sched = new ParentMonitoring_Report_ListModel();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent_monitoring__pairing__driver_pairing);
		
		
		ParentMonitoring_Report_ListModel sched = new ParentMonitoring_Report_ListModel();
		sched.setReportField("Speed");
	    sched.setImage("img_speed");
	    sched.setDate("date");
		sched.setTime("time");
		
		CustomListViewValuesArr.add(sched);
		
		sched = new ParentMonitoring_Report_ListModel();
		sched.setReportField("Battery Status");
	    sched.setImage("img_battery_status");
	    sched.setDate("date");
		sched.setTime("time");
		
		CustomListViewValuesArr.add(sched);
		
		sched = new ParentMonitoring_Report_ListModel();
	    sched.setReportField("Speed");
	    sched.setImage("img_speed");
	    sched.setDate("date");
		sched.setTime("time");
		
		CustomListViewValuesArr.add(sched);
		
		sched = new ParentMonitoring_Report_ListModel();
		sched.setReportField("Stop");
	    sched.setImage("img_stops");
	    sched.setDate("date");
		sched.setTime("time");
		
		CustomListViewValuesArr.add(sched);
		
		sched = new ParentMonitoring_Report_ListModel();
		sched.setReportField("Battery Status");
	    sched.setImage("img_battery_status");
	    sched.setDate("date");
		sched.setTime("time");
		
		CustomListViewValuesArr.add(sched);
		
		sched = new ParentMonitoring_Report_ListModel();
		sched.setReportField("Application Status");
	    sched.setImage("img_application_status");
	    sched.setDate("date");
		sched.setTime("time");
		
		CustomListViewValuesArr.add(sched);
		
		
		
		
//		ActionBar bar=getActionBar();
//
//		bar.setDisplayHomeAsUpEnabled(true);
//
//		bar.setIcon(android.R.drawable.ic_input_add);
//
//		Intent i=new Intent(ParentMonitoring_Report_CustomListView.this,POI2Activity.class);
//
//		startActivity(i);
		
		CustomListView = this;
		
		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		//setListData();
		
		Resources res =getResources(); 
        list=(ListView)findViewById(R.id.driverReportList);
        
        /**************** Create Custom Adapter *********/
        
       // ParentMonitoring_Report_ListModel[] Arr = new ParentMonitoring_Report_ListModel[CustomListViewValuesArr.size()];
       // Arr = CustomListViewValuesArr.toArray(Arr);
        
 //       adapter = new ParentMonitoring_Report_CustomAdapter(this,CustomListViewValuesArr, res);
        adapter = new ParentMonitoring_Report_CustomAdapter(CustomListView, CustomListViewValuesArr, res);
//        ArrayAdapter<ParentMonitoring_Report_ListModel> adapter = new ArrayAdapter<ParentMonitoring_Report_ListModel>(ParentMonitoring_Report_CustomListView.this, res);
        list.setAdapter(adapter);
		
	}
	
	/****** Function to set data in ArrayList *************/
    /*public void setListData()
    {
    	
		for (int i = 0; i < CustomListViewValuesArr.size(); i++) {
			
			
			    
			  *//******* Firstly take data in model object ******//*
			
			
			if ( CustomListViewValuesArr.get(i).getReportField() == "Speed" ) {
				   sched.setReportField("Speed");
				   sched.setImage("img_speed");
				   sched.setDate("Date");
				   sched.setTime("Time");
				   
				   

					
			}
		
		else if ( i == 1 ) {
			   sched.setReportField("Stop");
			   sched.setImage("img_stops");
			   sched.setDate("Date");
			   sched.setTime("Time");
			   
			  

		}
		
		else if ( i == 2 ) {
			   sched.setReportField("Phone Status");
			   sched.setImage("img_phone_status");
			   sched.setDate("Date");
			   sched.setTime("Time");
			   
			 
		}
		
		else if ( i == 3 ) {
			   sched.setReportField("Battery Status");
			   sched.setImage("img_battery_status");
			   sched.setDate("Date");
			   sched.setTime("Time");
			   
			   
		}
		
		else if ( i == 4 ) {
			   sched.setReportField("App Closed");
			   sched.setImage("img_application_status");
			   sched.setDate("Date");
			   sched.setTime("Time");
			   
		}
	
	
			*//******** Take Model Object in ArrayList **********//*
			CustomListViewValuesArr.add(sched);
		}
		
    }*/
    
    public void onItemClick(int mPosition)
    {
    	ParentMonitoring_Report_ListModel tempValues = (ParentMonitoring_Report_ListModel) CustomListViewValuesArr.get(mPosition);

    	/*Toast.makeText(CustomListView, 
    			""+tempValues.getReportField()+" \nImage:"+tempValues.getImage()+" \nDate:"+tempValues.getDate()+" \nTime:"+tempValues.getTime(), 
    			Toast.LENGTH_LONG)
    	.show();*/
    		   	   
		Intent intent = new Intent(this, Parent_monitoring_report.class);
		intent.putExtra("ReportField", tempValues.getReportField());
		intent.putExtra("Image", tempValues.getImage());
		intent.putExtra("Date", tempValues.getDate());
		intent.putExtra("Time", tempValues.getTime());
   	    startActivity(intent);
    	
    }
   
    Listener listen = new Listener() {

		@Override
		public void onGpsStatusChanged(int event) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
}
