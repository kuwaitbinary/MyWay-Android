package com.pifss.myway;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class SplashScreen extends Activity {
	public final static String PREF_NAME = "userInformation";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_APPEND);
				boolean loggedin = prefs.getBoolean("isLoggedIn", false);
				Class nextClass=LoginRegisterActivity.class;
				
				if(loggedin ){
					nextClass=Home.class;
				} 
				
				Intent i=new Intent(SplashScreen.this,nextClass);
				startActivity(i);
				finish();
			}
		});
		t.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

}
