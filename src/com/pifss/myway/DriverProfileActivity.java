package com.pifss.myway;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DriverProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent_monitoring__pairing__driver_pairing);

		TextView username = (TextView) findViewById(R.id.textView_ParentControl_AddDriverUsername);
		TextView driverName = (TextView) findViewById(R.id.textView_ParentControl_AddDriverName);
		Button setDestenationForDriver = (Button) findViewById(R.id.button_AddDriver);

		username.setText("Abood");
		driverName.setText("aa@aa.com");

		setDestenationForDriver.setOnClickListener(new OnClickListener() {
			// To open the map
			@Override
			public void onClick(View v) {
				// Intent for opening the driver detail
				Intent intent = new Intent(DriverProfileActivity.this,
						DriverMainActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driver_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.addJourney) {

			Intent intent = new Intent(this, MonitoringActivity.class);
			startActivity(intent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
