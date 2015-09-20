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
		setContentView(R.layout.activity_parent_monitoring_report);
		
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
		
		ImageView imageView = (ImageView) findViewById(R.id.reportImage);
		TextView reportField = (TextView) findViewById(R.id.reportField);
		TextView reportDate = (TextView) findViewById(R.id.reportDate);
		TextView reportTime = (TextView) findViewById(R.id.reportTime);
		
		reportField.setText(extras.getString("ReportField"));
		reportDate.setText(extras.getString("Date"));
		reportTime.setText(extras.getString("Time"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_parent_monitoring_report,
				menu);
		return true;
	}

}
