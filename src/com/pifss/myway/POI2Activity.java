package com.pifss.myway;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class POI2Activity extends Activity {
	
	public final static String  PREF_NAME = "poilist";
	
	String filter="";
	LatLng mirqap;
	
	LatLng selected;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi2);
		
	    MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		
		
		final GoogleMap map=mapFragment.getMap();
		
		
		 mirqap=new LatLng(29.31407,47.4917);
		
		CameraUpdate cam=CameraUpdateFactory.newLatLngZoom(mirqap, 13);
		map.moveCamera(cam);

        map.addMarker(new MarkerOptions()
                .title("Mirqap")
                .snippet("The most populous city in Kuwait.")
                .position(mirqap));
		
        
        map.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng latlng) {
				// TODO Auto-generated method stub
				map.clear();
				Toast.makeText(POI2Activity.this, "You clicked Location:"+latlng.latitude+", "+latlng.longitude, Toast.LENGTH_LONG).show();
				
				map.addMarker(new MarkerOptions()
                
                .position(latlng));
				selected=latlng;
			}
		});
	
		
		ImageButton Save = (ImageButton)findViewById(R.id.imageButton1);
		ImageButton Reset = (ImageButton)findViewById(R.id.imageButton2);
        
        final RadioButton rbMAll=(RadioButton) findViewById(R.id.radioButton1);
        final RadioButton rbCafe=(RadioButton) findViewById(R.id.radioButton2);
        final RadioButton rbRest=(RadioButton) findViewById(R.id.radioButton3);
        
        OnClickListener rbListener=new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				boolean checked =((RadioButton) v).isChecked();
				
				switch(v.getId()){
				case R.id.radioButton1:
					if(checked)
						break;
					
				case R.id.radioButton2:
					if(checked)
						break;
					
				case R.id.radioButton3:
					if(checked)
						break;
				}
				
				
				if (rbMAll.isChecked()) {
					filter="Mall";
					
					
				}else if(rbCafe.isChecked()){
					filter="Cafe";
					
				}else if(rbRest.isChecked()){
					filter="restarant";
				}
			}
		};
        
        
        rbMAll.setOnClickListener(rbListener);
        rbCafe.setOnClickListener(rbListener);
        rbRest.setOnClickListener(rbListener);
        
    final    EditText NameET=(EditText) findViewById(R.id.editText1);
    final    EditText DescriptionET=(EditText) findViewById(R.id.editText2);
    
    Reset.setOnClickListener(new OnClickListener() { 
		
		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			NameET.setText("");
			DescriptionET.setText("");
			rbMAll.setChecked(false);
			rbRest.setChecked(false);
			rbCafe.setChecked(false);
		}
	});
    
    
    
    Save.setOnClickListener(new OnClickListener() {
		
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(POI2Activity.this,POI1Activity.class);
				SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
				Editor editor=prefs.edit();
				String Name= NameET.getText().toString();
				String Description= DescriptionET.getText().toString();
				
				String location = selected+"";
				
				
				
				String NameString=prefs.getString("Name","[]");
			    String DescriptionString=prefs.getString("Description","[]");
			    String filterString=prefs.getString("filter","[]");
			    String locationString =prefs.getString("location", "[]");
			   
				
			    JSONArray arrayName=null;
				
				try {
					arrayName = new JSONArray(NameString);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				arrayName.put(Name);
				String arrayNameAsString=arrayName.toString();
				editor.putString("Name", arrayNameAsString);
				
								        
				JSONArray arrayDes=null;
				
				try {
					arrayDes = new JSONArray( DescriptionString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				arrayDes.put(Description);

			 String arrayDescAsString=arrayDes.toString();
		     editor.putString("Description", arrayDescAsString);
		     
		     JSONArray arrayFilter=null;
				
				try {
					arrayFilter = new JSONArray( filterString);
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				arrayFilter.put(filter);

			 String arrayFilterAsString=arrayFilter.toString();
		     editor.putString("Filter", arrayFilterAsString);
		     
		     JSONArray arraylocation=null;
				
				try {
					arraylocation = new JSONArray( locationString);
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				arraylocation.put(location);

			 String arraylocationrAsString=arraylocation.toString();
		     editor.putString("latitude", arraylocationrAsString);
		     
		     
		     
		     
			 editor.commit();
			 NameET.setText("");
			 DescriptionET.setText("");
			 
				startActivity(i);
				finish();
					
			}
		});
     
 }

			
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_poi2, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
	   Log.d("CDA", "onBackPressed Called");
	   Intent intent = new Intent(POI2Activity.this, POI1Activity.class);
	   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	   startActivity(intent);
	}

}
