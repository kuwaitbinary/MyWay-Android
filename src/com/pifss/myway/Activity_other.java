package com.pifss.myway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

public class Activity_other extends Activity {
	
//	final static String PREF_NAME = "reportlist";
	boolean otherFlag = false;
	public final static String PREF_NAME = "userInformation";
	Double lat;
	Double lon;
	
	String otherComment="";
	String name="";
	
	@SuppressLint("ServiceCast") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		final  EditText othertext = (EditText) findViewById(R.id.othertext); 
		final  ImageView otherimg = (ImageView) findViewById(R.id.other);
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
		otherimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				otherFlag = true;
				otherimg.setImageResource(R.drawable.other_selected);
				
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
		
	
		submit.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				SharedPreferences prefs ;
				prefs= v.getContext()
					.getSharedPreferences(PREF_NAME,
							v.getContext().MODE_APPEND);
				
				
				
				
				
				if(otherFlag){
					otherComment = othertext.getText().toString();
					new DownloadTask().execute();
					
			Toast.makeText(Activity_other.this, "HELLO", Toast.LENGTH_LONG).show();
			otherFlag = false;
			otherimg.setImageResource(R.drawable.other);
			othertext.setText("");
			Intent i=new Intent(Activity_other.this,Activity_TrafficMain.class);
			startActivity(i);
		
				}
				
				else {
					AlertDialog alertDialog = new AlertDialog.Builder(Activity_other.this).create();
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
		getMenuInflater().inflate(R.menu.activity_other, menu);
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
			URI u = null;
			
			try {
				 u=new URI("http://172.16.8.105:8080/MyWayWeb/postTrafficReport");
				
				DefaultHttpClient client=new DefaultHttpClient();
				
				
			
			
		
				System.out.println("IM IN SYNC TASK name is " + name);
				
				HttpPost post=new HttpPost(u);	
				ArrayList<BasicNameValuePair> l=new ArrayList<BasicNameValuePair>();
				l.add(new BasicNameValuePair("report_type","1"));
				l.add(new BasicNameValuePair("report_lat", lat.toString()));
				l.add(new BasicNameValuePair("report_log", lon.toString()));
				l.add(new BasicNameValuePair("username", name));
				if(otherComment.equals("")){
					
						l.add(new BasicNameValuePair("report_comments","Caution! there is something on the road"));	
				}
				else{
				
						l.add(new BasicNameValuePair("report_comments",otherComment ));
				
				}
				
				System.out.println("hello");
				
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
			Toast.makeText(Activity_other.this, result, Toast.LENGTH_LONG).show();
			
		}
		
		
	}
	
	
	
}
