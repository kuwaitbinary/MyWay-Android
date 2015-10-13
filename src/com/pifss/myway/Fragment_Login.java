package com.pifss.myway;

import java.io.Console;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Login extends Fragment {
	public final static String PREF_NAME = "userInformation";
	
	private View v;
	private EditText etUsernameLogin;
	private EditText etPasswordLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		v = inflater.inflate(R.layout.fragment_login, null);
		etUsernameLogin = (EditText) v
				.findViewById(R.id.editTextUsernameLogin);
		etPasswordLogin = (EditText) v
				.findViewById(R.id.editTextPasswordLogin);
		final ImageView bLogin = (ImageView) v
				.findViewById(R.id.imageButtonLogin);

		// final String tempusername = etUsernameLogin.getText().toString();

		bLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				new Login().execute();

				/*SharedPreferences pref = v.getContext().getSharedPreferences(
						PREF_NAME, v.getContext().MODE_APPEND);
				String userObj = pref.getString("user", "ERROR");
				try {
					JSONObject userJson = new JSONObject(userObj);
					String tempUsername = userJson.getString("username");
					String tempPassword = userJson.getString("password");
					if (etUsernameLogin.getText().toString()
							.equals(tempUsername)
							&& etPasswordLogin.getText().toString()
									.equals(tempPassword)) {
						Intent i = new Intent(v.getContext(),
								ProfileHomeActivity.class);
						InformationManager im = new InformationManager(v.getContext());
						im.logIn(getActivity());
					
						startActivity(i);
						getActivity().finish();// to prevent going back to the
												// previous page
					} else {
						Toast.makeText(v.getContext(), "Incorrect credentials",
								Toast.LENGTH_SHORT).show();
						etPasswordLogin.setText("");
						etUsernameLogin.setText("");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

			}
		});

		// Creating an object of the dialog or something
		
		Builder builder = new Builder(v.getContext());
		
		builder.setTitle("Forget Password");
		
		final EditText usernameInput = new EditText(v.getContext());
		usernameInput.setInputType(InputType.TYPE_CLASS_TEXT);
		
		builder.setView(usernameInput);
		builder.setMessage("Reset Password via Email");
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setCancelable(false);
		
		builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String [] stringArray = {usernameInput.getText().toString()};
				new ForgetPassword().execute(stringArray);
				
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		
		final Dialog dialog = builder.create();
		

		/*
		Builder b1 = new Builder(v.getContext());

		LayoutInflater DialogInflater = (LayoutInflater) v.getContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View dialogView = inflater
				.inflate(R.layout.dialog_forgotpassword, null);

		b1.setView(dialogView);

		final Dialog dialogCustom = b1.create();
		*/
		
		TextView tvForgot = (TextView) v.findViewById(R.id.textViewForgot);
		tvForgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
				//dialogCustom.show();

			}
		});

		return v;
	}
	
	class Login extends AsyncTask<Void, Void, Boolean> {
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			Boolean validData = true;
			
			try {
				URI uri = new URI("");
				
				DefaultHttpClient client = new DefaultHttpClient();
				
				HttpPost postRequest = new HttpPost(uri);
				
				ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
				arrayList.add(new BasicNameValuePair("username", etUsernameLogin.getText().toString()));
				arrayList.add(new BasicNameValuePair("password", etPasswordLogin.getText().toString()));
				
				postRequest.setEntity(new UrlEncodedFormEntity(arrayList));
				
				HttpResponse response = client.execute(postRequest);
				
				HttpEntity entity = response.getEntity();
			    String jsonString = EntityUtils.toString(entity);
			    
			    JSONObject jsonData = new JSONObject(jsonString);
			    
			    if (jsonData.getString("result_code").equals("1"))
			    	validData = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return validData;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if (result == true) {
				
				Intent i = new Intent(v.getContext(), ProfileHomeActivity.class);
				InformationManager im = new InformationManager(v.getContext());
				im.logIn(getActivity());
			
				startActivity(i);
				getActivity().finish();
				
			} else {
				Toast.makeText(v.getContext(), "Incorrect credentials",
						Toast.LENGTH_SHORT).show();
				etPasswordLogin.setText("");
				etUsernameLogin.setText("");
			}
		}
		
	}
	
	class ForgetPassword extends AsyncTask<String, Void, Void> {
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			try {
				URI uri = new URI("");
				
				DefaultHttpClient client = new DefaultHttpClient();
				
				HttpPost postRequest = new HttpPost(uri);
				
				ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
				arrayList.add(new BasicNameValuePair("username", params[0]));
				
				postRequest.setEntity(new UrlEncodedFormEntity(arrayList));
				
				HttpResponse response = client.execute(postRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
	}

}