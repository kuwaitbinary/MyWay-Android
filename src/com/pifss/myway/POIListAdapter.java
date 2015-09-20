package com.pifss.myway;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class POIListAdapter extends BaseAdapter{
	ArrayList<String> poi=new ArrayList<String>();
	ArrayList<String> poiDescription= new ArrayList<String>();
	
	Context context;
	LayoutInflater layoutInflater;
	
	public POIListAdapter(ArrayList<String> poi,ArrayList<String> poiDesc, Context context) {
		super();
		this.poi = poi;
		this.poiDescription=poiDesc;
		this.context = context;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return poi.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return poi.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int pos, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View myView= layoutInflater.inflate(R.layout.activity_poi_customlist, null);
		
		TextView poitvName = (TextView) myView.findViewById(R.id.textViewPOIName);
		TextView poitvDesc = (TextView) myView.findViewById(R.id.textViewPOIDesc);
		
		poitvName.setText(poi.get(pos));
		poitvDesc.setText(poiDescription.get(pos));
		ImageButton remove = (ImageButton) myView.findViewById(R.id.imgTrash);
		remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String PREF_NAME = "userInformation"; 
				final SharedPreferences prefs = v.getContext().getSharedPreferences(PREF_NAME, v.getContext().MODE_APPEND);
				
				if(prefs.getBoolean("isLoggedIn", false)){
					Builder b = new Builder(context);
					b.setTitle("Confirm deletion");
					b.setMessage("Are you sure you want to remove this item from your list?");
					b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(context, "Removed!", Toast.LENGTH_SHORT).show();
							poi.remove(getItem(pos));
//							DailyRoutesAdapter refreshed = new DailyRoutesAdapter(routes, context);
							
						                notifyDataSetChanged();
						        
						}
					});
			
					
					b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(context, "Cancelled!", Toast.LENGTH_SHORT).show();
						}
					});
					Dialog d = b.create();
					d.show();
				} else {
					Toast.makeText(v.getContext(), "Log in to delete!", Toast.LENGTH_SHORT).show();;
				}
				
				
			}
		});
		
		myView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(context, POI3Activity.class);
	             //Get the value of the item you clicked
			
            //int itemClickedname = (Integer) arg0.getAdapter().getItem(pos);
            
				 String itemName =poi.get(pos);
	             intent.putExtra("Name", itemName);
	             
	             intent.putExtra("Description", poiDescription.get(pos));
	             Toast.makeText(context, "Item CLicked is ", Toast.LENGTH_LONG).show();
	             context.startActivity(intent);
				
			}
		});
		
		return myView;
	}
	
	
}
