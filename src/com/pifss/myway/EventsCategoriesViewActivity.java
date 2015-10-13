package com.pifss.myway;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

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

import com.pifss.myway.FavoritesViewActivity.RetrieveFavsTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EventsCategoriesViewActivity extends Activity {

	ArrayList<String> catNames = new ArrayList<String>();
//	public final static String PREF_NAME = "userInformation";
	ArrayList<String> eventsNames=new ArrayList<String>();
	static ArrayList<Event> eventsList = new ArrayList<Event>();
	ListView lvEvents;
	public final static String PREF_NAME = "userInformation";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_categories_view);
		
		catNames.add("Mall");
		catNames.add("Concerts");
		catNames.add("Conventions");
		
		eventsList.clear();
		eventsNames.clear();
		new RetrieveFavsTask().execute();
		
		//***********Controller********************//
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventsCategoriesViewActivity.this, android.R.layout.simple_list_item_1, catNames);
		
		//***************View****************//
		lvEvents = (ListView) findViewById(R.id.listViewCategoryEvents);
				
		lvEvents.setAdapter(adapter);
		
		lvEvents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(EventsCategoriesViewActivity.this, EventsViewActivity.class);
				i.putExtra("cat", catNames.get(position));
				startActivity(i);
			}
		
		});
		
		Button bReq = (Button) findViewById(R.id.buttonReqEvent);
		
		final SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
		if (prefs.getBoolean("isLoggedIn", false)){
			bReq.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(EventsCategoriesViewActivity.this, RequestEventActivity.class);
					startActivity(i);
				}
			});
		} else {
			bReq.setText("Request Event (Log in needed!)");
		}
		
		
		//use the following line to add the sliding menu to the current page
				SlidingUtil.SetSliding(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events_categories_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//to send the user to the home page when pressing the back button
			@Override
		    public void onBackPressed() {
		    	// TODO Auto-generated method stub
		    	super.onBackPressed();
		    	Intent i = new Intent(EventsCategoriesViewActivity.this, Home.class);
				startActivity(i);
				finish();
		    }

class RetrieveFavsTask extends AsyncTask<String, Integer, String>{
        
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
			
				URI u=new URI("http://172.16.8.105:8080/MyWayWeb/viewAllEvents");
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
			        JSONObject FavObject = jsonArray.getJSONObject(i);
			        String Fname = FavObject.getString("name");
			        String Fcat = FavObject.getString("category");
			        String Fdes = FavObject.getString("description");
			        String Flat = FavObject.getString("latitude");
			        String Flong = FavObject.getString("longitude");
			        String Fimage = FavObject.getString("image");
			        String FSdate = FavObject.getString("startDate");
			        String FEdate = FavObject.getString("endDate");
			        Log.d("test:", FavObject.toString());
			       Event e = new Event(Fname, Fdes, Fcat, Flat, Flong, FSdate, FEdate, Fimage);
			        eventsList.add(e);
			    }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//set the names in the eventsnames list to be displayed in the listView 
			for (int i = 0; i< eventsList.size(); i++){
				Event e = eventsList.get(i);
				String name = e.getName();
				Log.d("test:", e.getName());
				
				eventsNames.add(name);
			}
			
//			dialog.dismiss();
			
		
		}
}
}
