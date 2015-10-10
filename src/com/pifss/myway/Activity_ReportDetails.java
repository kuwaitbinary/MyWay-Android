package com.pifss.myway;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_ReportDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_details);
		 MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.rmap);
		 final GoogleMap map=mapFragment.getMap();
		System.out.println("back to oncreate");
		 if(Activity_TrafficMain.reports==null )
		 {
			 System.out.println("YES it is null");
//				lr.getReports();
				System.out.println("IM IN Activity_ReportDetails ");
				System.out.println(Activity_TrafficMain.reports);
				
		 }
		 else{
		 System.out.println("NOT NULL");
			System.out.println("IM IN Activity_ReportDetails ");
			System.out.println(Activity_TrafficMain.reports);
			   // ITERATE THROUGH AND RETRIEVE CLUB FIELDS
            int n = Activity_TrafficMain.reports.length();
            System.out.println("Length is " + n);
            for (int i = 0; i < n; i++) {
//                // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                JSONObject jo;
				try {
					jo = Activity_TrafficMain.reports.getJSONObject(i);
					   // RETRIEVE EACH JSON OBJECT'S FIELDS
	                String id = jo.getString("report_type");
	                String comment = jo.getString("report_comments");
	                String log = jo.getString("report_log");
	                String  lat= jo.getString("report_lat");
	                Double latd = Double.parseDouble(lat);
	                Double logd = Double.parseDouble(log);

	                
	                System.out.println("lat is"+latd+"lod is "+logd);
	                map.addMarker(new MarkerOptions()
	                .position(new LatLng(latd,logd))
	                .title(comment));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		 }
		 
			   
	         // ITERATE THROUGH AND RETRIEVE CLUB FIELDS
//	            int n = Activity_TrafficMain.reports.length();
//	            System.out.println("Length is " + n);
//	            for (int i = 0; i < n; i++) {
//	                // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
//	                JSONObject jo;
//					try {
//						jo = Activity_TrafficMain.reports.getJSONObject(i);
//						   // RETRIEVE EACH JSON OBJECT'S FIELDS
//		                String id = jo.getString("report_type");
//		                String comment = jo.getString("report_comments");
//		                String log = jo.getString("report_log");
//		                String  lat= jo.getString("report_lat");
//		                Double latd = Double.parseDouble(lat);
//		                Double logd = Double.parseDouble(log);
//
//		                
//		                System.out.println("lat is"+latd+"lod is "+logd);
//		                map.addMarker(new MarkerOptions()
//		                .position(new LatLng(latd,logd))
//		                .title(comment));
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
	                 
	
	            
	             
//	}
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_report_details, menu);
		
		return true;
	}
	
}
	
 

		
	


