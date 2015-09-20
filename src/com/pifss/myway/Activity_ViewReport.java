package com.pifss.myway;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;

import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Activity_ViewReport extends Activity {
	ArrayList<String> commentList=new ArrayList<String>();
	
	
	ArrayList<String> moderate=new ArrayList<String>();
	ArrayList<String> heavy=new ArrayList<String>();
	ArrayList<String> standstill=new ArrayList<String>();
	ArrayList<String> minor=new ArrayList<String>();
	ArrayList<String> major=new ArrayList<String>();
	ArrayList<String> construction=new ArrayList<String>();
	ArrayList<String> onroad=new ArrayList<String>();
	ArrayList<String> other=new ArrayList<String>();
	
	ArrayList<Comment> commentTypeList=new ArrayList<Comment>();
	
	
	//String [] keyNames = {"Moderate","Heavy","Standstill"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_report);
		ListView lvReport = (ListView) findViewById(R.id.listView1);

		
		
//		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getComments("Moderate"));
//		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getComments("Heavy"));
//		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getComments("Standstill"));
//		
//		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getComments("Minor_Accident"));
//		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getComments("Major_Accident"));
//
//		ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getComments("Construction"));
//		ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getComments("onRoad"));
		ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,commentList);

		getAllComments("");
//		lvReport.setAdapter(adapter1);
//		lvReport.setAdapter(adapter2);
//		lvReport.setAdapter(adapter3);
//		lvReport.setAdapter(adapter4);
//		lvReport.setAdapter(adapter5);
//		lvReport.setAdapter(adapter6);
//		lvReport.setAdapter(adapter7);
		lvReport.setAdapter(adapter8);
		
		
		lvReport.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Comment c=getComment(commentList.get(pos));
				
				Intent i=new Intent(Activity_ViewReport.this,Activity_ReportDetails.class);
				i.putExtra("comment", c);
				startActivity(i);
				
			}
		});
		       
	}

	
	public ArrayList<String> getComments(String key){
		SharedPreferences prefs = getSharedPreferences(Activity_TrafficJam.PREF_NAME, MODE_APPEND);
		ArrayList<String> lcommentList=new ArrayList<String>();

		String  commentes=prefs.getString(key,"[]");
		JSONArray arrayJson=null;
	
		
		try {
			arrayJson = new JSONArray(commentes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < arrayJson.length(); i++) {
			try {
				String item=arrayJson.getString(i);
				lcommentList.add(item);
				commentList.add(item);
				commentTypeList.add(new Comment(item, key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return lcommentList;
	}

	public void getAllComments(String key){
	
			getComments("Moderate");
			getComments("Heavy");
			getComments("Standstill");
			getComments("Minor_Accident");
			getComments("Major_Accident");
			getComments("Construction");
			getComments("Onroad");
			getComments("other");
		
	}
	
	
	
	Comment getComment(String comment){
		
		for (int i = 0; i < commentTypeList.size(); i++) {
			if(commentTypeList.get(i).comment.contains(comment))
				return commentTypeList.get(i);
		}
		
		
		return null;
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_report, menu);
		return true;
	}
	
	 //to send the user to the home page when pressing the back button or back to the traffic report if the user is registered
		@Override
	    public void onBackPressed() {
	    	// TODO Auto-generated method stub
//	    	super.onBackPressed();
			final String PREF_NAME = "userInformation";
			SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
			Intent i;
			if(prefs.getBoolean("isLoggedIn", false)){
				i = new Intent(this, Activity_TrafficMain.class);
			} else {
				i = new Intent(this, Home.class);
			}
			startActivity(i);
			finish();
	    }

}
