package com.pifss.myway;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileEditActivity extends Activity {
	ImageView imEdit;
	public final static String PREF_NAME = "userInformation";
	JSONObject userJson;
	InformationManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_edit);

		imm = new InformationManager(this);

		// set the old image in the imageView
		imEdit = (ImageView) findViewById(R.id.imageViewEditImage);
		Bitmap bm = imm.readImage();
		if (bm != null){
		imEdit.setImageBitmap(imm.readImage());
		}
		imEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, 1);
			}
		});

		final EditText etUsername = (EditText) findViewById(R.id.editTextUsernameEdit);
		final EditText etEmail = (EditText) findViewById(R.id.editTextEmailEdit);

		try {
			userJson = imm.getUserInformation();
			etUsername.setText(userJson.getString("username"));
			etEmail.setText(userJson.getString("email"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ImageView imSave = (ImageView) findViewById(R.id.imageViewSaveChanges);

		imSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText etOldPass = (EditText) findViewById(R.id.editTextOldPassConfirm);
				EditText etNewPass = (EditText) findViewById(R.id.editTextPasswordEdit);
				EditText etUsername = (EditText) findViewById(R.id.editTextUsernameEdit);
				EditText etPassword = (EditText) findViewById(R.id.editTextPasswordEdit);
				JSONObject json = new JSONObject();

				try {

					// checking username
					String UN = userJson.getString("username");
					if (!etUsername.getText().toString().equals(UN)) {
						if (etUsername.getText().length() < 5) {
							etUsername.setError("Username too short");
						} else {
							json.put("username", etUsername.getText()
									.toString());
						}
					} else {
						json.put("username", UN);
					}

					// checking email
					String EM = userJson.getString("email");
					if (!etPassword.getText().toString().equals(EM)) {
						if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
								etEmail.getText().toString()).matches()) {
							etEmail.setError("Invalid email address");
						} else {
							json.put("email", etEmail.getText().toString());
						}
					} else {
						json.put("email", EM);
					}

					// checking passwords
					String oldPass = userJson.getString("password");
					boolean flag = false;
					if (!etNewPass.getText().toString().equals("")) {
						if (etNewPass.getText().length() > 8
								&& etOldPass.getText().toString()
										.equals(oldPass)) {
							json.put("password", etNewPass.getText().toString());
							flag = true;
						} else {
							Toast.makeText(ProfileEditActivity.this,
									"Passwords are not matching!",
									Toast.LENGTH_SHORT).show();
							etOldPass.setText("");
							etNewPass.setText("");
							// json.put("password", oldPass);
							flag = false;
						}
					} else {
						json.put("password", oldPass);
						flag = true;
					}

					if (flag) {
						SharedPreferences prefs = v.getContext()
								.getSharedPreferences(PREF_NAME,
										v.getContext().MODE_APPEND);

						Editor editor = prefs.edit();

						editor.putString("user", json.toString());

						editor.commit();

						Intent i = new Intent(v.getContext(),
								ProfileHomeActivity.class);
						startActivity(i);
						finish();// to prevent going back to the previous page.
									// on the activity i can do it without
									// getActivity()!

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

//	// method for base64 to bitmap
//	public static Bitmap decodeBase64(String input) {
//		byte[] decodedByte = Base64.decode(input, 0);
//		return BitmapFactory
//				.decodeByteArray(decodedByte, 0, decodedByte.length);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_edit, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK) {
			// *************Content Provider Knowledge************//
			Uri d = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(d, filePathColumn, null,
					null, null);

			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

			if (columnIndex < 0) // no column index
				return; // DO YOUR ERROR HANDLING

			// ********************************************//

			String picturePath = cursor.getString(columnIndex);

			cursor.close(); // close cursor

			Bitmap map = BitmapFactory.decodeFile(picturePath);

			imEdit.setImageBitmap(map);

			imm.writeImage(map);
		}
	}
	
	//to send the user to the home page when pressing the back button
		@Override
	    public void onBackPressed() {
	    	// TODO Auto-generated method stub
//	    	super.onBackPressed();
	    	Intent i = new Intent(ProfileEditActivity.this, ProfileHomeActivity.class);
			startActivity(i);
			finish();
	    }

}
