package com.pifss.myway;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class SetLocationActivity extends Activity {

	LatLng mirqap;
	LatLng selected = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_location);
		
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.setPointMap);

		final GoogleMap map = mapFragment.getMap();

		mirqap = new LatLng(29.363745, 47.983726);

		CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(mirqap, 13);
		map.moveCamera(cam);

		map.addMarker(new MarkerOptions().title("Mirqap").position(mirqap));

		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latlng) {
				// TODO Auto-generated method stub
				map.clear();
				map.addMarker(new MarkerOptions().position(latlng));
				RequestEventActivity.point = latlng;
				Intent ret = new Intent();
				ret.putExtra("result", "");
				setResult(RESULT_OK, ret);
				finish();
			}
		});
		
		
//		MapFragment mapFragment = (MapFragment) getFragmentManager()
//				.findFragmentById(R.id.setPointMap);
//
//		final GoogleMap map = mapFragment.getMap();
//
//		CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(pointPin, 13);
//		map.moveCamera(cam);
//
//
//		map.setOnMapLongClickListener(new OnMapLongClickListener() {
//
//			@Override
//			public void onMapLongClick(LatLng latlng) {
//				// TODO Auto-generated method stub
//				map.clear();
//				map.addMarker(new MarkerOptions().position(latlng));
//				pointPin = latlng;
//				RequestEventActivity.point = pointPin;
//				Intent ret = new Intent();
//				ret.putExtra("result", "");
//				setResult(RESULT_OK, ret);
//				finish();
//			}
//		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_location, menu);
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
