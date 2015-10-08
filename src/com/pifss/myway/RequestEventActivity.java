package com.pifss.myway;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestEventActivity extends Activity {

	private TextView mDateDisplay;
	private Button mPickDate;

	ImageView eventImage;
	InformationManager imm;
	
	
	private TextView mDateDisplay2;
	private Button mPickDate2;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private int flag;

	static final int DATE_DIALOG_ID = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_event);
		
		   // capture our View elements
        mDateDisplay = (TextView) findViewById(R.id.textView1);
        mPickDate = (Button) findViewById(R.id.button2);
        mDateDisplay2 = (TextView) findViewById(R.id.textView2);
        mPickDate2 = (Button) findViewById(R.id.button3);
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
        updateDisplay();
        
      eventImage = (ImageView) findViewById(R.id.imageView1);
//      imm = new InformationManager(this);
//
//		// set the old image in the imageView
//		
//		Bitmap bm = imm.readImage();
//		if (bm != null){
//		eventImage.setImageBitmap(imm.readImage());
//		}
//		eventImage.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(Intent.ACTION_PICK,
//						MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//				startActivityForResult(i, 1);
//			}
//		});
		
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
	        return new DatePickerDialog(this,
	                    mDateSetListener,
	                    mYear, mMonth, mDay);
	    }
	    return null;
	}
	
    // updates the date we display in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
        
        
    }
    
    private void updateDisplay2(){
    	mDateDisplay2.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }
    
  
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    if (flag == 1){
                    	updateDisplay();
                    }
                    if (flag == 2){
                    	updateDisplay2();
                    }
                }
            };
            
            
//            @Override
//        	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        		// TODO Auto-generated method stub
//        		super.onActivityResult(requestCode, resultCode, data);
//
//        		if (requestCode == 1 && resultCode == RESULT_OK) {
//        			// *************Content Provider Knowledge************//
//        			Uri d = data.getData();
//        			String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//        			Cursor cursor = getContentResolver().query(d, filePathColumn, null,
//        					null, null);
//
//        			cursor.moveToFirst();
//
//        			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//
//        			if (columnIndex < 0) // no column index
//        				return; // DO YOUR ERROR HANDLING
//
//        			// ********************************************//
//
//        			String picturePath = cursor.getString(columnIndex);
//
//        			cursor.close(); // close cursor
//
//        			Bitmap map = BitmapFactory.decodeFile(picturePath);
//
//        			eventImage.setImageBitmap(map);
//
//        			imm.writeImage(map);
//        		}
//        	}
}
