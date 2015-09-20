package com.pifss.myway;

import java.text.BreakIterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Fragment_Register extends Fragment {

	public final static String PREF_NAME = "userInformation";
	EditText userName;
	EditText passWord;
	EditText email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.fragment_register, null);

		userName = (EditText) v.findViewById(R.id.editTextUsernameReg);
		passWord = (EditText) v.findViewById(R.id.editTextPasswordReg);
		email = (EditText) v.findViewById(R.id.EditTextEmailReg);

		ImageView Register = (ImageView) v
				.findViewById(R.id.imageButtonRegister);

		Register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				JSONObject json = new JSONObject();

				if (validate()) {
					try {
						json.put("username", userName.getText().toString());
						json.put("password", passWord.getText().toString());
						json.put("email", email.getText().toString());

						SharedPreferences prefs = v.getContext()
								.getSharedPreferences(PREF_NAME,
										v.getContext().MODE_APPEND);

						Editor editor = prefs.edit();

						editor.putString("user", json.toString());

						editor.commit();
						InformationManager im = new InformationManager(v.getContext());
						im.logIn(getActivity());
						Intent i = new Intent(v.getContext(),
								ProfileHomeActivity.class);
						startActivity(i);
						getActivity().finish();// to prevent going back to the
												// previous page
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		return v;
	}

	boolean flag1;
	boolean flag2;
	boolean flag3;

	public boolean validate() {

		if (userName.getText().length() < 5) {
			userName.setError("Username too short");
			flag1 = false;
		} else {
			flag1 = true;
		}

		if (passWord.getText().length() < 8) {
			passWord.setError("Password must be more than 8 Characters");
			flag2 = false;
		} else {
			flag2 = true;
		}

		if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
				email.getText().toString()).matches()) {
			email.setError("Invalid email address");
			flag3 = false;
		} else {
			flag3 = true;
		}

		if (flag1 == true && flag2 == true && flag3 == true) {
			return true;
		} else {
			return false;
		}
	} // end of method

}
