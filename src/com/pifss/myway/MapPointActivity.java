package com.pifss.myway;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MapPointActivity extends Activity {

	LatLng pointPin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_point);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.mapViewPoint);

		Intent i = getIntent();
		String pointName = i.getStringExtra("name");
		Double lat = Double.parseDouble(i.getStringExtra("lat"));
		Double lng = Double.parseDouble(i.getStringExtra("long"));
		
		final GoogleMap map = mapFragment.getMap();

		pointPin = new LatLng(lat, lng);

		CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(pointPin, 13);
		map.moveCamera(cam);

		map.addMarker(new MarkerOptions().title(pointName).position(pointPin));

		TextView tvLabel = (TextView) findViewById(R.id.textViewPoint);
		tvLabel.setText(pointName);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_point, menu);
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
