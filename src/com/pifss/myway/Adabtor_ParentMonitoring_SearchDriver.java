package com.pifss.myway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class Adabtor_ParentMonitoring_SearchDriver extends BaseAdapter{

	String[] names;
	Context ctxt;
	LayoutInflater myInflater;
	
	public Adabtor_ParentMonitoring_SearchDriver(String[] arr, Context c){
		names = arr;
		ctxt = c;
		myInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return names[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup vg) {
		// TODO Auto-generated method stub
		
		if(view == null)
		{
			view = myInflater.inflate(android.R.layout.simple_list_item_1, vg , false);
		}
		
		TextView name = (TextView) view.findViewById(android.R.id.text1);
		name.setText(names[arg0]+"");
		
		return view;
	}

}
