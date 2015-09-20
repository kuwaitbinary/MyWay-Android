package com.pifss.myway;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class ParentMonitoring_Report_CustomAdapter extends BaseAdapter   implements OnClickListener {
    
	/*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList<ParentMonitoring_Report_ListModel> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ParentMonitoring_Report_ListModel tempValues=null;
    private Context ctxc;
    int i=0;
    
    /*************  CustomAdapter Constructor *****************/
    public ParentMonitoring_Report_CustomAdapter(Activity a, ArrayList<ParentMonitoring_Report_ListModel> d,Resources resLocal) {
    	
    	/********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;
        
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }
    
    public ParentMonitoring_Report_CustomAdapter(Context c, ArrayList<ParentMonitoring_Report_ListModel> array, Resources resLocal)
    {
    	ctxc = c;
    	data = array;
    	inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {
    	
    	if(data.size()<=0)
    		return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    /********* Create a holder to contain inflated xml file elements ***********/
    public static class ViewHolder{
    	
        public TextView text;
        public TextView text1;
        public TextView textWide;
        public ImageView image;

    }

    /*********** Depends upon data size called for each row , Create each ListView row ***********/
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View vi=convertView;
        ViewHolder holder;
        
        if(convertView==null){ 
        	
        	/********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflater.inflate(R.layout.tabitem, null); 
            
            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder=new ViewHolder();
            holder.text=(TextView)vi.findViewById(R.id.text);
            holder.text1=(TextView)vi.findViewById(R.id.text1);
            holder.image=(ImageView)vi.findViewById(R.id.image);
            
           /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }
        else  
            holder=(ViewHolder)vi.getTag();
        
        if(data.size()<=0)
        {
        	holder.text.setText("No Data");
            
        }
        else
        {
        	/***** Get each Model object from Arraylist ********/
	        tempValues=null;
	        tempValues = (ParentMonitoring_Report_ListModel) data.get(position);
	        
	        /************  Set Model values in Holder elements ***********/
	        
	        if ( tempValues.getReportField() == "Speed" ) {
	        	holder.text.setText(tempValues.getReportField());
		        holder.text1.setText(tempValues.getDate());
		        holder.image.setImageResource(res.getIdentifier("com.pifss.myway:drawable/"+tempValues.getImage(),null,null));
	        }
	        else if ( tempValues.getReportField() == "Stop" ) {
	        	holder.text.setText(tempValues.getReportField());
		        holder.text1.setText(tempValues.getDate());
		        holder.image.setImageResource(res.getIdentifier("com.pifss.myway:drawable/"+tempValues.getImage(),null,null));
	        }
	        else if ( tempValues.getReportField() == "Phone Status" ) {
	        	holder.text.setText(tempValues.getReportField());
		        holder.text1.setText(tempValues.getDate());
		        holder.image.setImageResource(res.getIdentifier("com.pifss.myway:drawable/"+tempValues.getImage(),null,null));
	        }
	        else if ( tempValues.getReportField() == "Application Status" ) {
	        	holder.text.setText(tempValues.getReportField());
		        holder.text1.setText(tempValues.getDate());
		        holder.image.setImageResource(res.getIdentifier("com.pifss.myway:drawable/"+tempValues.getImage(),null,null));
	        }
	        else if ( tempValues.getReportField() == "Battery Status" ) {
	        	holder.text.setText(tempValues.getReportField());
		        holder.text1.setText(tempValues.getDate());
		        holder.image.setImageResource(res.getIdentifier("com.pifss.myway:drawable/"+tempValues.getImage(),null,null));
	        }
	         
	         
	         /******** Set Item Click Listner for LayoutInflater for each row ***********/
	         vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }
    
    @Override
    public void onClick(View v) {
            Log.v("CustomAdapter", "=====Row button clicked");
    }
    
    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{           
        private int mPosition;
        
        OnItemClickListener(int position){
        	 mPosition = position;
        }
        
        @Override
        public void onClick(View arg0) {
            ParentMonitoring_Report_CustomListView sct = (ParentMonitoring_Report_CustomListView)activity;
        	sct.onItemClick(mPosition);
        }               
    }   
}