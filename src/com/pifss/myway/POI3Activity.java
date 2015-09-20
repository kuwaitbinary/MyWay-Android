package com.pifss.myway;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class POI3Activity extends Activity {
	ArrayList<String> POI=new ArrayList<String>();
	ArrayList<String> POIDesc=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi3_detail);
		TextView poiName = (TextView) findViewById(R.id.textViewName);
		TextView poiType = (TextView) findViewById(R.id.textViewType);
		TextView poiDescription = (TextView) findViewById(R.id.textViewDescription);
		Button poiNaviBtn = (Button) findViewById(R.id.buttonNavi);
		
       String name= getIntent().getExtras().getString("Name");	
       String description= getIntent().getExtras().getString("Description");	
			
		poiName.setText(name);
		poiType.setText("");
		poiDescription.setText(description);
		poiNaviBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
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
}
