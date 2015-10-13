package com.pifss.myway;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.os.Bundle;
import com.pifss.myway.Constants;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class speedClass extends Activity implements GPSCallback{
    private GPSManager gpsManager = null;
    private double speed = 0.0;
    private int measurement_index = Constants.INDEX_KM;
    private AbsoluteSizeSpan sizeSpanLarge = null;
    private AbsoluteSizeSpan sizeSpanSmall = null;

@Override
public void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    
    
    gpsManager = new GPSManager();
    
    gpsManager.startListening(getApplicationContext());
    gpsManager.setGPSCallback(this);
    
   
    
    measurement_index = SpeedAppSettings.getMeasureUnit(this);
}

    @Override
    public void onGPSUpdate(Location location) 
    {
            location.getLatitude();
            location.getLongitude();
            speed = location.getSpeed();
            
            String speedString = "" + roundDecimal(convertSpeed(speed),2);
            String unitString = measurementUnitString(measurement_index);
            
            
    }

    @Override
    protected void onDestroy() {
            gpsManager.stopListening();
            gpsManager.setGPSCallback(null);
            
            gpsManager = null;
            
            super.onDestroy();
    }
            
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            boolean result = true;
    
//            switch(item.getItemId())
//            {
//                    
//                    case R.id.unit_km:
//                    {
//                            measurement_index = 0;
//                            
//                            SpeedAppSettings.setMeasureUnit(this, 0);
//                            
//                            break;
//                    }
//                   
//                    default:
//                    {
//                            result = super.onOptionsItemSelected(item);
//                            
//                            break;
//                    }
//            }
            
            return result;
    }

    private double convertSpeed(double speed){
            return ((speed * Constants.HOUR_MULTIPLIER) * Constants.UNIT_MULTIPLIERS[measurement_index]); 
    }
    
    private String measurementUnitString(int unitIndex){
            String string = "";
            
            switch(unitIndex)
            {
                    case Constants.INDEX_KM:                string = "km/h";        break;
                   
            }
            
            return string;
    }
    
    private double roundDecimal(double value, final int decimalPlace)
    {
            BigDecimal bd = new BigDecimal(value);
            
            bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
            value = bd.doubleValue();
            
            return value;
    }
    
    private void setSpeedText(int textid,String text)
    {
            Spannable span = new SpannableString(text);
            int firstPos = text.indexOf(32);
            
            span.setSpan(sizeSpanLarge, 0, firstPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(sizeSpanSmall, firstPos + 1, text.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            TextView tv = ((TextView)findViewById(textid));
            
            tv.setText(span);
    }
    
    
}
