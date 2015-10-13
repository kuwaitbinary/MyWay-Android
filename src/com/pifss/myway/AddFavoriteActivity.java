package com.pifss.myway;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pifss.myway.FavoritesViewActivity.RetrieveFavsTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddFavoriteActivity extends Activity {

	LatLng mirqap;
	LatLng selected = null;
	EditText etFavName;
	public final static String PREF_NAME = "userInformation";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_favorite);

		etFavName = (EditText) findViewById(R.id.editTextAddFavName);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.mapAddFav);

		final GoogleMap map = mapFragment.getMap();

		mirqap = new LatLng(29.363745, 47.983726);

		CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(mirqap, 13);
		map.moveCamera(cam);

		map.addMarker(new MarkerOptions().title("Mirqap")
				.snippet("The most populous city in Kuwait.").position(mirqap));

		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latlng) {
				// TODO Auto-generated method stub
				map.clear();
				// Toast.makeText(AddFavoriteActivity.this,
				// "You clicked Location:"+latlng.latitude+", "+latlng.longitude,
				// Toast.LENGTH_LONG).show();

				map.addMarker(new MarkerOptions().position(latlng));
				// Toast.makeText(AddFavoriteActivity.this, selected.toString(),
				// Toast.LENGTH_LONG).show();
				selected = latlng;
			}
		});

		Button bSubmitAdd = (Button) findViewById(R.id.buttonSubmitAddFav);

		bSubmitAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (validateAdd()) {
					new AddFavsTask().execute();
				}
			}
		});

	}

	public boolean validateAdd() {
		if (etFavName.getText().toString().equals("") || selected == null) {
			Toast.makeText(AddFavoriteActivity.this, "Add Failed",
					Toast.LENGTH_SHORT).show();
			return false;

		} else {
			return true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_favorite, menu);
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

	class AddFavsTask extends AsyncTask<String, Integer, String> {

		ProgressDialog dialog = new ProgressDialog(AddFavoriteActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setTitle("Adding Favorites");
			dialog.setMessage("Adding....");
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

				u = new URI("http://172.16.8.105:8080/MyWayWeb/addFavorite");
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

				urlparameters.add(new BasicNameValuePair("name", etFavName
						.getText().toString()));
				String lat = selected.latitude + "";
				String lng = selected.longitude + "";
				urlparameters.add(new BasicNameValuePair("latitude", lat));
				urlparameters.add(new BasicNameValuePair("longitude", lng));
				urlparameters.add(new BasicNameValuePair("username",
						tempUsername));

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
			Log.d("test: ", result);
			Intent i = new Intent(AddFavoriteActivity.this,
					FavoritesViewActivity.class);
			startActivity(i);
		}

	}

}
