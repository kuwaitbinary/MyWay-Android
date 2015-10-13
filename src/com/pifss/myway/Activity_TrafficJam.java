package com.pifss.myway;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pifss.myway.Activity_AccidentReport.DownloadTask;

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

public class Activity_TrafficJam extends Activity {
	
	boolean moderateFlag=false;
	boolean heavyFlag=false;
	boolean standstillFlag=false;
	
	public final static String  PREF_NAME = "reportlist";
	Double lat;
	Double lon;
	String trafficComment = "";
	Integer typeId=0;
	String name;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_jam);
		final ImageView moderate = (ImageView) findViewById(R.id.moderate);
		final ImageView heavy = (ImageView) findViewById(R.id.heavy);
		final ImageView standstill = (ImageView) findViewById(R.id.standstill);
		final EditText TComment= (EditText) findViewById(R.id.comment);
		
		Button submit= (Button) findViewById(R.id.submit);
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
		
		
		
		moderate.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				moderateFlag=true;
				heavyFlag = false;
				standstillFlag=false;
				
				moderate.setImageResource(R.drawable.moderate_selected);
				heavy.setImageResource(R.drawable.heavy);
				standstill.setImageResource(R.drawable.standstill);

			}
		});
		
		heavy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				moderateFlag=false;
				heavyFlag = true;
				standstillFlag=false;
				
				moderate.setImageResource(R.drawable.moderate);
				heavy.setImageResource(R.drawable.heavy_selected);
				standstill.setImageResource(R.drawable.standstill);
			}
		});
		
		
		standstill.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				moderateFlag  = false;
				heavyFlag     = false;
				standstillFlag= true;
				
				moderate.setImageResource(R.drawable.moderate);
				heavy.setImageResource(R.drawable.heavy);
				standstill.setImageResource(R.drawable.standstill_selected);
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
				// TODO Auto-generated method stub
				
				if(moderateFlag){
					trafficComment = TComment.getText().toString();
					typeId = 4;
					new DownloadTask().execute();	
					moderateFlag = false;
					moderate.setImageResource(R.drawable.moderate);
					TComment.setText("");
					Intent i=new Intent(Activity_TrafficJam.this,Activity_TrafficMain.class);
					startActivity(i);
					
				}
				
				else if(heavyFlag){
					trafficComment = TComment.getText().toString();
					typeId = 5;
					new DownloadTask().execute();	
						
				heavyFlag = false;
				heavy.setImageResource(R.drawable.heavy);
			    TComment.setText("");
				Intent i=new Intent(Activity_TrafficJam.this,Activity_TrafficMain.class);
				startActivity(i);
				}
				
				else if (standstillFlag){
					trafficComment = TComment.getText().toString();
					typeId = 6;
					new DownloadTask().execute();
				standstillFlag = false;
				standstill.setImageResource(R.drawable.standstill);
			    TComment.setText("");
				Intent i=new Intent(Activity_TrafficJam.this,Activity_TrafficMain.class);
				startActivity(i);
				}
				else{
					AlertDialog alertDialog = new AlertDialog.Builder(Activity_TrafficJam.this).create();
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
		getMenuInflater().inflate(R.menu.activity_traffic_jam, menu);
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
					if(trafficComment.equals("")){
						
							l.add(new BasicNameValuePair("report_comments","Caution! there is something on the road"));	
					}
					else{
					
							l.add(new BasicNameValuePair("report_comments",trafficComment ));
					
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
				Toast.makeText(Activity_TrafficJam.this, result, Toast.LENGTH_LONG).show();
				
			}
			
			
		}
		
		
		

}
