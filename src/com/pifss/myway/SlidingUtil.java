package com.pifss.myway;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SlidingUtil {
	
	private static ArrayList<ItemMenu> list=new ArrayList<ItemMenu>();
//	private static ArrayList<ItemMenu> list2=new ArrayList<ItemMenu>();
	public final static String PREF_NAME = "userInformation"; 
	private static ListView lv;
	private static MenuAdapter adapter;
	
	static void SetSliding(final Activity activity){
		
		SlidingMenu menu=new SlidingMenu(activity);
		 menu.setMode(SlidingMenu.LEFT);
		 menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		 menu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
		 menu.setBehindOffset(200);
		 menu.setMenu(R.layout.activity_sliding);
		
		
		lv=(ListView) activity.findViewById(R.id.listView1);
		adapter=new MenuAdapter(list, activity);
		
		lv.setAdapter(adapter);
		final SharedPreferences prefs = activity.getSharedPreferences(PREF_NAME, activity.MODE_APPEND);
		
		changeMenu(prefs.getBoolean("isLoggedIn", false),activity);
//		 Button button = (Button) findViewById(R.id.textView1);
//
//		 button.setOnClickListener(new OnClickListener() {
//	            public void onClick(View v) {
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int loc,
					long arg3) {
				
				if (loc == 0){
					Intent i = new Intent(activity, Home.class);
					activity.startActivity(i);
					activity.finish();
				}
				if (loc == 1){
					Intent i;
					if(prefs.getBoolean("isLoggedIn", false)){
						i = new Intent(activity, Activity_TrafficMain.class);
					} else {
						i = new Intent(activity, Activity_ViewReport.class);
					}
					activity.startActivity(i);
					activity.finish();
					
				}
				
				if (loc == 2){
					Intent i = new Intent(activity, EventsCategoriesViewActivity.class);
					activity.startActivity(i);
					activity.finish();				
				}
				if (loc == 3){
					Intent i = new Intent(activity, MainActivity.class);
					activity.startActivity(i);
					activity.finish();	
				}
				if (loc == 4){
					Intent i = new Intent(activity, UISettings.class);
					activity.startActivity(i);
					activity.finish();
				}
				if ( loc==5){
					Intent i;
					if(prefs.getBoolean("isLoggedIn", false)){
						i = new Intent(activity, ProfileHomeActivity.class);
					} else {
						i = new Intent(activity, LoginRegisterActivity.class);
					}
					activity.startActivity(i);
					activity.finish();

				}
				if (loc == 6){
					Intent i = new Intent(activity, DriverDestenationList.class);
					activity.startActivity(i);
					activity.finish();
				}
				if (loc == 7){
					Intent i = new Intent(activity, FavoritesViewActivity.class);
					activity.startActivity(i);
					activity.finish();
				}
  
			}
	      
			});
		
		
		
	}
	
	static void changeMenu(boolean status, Activity a){
		if (status){//is logged in
			list.clear();
			list.add(new ItemMenu("Home", BitmapFactory.decodeResource(a.getResources(), R.drawable.home)));
			list.add(new ItemMenu("Traffic", BitmapFactory.decodeResource(a.getResources(), R.drawable.s_traffic)));
			list.add(new ItemMenu("Events", BitmapFactory.decodeResource(a.getResources(), R.drawable.s_poi)));
			list.add(new ItemMenu("Nearby", BitmapFactory.decodeResource(a.getResources(), R.drawable.nearby2)));
			list.add(new ItemMenu("Setting", BitmapFactory.decodeResource(a.getResources(), R.drawable.settings)));
			list.add(new ItemMenu("Profile", BitmapFactory.decodeResource(a.getResources(), R.drawable.usr)));
			list.add(new ItemMenu("Parent Monitoring", BitmapFactory.decodeResource(a.getResources(), R.drawable.s_parent_icon)));
			list.add(new ItemMenu("Favorites", BitmapFactory.decodeResource(a.getResources(), R.drawable.s_ic_profile)));
		} else { //is logged out
			list.clear();
			list.add(new ItemMenu("Home", BitmapFactory.decodeResource(a.getResources(), R.drawable.home)));
			list.add(new ItemMenu("Traffic", BitmapFactory.decodeResource(a.getResources(), R.drawable.s_traffic)));
			list.add(new ItemMenu("Events", BitmapFactory.decodeResource(a.getResources(), R.drawable.s_poi)));
			list.add(new ItemMenu("Nearby", BitmapFactory.decodeResource(a.getResources(), R.drawable.nearby2)));
			list.add(new ItemMenu("Setting", BitmapFactory.decodeResource(a.getResources(), R.drawable.settings)));
			list.add(new ItemMenu("Log in", BitmapFactory.decodeResource(a.getResources(), R.drawable.login)));
			
		}
		lv.setAdapter(adapter);
	}

}
class CreateMenu {
	
}
