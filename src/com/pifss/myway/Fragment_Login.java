package com.pifss.myway;

import java.io.Console;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		final View v = inflater.inflate(R.layout.fragment_login, null);
		final EditText etUsernameLogin = (EditText) v
				.findViewById(R.id.editTextUsernameLogin);
		final EditText etPasswordLogin = (EditText) v
				.findViewById(R.id.editTextPasswordLogin);
		final ImageView bLogin = (ImageView) v
				.findViewById(R.id.imageButtonLogin);

		// final String tempusername = etUsernameLogin.getText().toString();

		bLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SharedPreferences pref = v.getContext().getSharedPreferences(
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
				}

			}
		});

		// Creating an object of the dialog or something

		Builder b1 = new Builder(v.getContext());

		LayoutInflater DialogInflater = (LayoutInflater) v.getContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View dialogView = inflater
				.inflate(R.layout.dialog_forgotpassword, null);

		b1.setView(dialogView);

		final Dialog dialogCustom = b1.create();

		TextView tvForgot = (TextView) v.findViewById(R.id.textViewForgot);
		tvForgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogCustom.show();

			}
		});

		return v;
	}

}
