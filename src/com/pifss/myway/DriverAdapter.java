package com.pifss.myway;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DriverAdapter extends BaseAdapter {
	
	private ArrayList<Driver> list;
	private Context context;
	private LayoutInflater inflater;
	
	public DriverAdapter(ArrayList<Driver> list, Context context) {
		this.list = list;
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" }) @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View inflate = inflater.inflate(R.layout.driver_list_row, null);
		View view = inflate;
		
		final Driver driver = list.get(position);
		
		TextView driverTimeTextView = (TextView) view.findViewById(R.id.textViewDriverSettedTime);
		TextView driverDateTextView = (TextView) view.findViewById(R.id.textViewDriverSettedtDate);
		ImageButton deleteDriverButton = (ImageButton) view.findViewById(R.id.deleteDriver);
		
		driverTimeTextView.setText(driver.getTime());
		driverDateTextView.setText(driver.getDay());
		deleteDriverButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Builder builder = new Builder(context);
				
				builder.setTitle(R.string.delete_driver_dialog_title);
				builder.setMessage(R.string.delete_driver_dialog_message);
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setCancelable(false);
				
				builder.setPositiveButton(R.string.delete_driver_dialog_positive_button, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Toast.makeText(context, R.string.delete_driver_toast_message, Toast.LENGTH_LONG).show();
						
						list.remove(position);
						notifyDataSetChanged();
						ParentalMonitoringConnectionManager.removeDriver(driver);
						
					}
				});
				
				builder.setNegativeButton(R.string.delete_driver_dialog_negative_button, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				
				Dialog dialog = builder.create();
				dialog.show();
				
			}
		});
		
		return view;
		
	}
	
}