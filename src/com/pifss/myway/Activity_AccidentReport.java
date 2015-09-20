package com.pifss.myway;

import org.json.JSONArray;
import org.json.JSONException;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_AccidentReport extends Activity {
	// define two flags minor & major 
	boolean minorFlag = false;
	boolean majorFlag = false;
	final static String PREF_NAME = "reportlist";
	SharedPreferences prefs;
	private Editor editor;
	String slon ;
	  String slat;
	  
	  float dLat;
	  float dLng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__accident_report);
		
		final ImageView minor = (ImageView) findViewById(R.id.minor);
		final ImageView major = (ImageView) findViewById(R.id.major);
		final EditText ETcomment = (EditText) findViewById(R.id.accidenttext);

		Button submit = (Button) findViewById(R.id.button1);
		
		
	
		
	
			
		
		minor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//  if minor button clicked set the minor flag to true and major to false (this will help to set the key name in the sharedprefrence)
				minorFlag = true;
				majorFlag = false;
				// set the image of minor to selected and major un-selected
				minor.setImageResource(R.drawable.minor_selected);
				major.setImageResource(R.drawable.major);
				
			}
		});
		
		major.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//  if major button clicked set the major flag to true and minor to false (this will help to set the key name in the sharedprefrence)
				minorFlag = false;
				majorFlag = true;
				// set the image of major to selected and minor un-selected
				minor.setImageResource(R.drawable.minor);
				major.setImageResource(R.drawable.major_selected);	
			}
		});
		
		
		// submit will get the comment from the user and saved in the sharedprefrence file 
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// check which button the user clicked minor or major
				// if user choose minor then create key"minor_Accient" in sharedprefrence file 
				if(minorFlag){
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
					Editor editor=prefs.edit();
					String comment=ETcomment.getText().toString();
					String commentsString=prefs.getString("Minor_Accident","[]");
					JSONArray arrayJson=null;
					try {
						arrayJson = new JSONArray(commentsString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					arrayJson.put(comment);
					
					String arrayAsString=arrayJson.toString();
					editor.putString("Minor_Accident", arrayAsString);
					
			        
			        editor.commit();
			        
			        ETcomment.setText("");
			        
					
				}
				
				// if user choose major then create key"major_Accient" in sharedprefrence file 
				else if(majorFlag){
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
				 Editor editor=prefs.edit();
					String comment=ETcomment.getText().toString();
					String commentsString=prefs.getString("Major_Accident","[]");
					JSONArray arrayJson=null;
					try {
						arrayJson = new JSONArray(commentsString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					arrayJson.put(comment);
					
					String arrayAsString=arrayJson.toString();
		
//
//					LocationResult locationResult = new LocationResult(){
//		    		    @Override
//		    		    public void gotLocation(Location location){
//		    		        //Got the location!
//		    		    	 slat = String.valueOf(location.getLatitude());
//		    		    	 slon = String.valueOf(location.getLongitude());
//		    		    	 
//		    		    	 dLat = (float) location.getLatitude();
//		    		    	 dLng = (float) location.getLongitude();
//		    		    	 
//		    		    	
//		    		    	 System.out.println("my lat + lng: " + dLat+"," + dLng);
//		 		    		editor.putString("lon", slon);
//		 		    		editor.putString("lat", slat);
//
//		    		    	System.out.println("location are: "+location);
//		    		    	
//		    		    }
//		    		};
//		    		MyLocation myLocation = new MyLocation();
//		    		myLocation.getLocation(Activity_AccidentReport.this, locationResult);
//		    		
//		    		
//		    		
//		    	//	Toast.makeText(Activity_AccidentReport.this, slat+"ans "+slon, Toast.LENGTH_LONG).show();
					editor.putString("Major_Accident", arrayAsString);
					
			        editor.commit();
			        
			        ETcomment.setText("");
			        
			        
					
				}
				finish();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__accident_report, menu);
		return true;
	}

}
