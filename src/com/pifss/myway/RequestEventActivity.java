package com.pifss.myway;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.R.string;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RequestEventActivity extends Activity {

	private TextView mDateDisplay;
	private Button mPickDate;

	private TextView mDateDisplay2;
	private Button mPickDate2;
	
	ImageView eventImage;
	InformationManager imm;

	EditText etName;
	EditText etDec;
	EditText etCat;
	

	private int mYear;
	private int mMonth;
	private int mDay;

	private int flag;
	
	static LatLng point = null;

	static final int DATE_DIALOG_ID = 0;
	String encodedImage = "";
	
	private String startDate = "";
	private String endDate = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_event);

		// capture our View elements
		mDateDisplay = (TextView) findViewById(R.id.textView1);
		mPickDate = (Button) findViewById(R.id.button2);
		mDateDisplay2 = (TextView) findViewById(R.id.textView2);
		mPickDate2 = (Button) findViewById(R.id.button3);
		etName = (EditText) findViewById(R.id.editText1);
		etDec = (EditText) findViewById(R.id.editText2);
		etCat = (EditText) findViewById(R.id.editText3);
		
		// add a click listener to the button
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
				flag = 1;
			}
		});

		mPickDate2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
				flag = 2;
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// display the current date (this method is below)
//		updateDisplay();

		eventImage = (ImageView) findViewById(R.id.imageView1);
		imm = new InformationManager(this);

		// set the old image in the imageView

//		Bitmap bm = null;
		eventImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, 1);
			}
		});
		
		Button bSetLocation = (Button) findViewById(R.id.button4);
		bSetLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(RequestEventActivity.this, SetLocationActivity.class);
				startActivityForResult(in, 2);
				
			}
		});

		Button bSend = (Button) findViewById(R.id.button1);
		bSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// validate the form
				if (validateRequest()) {
					new AddEventTask().execute();
				} else {
					Toast.makeText(RequestEventActivity.this,
							"Sending failed! Check your input.", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	public boolean validateRequest() {
		if (etName.getText().toString().equals("")
				|| etDec.getText().toString().equals("") || etCat.getText().toString().equals("")) {
			return false;
		} else if (point == null || startDate.equals("") || endDate.equals("")) {
			return false;
		} else {
			return true;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	// updates the date we display in the TextView
	private void updateDisplay() {
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
		startDate = ""+mYear+"-"+(mMonth + 1)+"-"+mDay;

	}

	private void updateDisplay2() {
		mDateDisplay2.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
		endDate = ""+mYear+"-"+(mMonth + 1)+"-"+mDay;
//		Toast.makeText(RequestEventActivity.this, endDate, Toast.LENGTH_SHORT).show();
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			if (flag == 1) {
				updateDisplay();
			}
			if (flag == 2) {
				updateDisplay2();
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RequestEventActivity.RESULT_OK) {
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
			// Log.d("test:", imm.encodeTobase64(map));
			eventImage.setImageBitmap(map);
			encodedImage = InformationManager.encodeTobase64(map);
			// imm.writeImage(map);
		}
		if (requestCode == 2){
			if (resultCode == RequestEventActivity.RESULT_OK){
				Toast.makeText(RequestEventActivity.this, "location set!", Toast.LENGTH_SHORT).show();
			}
			if (resultCode == RequestEventActivity.RESULT_CANCELED){
				Toast.makeText(RequestEventActivity.this, "set location failed.", Toast.LENGTH_SHORT).show();
			}
			
		}
	}

	public final static String PREF_NAME = "userInformation";
	class AddEventTask extends AsyncTask<String, Integer, String> {

		ProgressDialog dialog = new ProgressDialog(RequestEventActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setTitle("Sending Request");
			dialog.setMessage("Sending....");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				URI u;
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost post = null;
				ArrayList<BasicNameValuePair> urlparameters = new ArrayList<BasicNameValuePair>();

				u = new URI("http://172.16.8.105:8080/MyWayWeb/requestEvent");
				post = new HttpPost(u);

				SharedPreferences pref = getSharedPreferences(PREF_NAME,
						MODE_APPEND);
				String userObj = pref.getString("user", "ERROR");
				String tempUsername = "";
				JSONObject userJson;
				try {
					userJson = new JSONObject(userObj);
					tempUsername = userJson.getString("username");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				urlparameters.add(new BasicNameValuePair("name", etName
						.getText().toString()));
				String lat = point.latitude + "";
				String lng = point.longitude + "";
				urlparameters.add(new BasicNameValuePair("latitude", lat));
				urlparameters.add(new BasicNameValuePair("longitude", lng));
				urlparameters.add(new BasicNameValuePair("username",
						tempUsername));
				urlparameters.add(new BasicNameValuePair("startDate", startDate));
				urlparameters.add(new BasicNameValuePair("endDate", endDate));
				urlparameters.add(new BasicNameValuePair("category", etCat.getText().toString()));
				urlparameters.add(new BasicNameValuePair("description", etDec.getText().toString()));
				urlparameters.add(new BasicNameValuePair("image", encodedImage));
				
				post.setEntity(new UrlEncodedFormEntity(urlparameters));

				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);

				// Log.d("test:", data);
				return data;

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			point = null;
			Log.d("test: ", result);
			dialog.dismiss();
			Intent i = new Intent(RequestEventActivity.this,
					EventsCategoriesViewActivity.class);
			startActivity(i);
			finish();
		}

	}

}
