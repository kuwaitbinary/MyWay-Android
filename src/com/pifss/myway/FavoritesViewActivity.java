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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FavoritesViewActivity extends Activity {

	ArrayList<String> favoritesNames = new ArrayList<String>();
	ArrayList<Favorite> favoritesList = new ArrayList<Favorite>();
	ListView lvFavs;
//	String actionFlag = "";
	int favToDelete;
	public final static String PREF_NAME = "userInformation";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_view);
		// retrieve the list of favorites
		// actionFlag = "view";
		new RetrieveFavsTask().execute();

		Button addFav = (Button) findViewById(R.id.buttonAddFav);

		addFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FavoritesViewActivity.this,
						AddFavoriteActivity.class);
				startActivity(i);
				// actionFlag = "add";
				// new RetrieveFavsTask().execute();
			}
		});

		// use the following line to add the sliding menu to the current page
		SlidingUtil.SetSliding(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorites_view, menu);
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

	// to send the user to the home page when pressing the back button
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(FavoritesViewActivity.this, Home.class);
		startActivity(i);
		finish();
	}

	class RetrieveFavsTask extends AsyncTask<String, Integer, String> {

		ProgressDialog dialog = new ProgressDialog(FavoritesViewActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setTitle("Loading Favorites");
			dialog.setMessage("Loading....");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				ArrayList<BasicNameValuePair> urlparameters = new ArrayList<BasicNameValuePair>();

				URI u = new URI(
						"http://172.16.8.105:8080/MyWayWeb/viewAllFavorites");
				DefaultHttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(u);

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
			// convert the json string to arraylist of favorites

			try {
				JSONObject jsnobject = new JSONObject(result);
				JSONArray jsonArray = jsnobject.getJSONArray("result_data");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject FavObject = jsonArray.getJSONObject(i);
					String Fname = FavObject.getString("name");
					String Flat = FavObject.getString("latitude");
					String Flong = FavObject.getString("longitude");
					Favorite tempFav = new Favorite(Fname, Flat, Flong);
					favoritesList.add(tempFav);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// set the names in the favoritesnames list to be displayed in the
			// listView
			for (int i = 0; i < favoritesList.size(); i++) {
				Favorite f = favoritesList.get(i);
				String name = f.getName();
				favoritesNames.add(name);
			}

			// ***********Controller********************//

			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					FavoritesViewActivity.this,
					android.R.layout.simple_list_item_1, favoritesNames);

			// ***************View****************//
			lvFavs = (ListView) findViewById(R.id.listViewFavorites);

			lvFavs.setAdapter(adapter);

			lvFavs.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Favorite f = favoritesList.get(position);
					Intent i = new Intent(FavoritesViewActivity.this,
							MapPointActivity.class);
					i.putExtra("name", f.getName());
					i.putExtra("lat", f.getLatitude());
					i.putExtra("long", f.getLongitude());
					startActivity(i);
				}

			});

			dialog.dismiss();

			lvFavs.setLongClickable(true);

			lvFavs.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					removeItemFromList(position);
					return true;
				}

			});
		}

	}

	class DeleteFavTask extends AsyncTask<String, Integer, String> {

		ProgressDialog dialog = new ProgressDialog(FavoritesViewActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setTitle("Deleting Favorites");
			dialog.setMessage("Deleting....");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				ArrayList<BasicNameValuePair> urlparameters = new ArrayList<BasicNameValuePair>();

				URI u = new URI(
						"http://172.16.8.105:8080/MyWayWeb/deleteFavorite");
				DefaultHttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(u);

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
				urlparameters.add(new BasicNameValuePair("name", favoritesNames
						.get(favToDelete)));
				urlparameters.add(new BasicNameValuePair("username",
						tempUsername));

				post.setEntity(new UrlEncodedFormEntity(urlparameters));

				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);

				// Log.d("test:", data);
				favoritesList.clear();
				favoritesNames.clear();
				favToDelete = -1;
//				actionFlag = "";
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
			dialog.dismiss();
			new RetrieveFavsTask().execute();

		}

	}

	public void removeItemFromList(final int position) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				FavoritesViewActivity.this);
		// Add the buttons
		builder.setTitle("Delete " + favoritesNames.get(position) + "?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
//				actionFlag = "delete";
				favToDelete = position;
				new DeleteFavTask().execute();

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// dismissDialog(id);
					}
				});

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();

	}

}
