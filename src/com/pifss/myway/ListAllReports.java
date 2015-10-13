package com.pifss.myway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.*;


import android.os.AsyncTask;
import android.widget.Toast;

public class ListAllReports {
	
	public void getReports(){
	
	new DownloadTaskReport().execute();

}}
class DownloadTaskReport extends AsyncTask<String, Integer, String>{
	@Override
	protected String doInBackground(String... params) {
		URI u = null;
		try {
			 u= new URI("http://172.16.8.105:8080/MyWayWeb/viewAllTrafficReports");
				DefaultHttpClient client=new DefaultHttpClient();
				HttpPost post=new HttpPost(u);
				String finalString="hello baby";
				HttpResponse response;
				
					response = client.execute(post);

					HttpEntity entity=response.getEntity();
					
					// CONVERT RESPONSE TO STRING
		            String result = EntityUtils.toString(entity);
		           //  System.out.println("result"+result);
		            // CONVERT RESPONSE STRING TO JSON ARRAY
		            JSONObject allData = new JSONObject(result);
		            JSONArray ja = allData.getJSONArray("result_data");
		            Home.reports = ja;
		            System.out.println("Im In LIST ALL REPORTS");
		            System.out.println( Home.reports);
		         //   System.out.println("ja"+ja);
		            
		         // ITERATE THROUGH AND RETRIEVE CLUB FIELDS
		            int n = ja.length();
		            for (int i = 0; i < n; i++) {
		                // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
		                JSONObject jo = ja.getJSONObject(i);
		                 
		                // RETRIEVE EACH JSON OBJECT'S FIELDS
		                String id = jo.getString("report_type");
		                String comment = jo.getString("report_comments");
		                String log = jo.getString("report_log");
		                String  lat= jo.getString("report_lat");
		            
		             
		            }
		           
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}
	

}

