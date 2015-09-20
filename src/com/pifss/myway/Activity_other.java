package com.pifss.myway;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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

	
	final static String PREF_NAME = "reportlist";
	double lat;
	 double lon;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		
		 Button submit = (Button) findViewById(R.id.button1);
//		 Button cancel = (Button) findViewById(R.id.button2);
		 
		final ImageView otherimg = (ImageView) findViewById(R.id.other);
		final EditText othertext = (EditText) findViewById(R.id.othertext); 
		
		otherimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				otherimg.setImageResource(R.drawable.other_selected);
				
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
				Editor editor=prefs.edit();
				String comment=othertext.getText().toString();
				String commentsString=prefs.getString("other","[]");
				JSONArray arrayJson=null;
				try {
					arrayJson = new JSONArray(commentsString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				arrayJson.put(comment);
				
				String arrayAsString=arrayJson.toString();
				editor.putString("other", arrayAsString);
				
		        
		        editor.commit();
		        
		        finish();
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_other, menu);
		return true;
	}

}
