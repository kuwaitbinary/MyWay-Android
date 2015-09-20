package com.pifss.myway;

import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class InformationManager {
	
	Context context;
	SharedPreferences prefs;
	public final static String PREF_NAME = "userInformation";
	
	public InformationManager(Context context) {
		super();
		this.context = context;
		prefs = context.getSharedPreferences(PREF_NAME, context.MODE_APPEND);
	}

	
	// method for base64 to bitmap 
	//FROM BYTE ARRAY TO BITMAP
	public static Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
	
	//FROM BITMAP TO BYTE ARRAY
	public static String encodeTobase64(Bitmap image) {
		Bitmap immage = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		Log.d("Image Log:", imageEncoded);
		return imageEncoded;
	}
	
	public void writeImage(Bitmap imgMap){
		try {
			JSONObject userJson = getUserInformation();
			String username = userJson.getString("username");
			Editor editor = prefs.edit();
			editor.putString("userImagePref" + username,
					encodeTobase64(imgMap));
			editor.commit();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Bitmap readImage(){
		JSONObject jo = getUserInformation();
		String username = "";
		try {
			username = jo.getString("username");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userProfileImage = prefs.getString("userImagePref" + username, "");
		Bitmap tempMap = null;
		if (!userProfileImage.equals("")) {
			tempMap = decodeBase64(userProfileImage);
		}
		return tempMap;
	}
	
	//method to return the user information as JSON object, if not found it would return null.
	public JSONObject getUserInformation (){
		
		String userObj = prefs.getString("user", "ERROR");
		JSONObject userJson;
		try {
			userJson = new JSONObject(userObj);
			return userJson;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void logIn(Activity a){
		Editor editor = prefs.edit();
		editor.putBoolean("isLoggedIn",true);
		editor.commit();
		SlidingUtil.changeMenu(true, a);
	}
	
	public void logOut(Activity a){
		Editor editor = prefs.edit();
		editor.putBoolean("isLoggedIn",false);
		editor.commit();
		SlidingUtil.changeMenu(false, a);
	}

}
