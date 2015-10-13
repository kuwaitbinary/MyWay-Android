package com.pifss.myway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pifss.myway.Activity_other.DownloadTask;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_hazard extends Activity {
	// define two flags 
	boolean constructionFlag = false;
	boolean onroadFlag = false;
	// shared preference file name 
	final static String PREF_NAME = "reportlist";
	Double lat ;
	Double lon;
	String hazardComment = "";
	Integer typeId = 0;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hazard);
		
		final ImageView construction = (ImageView) findViewById(R.id.construction);
		final ImageView onroad = (ImageView) findViewById(R.id.onroad);
		final EditText ETcomment = (EditText) findViewById(R.id.hazardtext);

		Button submit = (Button) findViewById(R.id.button1);
		 final InformationManager imm = new InformationManager(this);
			
			JSONObject userJson;
			
			userJson = imm.getUserInformation();
			try {
				 name = userJson.getString("username");
				System.out.println("name is " + name);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// set on click listener for  construction image view 
		construction.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		//  if construction button clicked set the construction flag to true and onroad to false (this will help to set the key name in the sharedprefrence)
				constructionFlag = true;
				onroadFlag = false ;
			// set the image of minor to selected and major un-selected
				construction.setImageResource(R.drawable.construction_selected);
				onroad.setImageResource(R.drawable.onroad);		
			}
		});
		
		// set on click listener for  onroad image view 
		onroad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//  if onroad button clicked set the construction flag to true and construction to false (this will help to set the key name in the sharedprefrence)
				onroadFlag = true ;			
				constructionFlag = false;
					
				// set the image of minor to selected and major un-selected
				onroad.setImageResource(R.drawable.onroad_selected);				
				construction.setImageResource(R.drawable.construction);	
			}
		});
		
		
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 2000,5, new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				lat = location.getLatitude();
				lon = location.getLongitude();
		
				
			}
		});
	
		
		// submit will get the comment from the user and saved in the sharedprefrence file 
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// check which button the user clicked construction or onroad
				// if user choose construction then create key"construction" in sharedprefrence file
				if(constructionFlag){
					typeId=2;
				hazardComment = ETcomment.getText().toString();
				new DownloadTask().execute();
					
				constructionFlag = false;
				construction.setImageResource(R.drawable.construction);
				ETcomment.setText("");
				Intent i=new Intent(Activity_hazard.this,Activity_TrafficMain.class);
				startActivity(i);
			}
		
				// if user choose onroad then create key"construction" in sharedprefrence file

				else if(onroadFlag){
					typeId=3;
					hazardComment = ETcomment.getText().toString();
					new DownloadTask().execute();
					
				onroadFlag = false;
				onroad.setImageResource(R.drawable.onroad);
				ETcomment.setText("");
				Intent i=new Intent(Activity_hazard.this,Activity_TrafficMain.class);
				startActivity(i);
					
				}
				else {
					AlertDialog alertDialog = new AlertDialog.Builder(Activity_hazard.this).create();
					alertDialog.setTitle("Oops!");
					alertDialog.setMessage("Please click on the icon");
					alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
					    new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) {
					            dialog.dismiss();
					        }
					    });
					alertDialog.show();
				}
			}			
		});
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hazard, menu);
		return true;
	}
	
	
	
	// async class 
	
		class DownloadTask extends AsyncTask<String, Integer, String>{
	        
		
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
	
			}
			
			
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				
				try {
					URI u=new URI("http://172.16.8.105:8080/MyWayWeb/postTrafficReport");
					
					DefaultHttpClient client=new DefaultHttpClient();
					
				
					HttpPost post=new HttpPost(u);	
					ArrayList<BasicNameValuePair> l=new ArrayList<BasicNameValuePair>();
					l.add(new BasicNameValuePair("report_type",typeId.toString()));
					l.add(new BasicNameValuePair("report_lat", lat.toString()));
					l.add(new BasicNameValuePair("report_log", lon.toString()));
					l.add(new BasicNameValuePair("username", name));
					if(hazardComment.equals("")){
						
							l.add(new BasicNameValuePair("report_comments","Caution! there is a hazard ahead"));	
					}
					else{
					
							l.add(new BasicNameValuePair("report_comments",hazardComment ));
					
					}
					
					//Toast.makeText(Activity_hazard.this, "type ID: "+typeId.toString(), Toast.LENGTH_LONG).show();
					
					post.setEntity(new UrlEncodedFormEntity(l));
					
				client.execute(post);
	

					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return null;
			}
			
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				
				super.onPostExecute(result);
				
				Toast.makeText(Activity_hazard.this, result, Toast.LENGTH_LONG).show();
				
			}
			
			
		}
		
		
	
	

}
