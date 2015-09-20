package com.pifss.myway;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class UISettings extends Activity {
	public static final String PREFS_NAME = "userSettings";
	String[] languages;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences sp = getSharedPreferences(PREFS_NAME,0);
        languages = getResources().getStringArray(R.array.available_languages);
		final Editor spEditor = sp.edit();
        //************Defining the controls****************//
        final Switch enableReports = (Switch) findViewById(R.id.swtEnableReports);
        final Switch enableNearbyPOI = (Switch) findViewById(R.id.swtEnableNearbyPOI);
        final Switch enableOverspeedAlert = (Switch) findViewById(R.id.swtEnableOverspeedAlert);
        final Switch enableVoiceAlert = (Switch) findViewById(R.id.swtEnableVoiceAlert);
        final TextView selectedLanguage = (TextView) findViewById(R.id.txtSelectedLanguage);
        final Button changeSelectedLanguage = (Button) findViewById(R.id.btnChange);
        final Settings settings = new Settings();
        //*********************Read Current Settings****************************//
        changeSelectedLanguage.setVisibility(View.INVISIBLE);
        selectedLanguage.setVisibility(View.INVISIBLE);
        settings.setEnableReports(sp.getBoolean("enableReports", false));
        if (settings.isEnableReports() == true){
        	enableReports.setChecked(true);
        } else {
        	enableReports.setChecked(false);
        }
        settings.setEnableNearbyPOI(sp.getBoolean("enableNearbyPOI", false));
        if (settings.isEnableNearbyPOI() == true){
        	enableNearbyPOI.setChecked(true);
        } else {
        	enableNearbyPOI.setChecked(false);
        }
        
        settings.setEnableOverspeedAlert(sp.getBoolean("enableOverspeedAlert", false));
        if ( settings.isEnableOverspeedAlert() == true){
        	enableOverspeedAlert.setChecked(true);
        } else {
        	enableOverspeedAlert.setChecked(false);
        }
        settings.setEnableVoiceAlert(sp.getBoolean("enableVoiceAlert", false));
        if ( settings.isEnableVoiceAlert() == true){
        	enableVoiceAlert.setChecked(true);
        	changeSelectedLanguage.setVisibility(View.VISIBLE);
        	selectedLanguage.setText(sp.getString("selectedLanguage", ""));
        	selectedLanguage.setVisibility(View.VISIBLE);
        } else {
        	enableVoiceAlert.setChecked(false);
        	changeSelectedLanguage.setVisibility(View.INVISIBLE);
        	selectedLanguage.setVisibility(View.INVISIBLE);
        }
        //***********Listeners to change SharedPrefrences**************//
        enableReports.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (enableReports.isChecked()){
					settings.setEnableReports(true);
					spEditor.putBoolean("enableReports", true);
				} else {
					settings.setEnableReports(false);
					spEditor.putBoolean("enableReports", false);
				}
				spEditor.commit();
			}
		});
        enableNearbyPOI.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (enableNearbyPOI.isChecked()){
					settings.setEnableNearbyPOI(true);
					spEditor.putBoolean("enableNearbyPOI", true);
				} else {
					settings.setEnableNearbyPOI(false);
					spEditor.putBoolean("enableNearbyPOI", false);
				}
				spEditor.commit();
			}
		});
        enableOverspeedAlert.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (enableOverspeedAlert.isChecked()){
					settings.setEnableOverspeedAlert(true);
					spEditor.putBoolean("enableOverspeedAlert", true);
				} else {
					settings.setEnableOverspeedAlert(false);
					spEditor.putBoolean("enableOverspeedAlert", false);
				}
				spEditor.commit();
				Toast.makeText(UISettings.this, settings.isEnableOverspeedAlert()+"", Toast.LENGTH_SHORT).show();
			}
		});
        enableVoiceAlert.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (enableVoiceAlert.isChecked()){
					settings.setEnableVoiceAlert(true);
					spEditor.putBoolean("enableVoiceAlert", true);
					changeSelectedLanguage.setVisibility(View.VISIBLE);
					selectedLanguage.setVisibility(View.VISIBLE);
				} else {
					settings.setEnableVoiceAlert(false);
					spEditor.putBoolean("enableVoiceAlert", false);
					changeSelectedLanguage.setVisibility(View.INVISIBLE);
					selectedLanguage.setVisibility(View.INVISIBLE);
				}
				spEditor.commit();
				Toast.makeText(UISettings.this, settings.isEnableVoiceAlert()+"", Toast.LENGTH_SHORT).show();
			}
		});
        changeSelectedLanguage.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Builder b = new Builder(UISettings.this);
				b.setItems(languages, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int menuItem) {
						// TODO Auto-generated method stub
						
						selectedLanguage.setText(languages[menuItem]);
						spEditor.putString("selectedLanguage", (String) selectedLanguage.getText());
						selectedLanguage.setVisibility(View.VISIBLE);
						spEditor.commit();
					}
				});
			   Dialog listalert = b.create();
			   listalert.show();
			}
		});
      //use the following line to add the sliding menu to the current page
      		SlidingUtil.SetSliding(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater mi = getMenuInflater();
		//mi.inflate(R.menu.activity_action_bar, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	Intent i = new Intent(UISettings.this, Home.class);
		startActivity(i);
		finish();
    }
}
