package com.pifss.myway;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class POIActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);
        
//        ImageButton imB = (ImageButton)findViewById(R.id.imageButton1);
        
        ImageButton imB1 = (ImageButton)findViewById(R.id.imageButton2);
        
        
        
        imB1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(POIActivity.this,POI1Activity.class);
			    
				startActivity(i);
				finish();
			
			}
		});
    }
        

        
        
        
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_poi, menu);
        return true;
    }
    
}
