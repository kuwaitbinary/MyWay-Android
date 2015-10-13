package com.pifss.myway;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class DriverDestenationList extends Activity {
	static ArrayList<Driver> driverDesList = new ArrayList<Driver>();
	TextView tvDestination ;
	LocationManager lm ;
	Location location ;
	Calendar cal ;
	Date currentLocalTime ;
	SimpleDateFormat date ;
	int i = 0;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_destenation_list);
		
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));
		currentLocalTime = cal.getTime();
		date = new SimpleDateFormat("HH:mm a");
		
		tvDestination = (TextView) findViewById(R.id.DestinationLocationDriver);
		new RetrieveDestinationsTask().execute();
        tvDestination.setText("Press Here To Show Your Destenation");
	new postDriverDashBoardTask().execute();
		
//        final Thread t=new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(true) {
//					
////					runOnUiThread(new Runnable() {
////						public void run() {
////							new postDriverReportTask().execute();
//			
//						        
//							
////						}
////					});
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
//				
//			}
//		});
        
        
        tvDestination.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(DriverDestenationList.this, DriverMainActivity.class);
				startActivity(i);
				
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

	public float getBatteryLevel() {
		Intent batteryIntent = getApplicationContext().registerReceiver(null,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		// Error checking that probably isn't needed but I added just in case.
		if (level == -1 || scale == -1) {
			return 50.0f;
		}

		return ((float) level / (float) scale) * 100.0f;
	}

	

	public void getLat() {
		if (location != null) {
			
			double lat = location.getLatitude();
		}
	}
	public void getLon() {
		if (location != null) {
			
			double lon = location.getLongitude();
		}
	}

	public final static String PREF_NAME = "userInformation";

	class postDriverDashBoardTask extends AsyncTask<String, Integer, String> {
		
        String localTime = date.format(currentLocalTime);
        
		ProgressDialog dialog = new ProgressDialog(DriverDestenationList.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setTitle("Sending Request");
			dialog.setMessage("Sending....");
			dialog.show();
			
			
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			lm =  (LocationManager) context.getSystemService(LOCATION_SERVICE);
			location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));
			currentLocalTime = cal.getTime();
			date = new SimpleDateFormat("HH:mm a");
			try {
				URI u = new URI("http://172.16.8.105:8080/MyWayWeb/setDriverDashBoard");
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(u);
				ArrayList<BasicNameValuePair> urlparameters = new ArrayList<BasicNameValuePair>();

				SharedPreferences pref = getSharedPreferences(PREF_NAME,MODE_APPEND);
				String userObj = pref.getString("user", "ERROR");
				String tempUsername = "";
				
				JSONObject userJson;
				try {
					userJson = new JSONObject(userObj);
					tempUsername = userJson.getString("username");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				float battreyPrc = getBatteryLevel();
//				 if (battreyPrc == 100) {
//					String battreyPrec = String.valueOf(getBatteryLevel());
//					String lon = String.valueOf(location.getLongitude());
//					String lat = String.valueOf(location.getLatitude());
//					urlparameters.add(new BasicNameValuePair("currentLat", lat));
//					urlparameters.add(new BasicNameValuePair("currentLon", lon));
//					urlparameters.add(new BasicNameValuePair("batteryStatus", battreyPrec));
//					urlparameters.add(new BasicNameValuePair("userName",tempUsername));
//					
//				} else if (battreyPrc == 70) {
//					String battreyPrec = String.valueOf(getBatteryLevel());
//					String lon = String.valueOf(location.getLongitude());
//					String lat = String.valueOf(location.getLatitude());
//					urlparameters.add(new BasicNameValuePair("currentLat", lat));
//					urlparameters.add(new BasicNameValuePair("currentLon", lon));
//					urlparameters.add(new BasicNameValuePair("batteryStatus", battreyPrec));
//					urlparameters.add(new BasicNameValuePair("userName",tempUsername));
//				} else if (battreyPrc == 40) {
//					String battreyPrec = String.valueOf(getBatteryLevel());
//					String lon = String.valueOf(location.getLongitude());
//					String lat = String.valueOf(location.getLatitude());
//					urlparameters.add(new BasicNameValuePair("currentLat", lat));
//					urlparameters.add(new BasicNameValuePair("currentLon", lon));
//					urlparameters.add(new BasicNameValuePair("batteryStatus", battreyPrec));
//					urlparameters.add(new BasicNameValuePair("userName",tempUsername));
//				} else if (battreyPrc == 20) {
//					String battreyPrec = String.valueOf(getBatteryLevel());
//					String lon = String.valueOf(location.getLongitude());
//					String lat = String.valueOf(location.getLatitude());
//					urlparameters.add(new BasicNameValuePair("currentLat", lat));
//					urlparameters.add(new BasicNameValuePair("currentLon", lon));
//					urlparameters.add(new BasicNameValuePair("batteryStatus", battreyPrec));
//					urlparameters.add(new BasicNameValuePair("userName",tempUsername));
//				}
				String battreyPrec = String.valueOf(getBatteryLevel());
				String lon = String.valueOf(location.getLongitude());
				String lat = String.valueOf(location.getLatitude());
				urlparameters.add(new BasicNameValuePair("currentLat", lat));
				urlparameters.add(new BasicNameValuePair("currentLon", lon));
				urlparameters.add(new BasicNameValuePair("batteryStatus", battreyPrec));
				urlparameters.add(new BasicNameValuePair("userName",tempUsername));
				post.setEntity(new UrlEncodedFormEntity(urlparameters));
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);
				
				return data;
				

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			location = null;
			Log.d("test: ", result);
			dialog.dismiss();
			
			finish();
		}
		

	}
