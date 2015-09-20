package com.pifss.myway;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	
	ArrayList<ItemMenu> list;
	Context context;
	LayoutInflater inflater;

	public MenuAdapter(ArrayList<ItemMenu> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}
	
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return list.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View v, ViewGroup vg) {
		// TODO Auto-generated method stub
		
		View myView=inflater.inflate(R.layout.list_row, null);
		ItemMenu m=list.get(pos);
		
		// Get 2 views
		
		
		TextView title=(TextView) myView.findViewById(R.id.textView1);
		ImageView imgView= (ImageView) myView.findViewById(R.id.imageView1);

		title.setText(m.getTitle());
      
        imgView.setImageBitmap(m.getImage());
		 
		
		
		
		
		
		return myView;
	}

}
