package com.pifss.myway;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Parent_monitoring_report extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.driver_list_row);
		
		/*String newString;
		if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		        newString= null;
		    } else {
		        newString= extras.getString("username");
		    }
		} else {
		    newString= (String) savedInstanceState.getSerializable("username");
		}*/
		
		Bundle extras = getIntent().getExtras();
		
//		ImageView driverImage = (ImageView) findViewById(R.id.imageViewDriverReport);
//		TextView reportField = (TextView) findViewById(R.id.driverName);
//		TextView reportDate = (TextView) findViewById(R.id.textViewDriverReportDate);
//		TextView reportTime = (TextView) findViewById(R.id.textViewDriverReportTime);
		
//		reportField.setText(extras.getString("ReportField"));
//		reportDate.setText(extras.getString("Date"));
//		reportTime.setText(extras.getString("Time"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_parent_monitoring_report,
				menu);
		return true;
	}

}