//	
//	class postDriverReportTask extends AsyncTask<String, Integer, String> {
//		String localTime = date.format(currentLocalTime);
//		ProgressDialog dialog = new ProgressDialog(DriverDestenationList.this);
//		
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			dialog.setTitle("Sending Request");
//			dialog.setMessage("Sending....");
//			dialog.show();
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			lm =  (LocationManager) context.getSystemService(LOCATION_SERVICE);
//			location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//			cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));
//			currentLocalTime = cal.getTime();
//			date = new SimpleDateFormat("HH:mm a");
//			try {
//				URI u = new URI("");
//				DefaultHttpClient client = new DefaultHttpClient();
//				HttpPost post  = new HttpPost(u);
//				ArrayList<BasicNameValuePair> urlparameters = new ArrayList<BasicNameValuePair>();
//
//				
//				
//
//				SharedPreferences pref = getSharedPreferences(PREF_NAME,
//						MODE_APPEND);
//				String userObj = pref.getString("user", "ERROR");
//				String tempUsername = "";
//				JSONObject userJson;
//				try {
//					userJson = new JSONObject(userObj);
//					tempUsername = userJson.getString("username");
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String lon = String.valueOf(location.getLongitude());
//				String lat = String.valueOf(location.getLatitude());
//
//				String overSpeed = "overSpeed";
//				String stops = "stop";
//				for (i=0;i<3;i++){
//					if(i==1){
//						urlparameters.add(new BasicNameValuePair("Reason", overSpeed));
//					}else{
//						urlparameters.add(new BasicNameValuePair("Reason", stops));
//					}
//				}
//				urlparameters.add(new BasicNameValuePair("latitude", lat));
//				urlparameters.add(new BasicNameValuePair("longitude", lon));
//				urlparameters.add(new BasicNameValuePair("username",tempUsername));
//				urlparameters.add(new BasicNameValuePair("currentTime", localTime));
//				
//				
//				
//				post.setEntity(new UrlEncodedFormEntity(urlparameters));
//
//				HttpResponse response = client.execute(post);
//				HttpEntity entity = response.getEntity();
//				String data = EntityUtils.toString(entity);
//
//				
//				return data;
//
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			
//			Log.d("test: ", result);
//			dialog.dismiss();
//			
//			finish();
//		}
//
//	}


}
class RetrieveDestinationsTask extends AsyncTask<String, Integer, String>{

	
    
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();		
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		try {
			
			ArrayList<BasicNameValuePair> urlparameters=new ArrayList<BasicNameValuePair>();
		
			URI u=new URI("http://172.16.8.105:8080/MyWayWeb/getDestination");
			DefaultHttpClient client=new DefaultHttpClient();
			
			HttpPost post = new HttpPost(u);
			urlparameters.add(new BasicNameValuePair("driverUserName", "ahmed"));
			post.setEntity(new UrlEncodedFormEntity(urlparameters));
			
			HttpResponse response=client.execute(post);
			HttpEntity entity=response.getEntity();
		    String data=EntityUtils.toString(entity);
			

			return data;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	
	@Override
	protected void onPostExecute(String result) {
		
		// TODO Auto-generated method stub
		super.onPostExecute(result);			
		//convert the json string to arraylist of events
		
		System.out.print(result);
		
		try {
			JSONObject jsnobject = new JSONObject(result);
			JSONArray jsonArray = new JSONArray(jsnobject.getString("result_data"));
			
			System.out.print(jsonArray);
		    for (int i = 0; i < jsonArray.length(); i++) {
		        JSONObject desObject = jsonArray.getJSONObject(i);
//		        String userN = desObject.getString("userName");
//		        String driverdate = desObject.getString("date");
//		        String dTime = desObject.getString("Time");
		        String dStartLat = desObject.getString("destination_Lat");
		        String dStartLon = desObject.getString("destination_Lon");
		     System.out.print(dStartLon);
		       
		        //Log.d("test:", desObject.toString());
		       Driver d = new Driver();
		       d.setStartlat(dStartLat);
		       d.setStartlon(dStartLon);
//		       d.setUserName(userN);
//		       
//		       d.setDay(driverdate);
//		       d.setTime(dTime);
		    }
		    
		}catch(JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
}
