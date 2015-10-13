package com.pifss.myway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;


public class ParentMonitoring_Pairing extends Activity {

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parental_monitoring);
        
        //code to search the database and populate the list
        
        
        
        //change the array to the database information
        final String[] vals = {"Omar" , "Ahmed" , "Ali" , "Bader"};
        ListView lv = (ListView) findViewById(R.id.driverList);
        
        Adabtor_ParentMonitoring_SearchDriver adabtor = new Adabtor_ParentMonitoring_SearchDriver(vals, this);
        
        lv.setAdapter(adabtor);
        lv.setClickable(true);
        
        // making the list clickable 
        
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				 Intent i = new Intent(getApplicationContext(), ParentMonitoring_Pairing_DriverPairing.class);
				 i.putExtra("username", vals[arg2]);
				 startActivity(i);
				
			}
		});
       
    }
    
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parent_monitoring__pairing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        return super.onOptionsItemSelected(item);
    }
}
