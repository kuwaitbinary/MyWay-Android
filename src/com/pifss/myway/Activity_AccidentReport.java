package com.pifss.myway;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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

public class Activity_AccidentReport extends Activity {
	// define two flags minor & major 
	boolean minorFlag = false;
	boolean majorFlag = false;
	final static String PREF_NAME = "reportlist";

	Double lat ;
	Double lon;
	String accidentComment = "";
	Integer typeId = 0;
	  String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__accident_report);
		
		final ImageView minor = (ImageView) findViewById(R.id.minor);
		final ImageView major = (ImageView) findViewById(R.id.major);
		final EditText ETcomment = (EditText) findViewById(R.id.accidenttext);

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
				// check which button the user clicked minor or major
				// if user choose minor then create key"minor_Accient" in sharedprefrence file 
				if(minorFlag){
					accidentComment = ETcomment.getText().toString();
					typeId = 7;
					new DownloadTask().execute();
					
					minorFlag = false;
					minor.setImageResource(R.drawable.minor);
					ETcomment.setText("");
					Intent i=new Intent(Activity_AccidentReport.this,Activity_TrafficMain.class);
					startActivity(i);
			}
		
				
				// if user choose major then create key"major_Accient" in sharedprefrence file 
				else if(majorFlag){
					accidentComment = ETcomment.getText().toString();
					typeId = 8;
					new DownloadTask().execute();
					
				majorFlag = false;
				major.setImageResource(R.drawable.major);
				ETcomment.setText("");
				Intent i=new Intent(Activity_AccidentReport.this,Activity_TrafficMain.class);
				startActivity(i);
					
				}
				else {
					AlertDialog alertDialog = new AlertDialog.Builder(Activity_AccidentReport.this).create();
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
		getMenuInflater().inflate(R.menu.activity__accident_report, menu);
		return true;
	}
	
	// async class 
	
		class DownloadTask extends AsyncTask<String, Integer, String>{
	        
			
			@Override
			protected void onPreExecute() {

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
					if(accidentComment.equals("")){
						
							l.add(new BasicNameValuePair("report_comments","Caution! there is an accident ahead"));	
					}
					else{
					
							l.add(new BasicNameValuePair("report_comments",accidentComment ));
					
					}
					
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
				Toast.makeText(Activity_AccidentReport.this, result, Toast.LENGTH_LONG).show();
				
			}
			
			
		}
		
		
	
	

}
