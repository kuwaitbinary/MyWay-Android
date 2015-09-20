package com.pifss.myway;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_ReportDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_details);
		
		TextView titleTV = (TextView) findViewById(R.id.titleText);
		TextView descTV = (TextView) findViewById(R.id.decsText);
		//TextView locTV = (TextView) findViewById(R.id.locationText);
		
		Bundle b =getIntent().getExtras();
		Comment c = (Comment)b.get("comment");
		
		titleTV.setText(c.commentType);
		descTV.setText(c.comment);
		
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_report_details, menu);
		return true;
	}

}
