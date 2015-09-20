package com.pifss.myway;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class POI1Activity extends Activity {
	ListView lv;
	ArrayList<String> POI=new ArrayList<String>();
	ArrayList<String> POIDesc=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi1);
		
		lv=(ListView) findViewById(R.id.listView1);
		getPOIListName();
		getPOIListDescrition();
		
		final POIListAdapter adapter = new POIListAdapter(POI, POIDesc,this);
		
//		 

		lv.setAdapter(adapter);
//		 runOnUiThread(new Runnable() {
//	            public void run() {
	                adapter.notifyDataSetChanged();
//            }
//	        });
		    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,long arg3) {
			Intent intent = new Intent(POI1Activity.this, POI3Activity.class);
	             //Get the value of the item you clicked
			
             int itemClickedname = (Integer) arg0.getAdapter().getItem(pos);
             
             String itemName =getPOIListName().get(itemClickedname);
	             intent.putExtra("Name", itemName);
//	             intent.putExtra("Name", itemClickedDesc);
	             Toast.makeText(POI1Activity.this, "Item CLicked is ", Toast.LENGTH_LONG).show();
	             startActivity(intent);
			}
		});
		    
		  //use the following line to add the sliding menu to the current page
			SlidingUtil.SetSliding(this);
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case R.id.menu_Add:
            // ProjectsActivity is my 'home' activity
        	String PREF_NAME = "userInformation"; 
        	SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
			if(prefs.getBoolean("isLoggedIn", false)){//if logged in he can add new POI
				Intent i = new Intent(POI1Activity.this, POI2Activity.class);
				startActivity(i);
				finish();
			} else {
				Toast.makeText(this, "Must be logged in to suggest a new point.", Toast.LENGTH_LONG).show();
			}
			
    		
            return true;
    }
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_poi1, menu);
		return true;
	}
	
	public ArrayList<String> getPOIListName(){
		
		String key="Name";
		SharedPreferences prefs = getSharedPreferences(POI2Activity.PREF_NAME, MODE_APPEND);
	
		String  POIs=prefs.getString(key,"[]");
		JSONArray arrayJson=null;
	
		
		try {
			arrayJson = new JSONArray(POIs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < arrayJson.length(); i++) {
			try {
				String item=arrayJson.getString(i);
                POI.add(item);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return POI;
	}


	public ArrayList<String> getPOIListDescrition(){
		String key="Description";
		SharedPreferences prefs = getSharedPreferences(POI2Activity.PREF_NAME, MODE_APPEND);
	
		String  POIs=prefs.getString(key,"[]");
		JSONArray arrayJson=null;
	
		
		try {
			arrayJson = new JSONArray(POIs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < arrayJson.length(); i++) {
			try {
				String item=arrayJson.getString(i);
                POIDesc.add(item);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return POI;
	}

	 //to send the user to the home page when pressing the back button
		@Override
	    public void onBackPressed() {
	    	// TODO Auto-generated method stub
	    	super.onBackPressed();
	    	Intent i = new Intent(POI1Activity.this, Home.class);
			startActivity(i);
			finish();
	    }

}
