package com.pifss.myway;
import java.util.Locale;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends Activity {

	final static String PREF_NAME = "userInformation"; 
	public static JSONArray reports=null;
	ListAllReports lr = new ListAllReports();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		lr.getReports();
		final SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
		
		//setting image resource from durable
				ImageView imageView = (ImageView) findViewById(R.id.imgTraffic);
				imageView.setImageResource((Integer) R.drawable.ic_traffic);
				
				ImageView imageView2 = (ImageView) findViewById(R.id.imgDailyRoute);
				imageView2.setImageResource((Integer) R.drawable.ic_daily);
				
				ImageView imageView3 = (ImageView) findViewById(R.id.imgParentMonitoring);
				imageView3.setImageResource(R.drawable.ic_parent);
				
				ImageView imageView4 = (ImageView) findViewById(R.id.imgMap);
				imageView4.setImageResource(R.drawable.ic_map);
				
				//use the following line to add the sliding menu to the current page
				SlidingUtil.SetSliding(this);
				
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent i;
						if(prefs.getBoolean("isLoggedIn", false)){
							
//							i = new Intent(Home.this, Activity_TrafficMain.class);
							i = new Intent(Home.this, Activity_ReportDetails.class);
						} else {
//							i = new Intent(Home.this, Activity_ViewReport.class);
							i = new Intent(Home.this, Activity_ReportDetails.class);
						}
						startActivity(i);
						finish();
						
					}
				});
				
				
				imageView3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
			        	
						if(prefs.getBoolean("isLoggedIn", false)){//if logged in he can add new POI
							Intent i = new Intent(Home.this, ParentalMonitoringActivity.class);
							startActivity(i);
							finish();
						} else {
							Toast.makeText(Home.this, "Must be logged in to access this feature!", Toast.LENGTH_LONG).show();
						}
					}
				});
				
				imageView2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "Clicked Thired Image",
								Toast.LENGTH_SHORT).show();
						
					}
				});
				
				imageView4.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Uri gmmIntentUri = Uri.parse("geo:29.3697,47.9783");
						Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
						mapIntent.setPackage("com.google.android.apps.maps");
						if (mapIntent.resolveActivity(getPackageManager()) != null) {
						    startActivity(mapIntent);
						}
						
					}
				});
				
				
	}
	//method to check before closing the app
	@Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
//    	super.onBackPressed(); this calls the original back and would still close the app
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialogMessageExit)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       finish();
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        builder.create().show();
    }
	
			
}
