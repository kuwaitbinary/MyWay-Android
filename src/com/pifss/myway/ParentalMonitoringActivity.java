package com.pifss.myway;

import java.util.ArrayList;

import com.pifss.myway.Driver;
import com.pifss.myway.DriverAdapter;
import com.pifss.myway.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ParentalMonitoringActivity extends Activity {
	
	@Override
	protected void onResume() {
		
		super.onResume();
		
		ListView driverlv = (ListView) findViewById(R.id.driverList);
        TextView ifNoDriverFound = (TextView) findViewById(R.id.listTitle);
        
        ArrayList<Driver> driverList = ParentalMonitoringConnectionManager.getDriverList();
        DriverAdapter adapter = new DriverAdapter (driverList,this);
        
        driverlv.setEmptyView(ifNoDriverFound);
        
        if (driverList != null) {
        	driverlv.setAdapter(adapter);
        	driverlv.setClickable(true);
        }
    	
        driverlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(ParentalMonitoringActivity.this,DriverProfileActivity.class);
				startActivity(intent);
				
			}
			
		});
    	
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parental_monitoring);
        
      //use the following line to add the sliding menu to the current page
      		SlidingUtil.SetSliding(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.paranet_monitoring, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addDriver) {
        	
        	Intent intent = new Intent(this,ParentMonitoring_Pairing.class);
        	startActivity(intent);
        	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
  //to send the user to the home page when pressing the back button
  	@Override
      public void onBackPressed() {
      	// TODO Auto-generated method stub
      	super.onBackPressed();
      	Intent i = new Intent(ParentalMonitoringActivity.this, Home.class);
  		startActivity(i);
  		finish();
      }
    
}