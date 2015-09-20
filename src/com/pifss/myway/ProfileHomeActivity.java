package com.pifss.myway;

import java.io.Reader;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileHomeActivity extends Activity {

//	public final static String PREF_NAME = "userInformation";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_home);
		
		////////////////////// To set the user info on the profile ////////////////////////
		TextView tvUsername = (TextView) findViewById(R.id.textViewProfileUsername);
		ImageView userImg = (ImageView) findViewById(R.id.imageViewProfileImage);
		final InformationManager imm = new InformationManager(this);
		JSONObject userJson;
		
		
		
		
		userJson = imm.getUserInformation();
		try {
			String username = userJson.getString("username");
			tvUsername.setText(username);
			
			Bitmap bm = imm.readImage();
			if (bm != null){
				userImg.setImageBitmap(bm);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//////////////////////////////// Go to edit profile page /////////////////////////////////////////
		ImageView imIcon = (ImageView) findViewById(R.id.imageViewEditIcon); //edit profile icon

		imIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ProfileHomeActivity.this,
						ProfileEditActivity.class);
				startActivity(i);
				finish();
			}
		});
		//////////////// button to log out /////////////////////////
		
		ImageView imLogOut = (ImageView) findViewById(R.id.imageViewLogout);
		imLogOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imm.logOut(ProfileHomeActivity.this);
				Intent i = new Intent(ProfileHomeActivity.this, Home.class);
				startActivity(i);
				finish();
			}
		});
		
		//////////////// button to go to the add fav ////////////////////
//		TextView tvAddFav = (TextView) findViewById(R.id.textViewAddFav);
//		tvAddFav.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(ProfileHomeActivity.this, FavoritesActivity.class);
//				startActivity(i);
//				finish();
//			}
//		});
		
		
		//use the following line to add the sliding menu to the current page
		SlidingUtil.SetSliding(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_home, menu);
		return true;
	}
    //to send the user to the home page when pressing the back button
	@Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	Intent i = new Intent(ProfileHomeActivity.this, Home.class);
		startActivity(i);
		finish();
    }

}
