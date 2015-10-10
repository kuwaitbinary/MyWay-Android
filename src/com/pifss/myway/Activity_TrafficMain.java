package com.pifss.myway;

import org.json.JSONArray;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_TrafficMain extends Activity {

	// main class of traffic reports 
	
//	public static JSONArray reports=null;
//	ListAllReports lr = new ListAllReports();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_main);
//		lr.getReports();
		// four image views represents all types of traffic reports (traffic jam,accident,hazard,other)
		ImageView traffic  = (ImageView) findViewById(R.id.traffic);
		ImageView accident = (ImageView) findViewById(R.id.accident);
		ImageView hazard   = (ImageView) findViewById(R.id.hazard);
		ImageView other    = (ImageView) findViewById(R.id.other);
		Button view = (Button) findViewById(R.id.view);
		
		// traffic jam intent 
		traffic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Activity_TrafficMain.this,Activity_TrafficJam.class);
				
				startActivity(i);
			}
		});
		
		// accident intent 
		accident.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Activity_TrafficMain.this,Activity_AccidentReport.class);
				
				startActivity(i);
				
			}
		});
		
		// hazard intent
		hazard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Activity_TrafficMain.this,Activity_hazard.class);
				
				startActivity(i);
				
			}
		});
		
		// other intent 
		other.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Activity_TrafficMain.this,Activity_other.class);
				
				startActivity(i);
				
			}
		});
		
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i=new Intent(Activity_TrafficMain.this,Activity_ViewReport.class);
//				
//				startActivity(i);
//				
//				ListAllReports lr = new ListAllReports();
//				
//				lr.getReports();
				
				
			Intent i=new Intent(Activity_TrafficMain.this,Activity_ReportDetails.class);
			
			startActivity(i);
				
			}
		});
		
		//use the following line to add the sliding menu to the current page
				SlidingUtil.SetSliding(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_traffic_main, menu);
		return true;
	}
	
	//to send the user to the home page when pressing the back button
		@Override
	    public void onBackPressed() {
	    	// TODO Auto-generated method stub
	    	super.onBackPressed();
	    	Intent i = new Intent(Activity_TrafficMain.this, Home.class);
			startActivity(i);
			finish();
	    }
		
		

}
