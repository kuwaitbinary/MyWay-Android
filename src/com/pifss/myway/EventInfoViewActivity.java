package com.pifss.myway;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EventInfoViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_info_view);
		
		Intent i = getIntent();
		String temp = i.getStringExtra("pos");
		int pos = Integer.parseInt(temp);
		final Event e = EventsViewActivity.catEventsList.get(pos);
//		
		TextView tvEventName = (TextView) findViewById(R.id.textView1);
		TextView tvEventDec = (TextView) findViewById(R.id.textView2);
		TextView tvStartDate = (TextView) findViewById(R.id.textView4);
		TextView tvEndDate = (TextView) findViewById(R.id.textView5);
		ImageView ivEventImage = (ImageView) findViewById(R.id.imageView1);
		Button bSendToMap = (Button) findViewById(R.id.button1);
//		
		tvEventName.setText(e.getName());
		tvEventDec.setText(e.getDescription());
		tvStartDate.setText(e.getStartDate());
		tvEndDate.setText(e.getEndDate());
		
		
		Bitmap bm = InformationManager.decodeBase64(e.getImage());
		ivEventImage.setImageBitmap(bm);
		
		bSendToMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(EventInfoViewActivity.this, MapPointActivity.class);
				in.putExtra("name", e.getName());
				in.putExtra("lat", e.getLatitude());
				in.putExtra("long", e.getLongitude());
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_info_view, menu);
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
