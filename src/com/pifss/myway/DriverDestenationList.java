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

public class DriverDestenationList extends Activity {
	static ArrayList<Driver> driverDesList = new ArrayList<Driver>();
	TextView tvDestination = (TextView) findViewById(R.id.DestinationLocationDriver);
	LocationManager lm = (LocationManager) this
			.getSystemService(LOCATION_SERVICE);
	Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));
	Date currentLocalTime = cal.getTime();
	SimpleDateFormat date = new SimpleDateFormat("HH:mm a");
	int i = 0;
	// you can get seconds by adding "...:ss" to it

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_destenation_list);
		
		new postDriverDashBoardTask().execute();
        final Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					new postDriverReportTask().execute();
					new RetrieveDestinationsTask().execute();
//					runOnUiThread(new Runnable() {
//						public void run() {
//							new postDriverReportTask().execute();
//							new RetrieveDestinationsTask().execute();
//						}
//					});
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
        
        
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
			try {
				URI u = new URI(" ");
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
				 if (battreyPrc == 90) {
					String battreyPrec = String.valueOf(getBatteryLevel());
					String lon = String.valueOf(location.getLongitude());
					String lat = String.valueOf(location.getLatitude());
					urlparameters.add(new BasicNameValuePair("latitude", lat));
					urlparameters.add(new BasicNameValuePair("longitude", lon));
					urlparameters.add(new BasicNameValuePair("BatteryPrc", battreyPrec));
					urlparameters.add(new BasicNameValuePair("username",tempUsername));
					urlparameters.add(new BasicNameValuePair("currentTime", localTime));
				} else if (battreyPrc == 70) {
					String battreyPrec = String.valueOf(getBatteryLevel());
					String lon = String.valueOf(location.getLongitude());
					String lat = String.valueOf(location.getLatitude());
					urlparameters.add(new BasicNameValuePair("latitude", lat));
					urlparameters.add(new BasicNameValuePair("longitude", lon));
					urlparameters.add(new BasicNameValuePair("BatteryPrc", battreyPrec));
					urlparameters.add(new BasicNameValuePair("username",tempUsername));
					urlparameters.add(new BasicNameValuePair("currentTime", localTime));
				} else if (battreyPrc == 40) {
					String battreyPrec = String.valueOf(getBatteryLevel());
					String lon = String.valueOf(location.getLongitude());
					String lat = String.valueOf(location.getLatitude());
					urlparameters.add(new BasicNameValuePair("latitude", lat));
					urlparameters.add(new BasicNameValuePair("longitude", lon));
					urlparameters.add(new BasicNameValuePair("BatteryPrc", battreyPrec));
					urlparameters.add(new BasicNameValuePair("username",tempUsername));
					urlparameters.add(new BasicNameValuePair("currentTime", localTime));
				} else if (battreyPrc == 20) {
					String battreyPrec = String.valueOf(getBatteryLevel());
					String lon = String.valueOf(location.getLongitude());
					String lat = String.valueOf(location.getLatitude());
					urlparameters.add(new BasicNameValuePair("latitude", lat));
					urlparameters.add(new BasicNameValuePair("longitude", lon));
					urlparameters.add(new BasicNameValuePair("BatteryPrc", battreyPrec));
					urlparameters.add(new BasicNameValuePair("username",tempUsername));
					urlparameters.add(new BasicNameValuePair("currentTime", localTime));
				}
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
	
	class postDriverReportTask extends AsyncTask<String, Integer, String> {
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
			try {
				URI u = new URI("");
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost post  = new HttpPost(u);
				ArrayList<BasicNameValuePair> urlparameters = new ArrayList<BasicNameValuePair>();

				
				

				SharedPreferences pref = getSharedPreferences(PREF_NAME,
						MODE_APPEND);
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
				String lon = String.valueOf(location.getLongitude());
				String lat = String.valueOf(location.getLatitude());

				String overSpeed = "overSpeed";
				String stops = "stop";
				for (i=0;i<3;i++){
					if(i==1){
						urlparameters.add(new BasicNameValuePair("Reason", overSpeed));
					}else{
						urlparameters.add(new BasicNameValuePair("Reason", stops));
					}
				}
				urlparameters.add(new BasicNameValuePair("latitude", lat));
				urlparameters.add(new BasicNameValuePair("longitude", lon));
				urlparameters.add(new BasicNameValuePair("username",tempUsername));
				urlparameters.add(new BasicNameValuePair("currentTime", localTime));
				
				
				
				post.setEntity(new UrlEncodedFormEntity(urlparameters));

				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);

				// Log.d("test:", data);
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
			
			Log.d("test: ", result);
			dialog.dismiss();
			
			finish();
		}

	}
class RetrieveDestinationsTask extends AsyncTask<String, Integer, String>{
        
//		ProgressDialog dialog=new ProgressDialog(EventsCategoriesViewActivity.this);
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			dialog.setTitle("Loading Events");
//			dialog.setMessage("Loading....");
//			dialog.show();
		}
		
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				
				ArrayList<BasicNameValuePair> urlparameters=new ArrayList<BasicNameValuePair>();
			
				URI u=new URI("");
				DefaultHttpClient client=new DefaultHttpClient();
				
				HttpPost post = new HttpPost(u);
				
				post.setEntity(new UrlEncodedFormEntity(urlparameters));
				
				HttpResponse response=client.execute(post);
				HttpEntity entity=response.getEntity();
			    String data=EntityUtils.toString(entity);
				
//			    Log.d("test:", data);
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
//			Log.d("test: ", result);
			//convert the json string to arraylist of events
			
			try {
				JSONObject jsnobject = new JSONObject(result);
				JSONArray jsonArray = jsnobject.getJSONArray("result_data");
			    for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject desObject = jsonArray.getJSONObject(i);
			        String userN = desObject.getString("userName");
			        String driverdate = desObject.getString("date");
			        String dTime = desObject.getString("Time");
			        String dStartLat = desObject.getString("Startlatitude");
			        String dStartLon = desObject.getString("Startlongitude");
			        String dEndLat = desObject.getString("Endlatitude");
			        String dEndLon = desObject.getString("endlongitude");
			       
			        Log.d("test:", desObject.toString());
			       Driver d = new Driver();
			       d.setStartlat(dStartLat);
			       d.setStartlon(dStartLon);
			       d.setUserName(userN);
			       d.setEndlon(dEndLon);
			       d.setEndlat(dEndLat);
			       d.setDay(driverdate);
			       d.setTime(dTime);
			    }
			    if( jsnobject != null){
			        tvDestination.setText("Press Here To Show Your Destenation");
			        }
			}catch(JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
//			dialog.dismiss();
			
		
		}
}

}
