package com.pifss.myway;

import org.json.JSONArray;
import org.json.JSONException;

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

public class Activity_hazard extends Activity {
	// define two flags 
	boolean constructionFlag = false;
	boolean onroadFlag = false;
	// shared preference file name 
	final static String PREF_NAME = "reportlist";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hazard);
		
		final ImageView construction = (ImageView) findViewById(R.id.construction);
		final ImageView onroad = (ImageView) findViewById(R.id.onroad);
		final EditText ETcomment = (EditText) findViewById(R.id.hazardtext);

		Button submit = (Button) findViewById(R.id.button1);

		

		
		
		
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
		
		
		// submit will get the comment from the user and saved in the sharedprefrence file 
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// check which button the user clicked construction or onroad
				// if user choose construction then create key"construction" in sharedprefrence file
				if(constructionFlag){
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
					Editor editor=prefs.edit();
					String comment=ETcomment.getText().toString();
					
					String commentsString=prefs.getString("Construction","[]");
					
					JSONArray arrayJson=null;
					try {
						arrayJson = new JSONArray(commentsString);
					} catch (JSONException e) {
						e.printStackTrace();
					} 
					arrayJson.put(comment);
					String arrayAsString=arrayJson.toString();
					editor.putString("Construction", arrayAsString);
			        
			        editor.commit();
			        
			        ETcomment.setText("");
			        finish();
				}
				// if user choose onroad then create key"construction" in sharedprefrence file

				else if(onroadFlag){
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
					Editor editor=prefs.edit();
					String comment=ETcomment.getText().toString();
					String commentsString=prefs.getString("onRoad","[]");
					JSONArray arrayJson=null;
					try {
						arrayJson = new JSONArray(commentsString);
					} catch (JSONException e) {
						e.printStackTrace();
					} 
					arrayJson.put(comment);
					String arrayAsString=arrayJson.toString();
					editor.putString("onRoad", arrayAsString);
			        
			        editor.commit();
			        
			        ETcomment.setText("");	
			        finish();
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

}
