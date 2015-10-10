package com.pifss.myway;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsViewActivity extends Activity {

	static ArrayList<Event> catEventsList = new ArrayList<Event>();
	ArrayList<String> eventNamesList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_view);
		
		eventNamesList.clear();
		catEventsList.clear();
		Intent intent = getIntent();
		String c = intent.getStringExtra("cat");

		for (int i = 0; i < EventsCategoriesViewActivity.eventsList.size(); i++) {
			Event e = EventsCategoriesViewActivity.eventsList.get(i);
			if (e.getCategory().equals(c)) {
				catEventsList.add(e);
			}
		}
		if (catEventsList.size() == 0){
			eventNamesList.add("No Events Found.");
		} else {
			for (int i = 0; i < catEventsList.size(); i++) {
				Event e = catEventsList.get(i);
				eventNamesList.add(e.getName());
			}
		}
		
		final ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventNamesList);
		
		ListView lvEv = (ListView) findViewById(R.id.listView2);
		
		lvEv.setAdapter(a);
		
		lvEv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (catEventsList.size() == 0){
					//nothing happens 
				} else {
					Intent in = new Intent(EventsViewActivity.this, EventInfoViewActivity.class);
					
					in.putExtra("pos", position+"");
					
					startActivity(in);
				}
				
			}
		});
		
//		 use the following line to add the sliding menu to the current page
		SlidingUtil.SetSliding(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events_view, menu);
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


}
