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

public class Activity_TrafficJam extends Activity {
	
	boolean moderateFlag=false;
	boolean heavyFlag=false;
	boolean standstillFlag=false;
	
	public final static String  PREF_NAME = "reportlist";
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_jam);
		final ImageView moderate = (ImageView) findViewById(R.id.moderate);
		final ImageView heavy = (ImageView) findViewById(R.id.heavy);
		final ImageView standstill = (ImageView) findViewById(R.id.standstill);
		final EditText TComment= (EditText) findViewById(R.id.comment);
		
		Button submit= (Button) findViewById(R.id.submit);

		
		
		
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
  
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(moderateFlag){
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
					Editor editor=prefs.edit();
					
					String comment=TComment.getText().toString();
					String commentsString=prefs.getString("Moderate","[]");
					JSONArray arrayJson=null;
					try {
						arrayJson = new JSONArray(commentsString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					arrayJson.put(comment);
					
					String arrayAsString=arrayJson.toString();
					editor.putString("Moderate", arrayAsString);
			        
			        editor.commit();
			        
			        TComment.setText("");
			        
			        finish();
					
				}
				
				else if(heavyFlag){
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
					Editor editor=prefs.edit();
					
					String comment=TComment.getText().toString();
					String commentsString=prefs.getString("Heavy","[]");
					JSONArray arrayJson=null;
					try {
						arrayJson = new JSONArray(commentsString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					arrayJson.put(comment);
					
					String arrayAsString=arrayJson.toString();
					editor.putString("Heavy", arrayAsString);
			        
			        editor.commit();
			        
			        TComment.setText("");
			        finish();
					
				}
				
				else if (standstillFlag){
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
					Editor editor=prefs.edit();
					
					String comment=TComment.getText().toString();
					String commentsString=prefs.getString("Standstill","[]");
					JSONArray arrayJson=null;
					try {
						arrayJson = new JSONArray(commentsString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					arrayJson.put(comment);
					
					String arrayAsString=arrayJson.toString();
					editor.putString("Standstill", arrayAsString);
			        
			        editor.commit();
			        
			        TComment.setText("");
			        finish();
					
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

}
