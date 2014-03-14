package com.example.flunetwork.ui;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.example.entity.eventendpoint.Eventendpoint;
import com.example.entity.eventendpoint.model.Event;
import com.example.entity.userendpoint.Userendpoint;
import com.example.entity.userendpoint.model.PhoneNumber;
import com.example.entity.userendpoint.model.User;
import com.example.flunetwork.CloudEndpointUtils;
import com.example.flunetwork.R;
import com.example.flunetwork.R.layout;
import com.example.helper.CustomDateTimePicker;
import com.example.helper.MyGlobal;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Data;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddEventActivity extends Activity{

	CustomDateTimePicker custom;
	
	EditText eventName;
	EditText eventLocation;
	EditText eventDescription;
	Event event; 
	Button setEventTimeBtn;
	
	String eventNameString;
	String eventLocationString;
	String eventTimeString;
	String eventDescriptionString; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);

		// submit event button	
		Button submitEventButton = (Button) findViewById(R.id.submitEventButton);
		submitEventButton.setOnClickListener(submitEvent) ;

		// get all view IDs for text
		eventName = (EditText) findViewById(R.id.eventNameWhat);
		eventLocation = (EditText) findViewById(R.id.eventLocationWhere);
		eventDescription = (EditText) findViewById(R.id.eventDescription);
		setEventTimeBtn = (Button) findViewById(R.id.setDateTimeBtn);

		// instantiate to create an event
		event = new Event();
		
		custom = new CustomDateTimePicker(this,
	            new CustomDateTimePicker.ICustomDateTimeListener() {

	                @Override
	                public void onSet(Dialog dialog, Calendar calendarSelected,
	                        Date dateSelected, int year, String monthFullName,
	                        String monthShortName, int monthNumber, int date,
	                        String weekDayFullName, String weekDayShortName,
	                        int hour24, int hour12, int min, int sec,
	                        String AM_PM) {
	                	/*setEventTimeBtn
	                            .setText(calendarSelected
	                                            .get(Calendar.DAY_OF_MONTH)
	                                   + "/" + (monthNumber+1) + "/" + year
	                                    + ", " + hour12 + ":" + min
	                                    + " " + AM_PM);*/
	                	String minutes = String.valueOf(min);
	                	if(min<10)
	                	{
	                		minutes = "0"+minutes;
	                	}
	                	setEventTimeBtn
                        .setText(calendarSelected
                                        .get(Calendar.DAY_OF_MONTH)
                               + "/" + (monthNumber+1) + "/" + year
                                + ", " + hour24 + minutes + " hrs");
	                }

	                @Override
	                public void onCancel() {

	                }
	            });
	    /**
	     * Pass Directly current time format it will return AM and PM if you set
	     * false
	     */
	    custom.set24HourFormat(true);
	    /**
	     * Pass Directly current data and time to show when it pop up
	     */
	    custom.setDate(Calendar.getInstance());

	    setEventTimeBtn.setOnClickListener(
	            new OnClickListener() {

	                @Override
	                public void onClick(View v) {
	                    custom.showDialog();
	                }
	            });
	}


	//@Override
	public OnClickListener submitEvent = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Boolean incompleteFlag = false;
			// Make the colors to default
			eventName.setBackgroundColor(Color.TRANSPARENT);
			
			eventNameString = eventName.getText().toString();
			eventLocationString = eventLocation.getText().toString();
			eventDescriptionString = eventDescription.getText().toString();

			if(eventNameString == null || eventNameString.isEmpty())
			{
				eventName.setBackgroundColor(Color.LTGRAY);
				incompleteFlag = true;
			}
			
			if(incompleteFlag)
			{
				Toast.makeText(getApplicationContext(), "Looks like you missed these!!!", Toast.LENGTH_SHORT).show();
			}
			// check this comparison
			if ((eventNameString.equals("")  || eventLocationString.equals("") || eventTimeString.equals("") || eventDescriptionString.equals(""))) 
			{
				Toast.makeText(getApplicationContext(),"Please fill all the fields.",Toast.LENGTH_SHORT).show();
			} 
			else 
			{
				new CreateEventTask().execute();
			}
		}
	};
	
	/**
	   * AsyncTask for calling Mobile Assistant API for checking into a place (e.g., a store)
	   */
	  private class CreateEventTask extends AsyncTask<Void, Void, Void> {

	    /**
	     * Calls appropriate CloudEndpoint to indicate that user checked into a place.
	     *
	     * @param params the place where the user is checking in.
	     */
		@Override
	    protected Void doInBackground(Void... params) {
	    	// TODO eventLocationString == reverse geocode it.
	    	
	    	double eventLat = 0;
	    	double eventLong = 0;
	    	//new date
	    	
	    	Event newEvent = null;
			if(MyGlobal.currentUser.getIsPublisher() == true)
			{
				newEvent = new Event();
				newEvent.setEventName(eventNameString);
				newEvent.setEventLat(eventLat);
				newEvent.setEventLong(eventLong);
				//newEvent.setEventTime(
				
			}
	      
	      //user.setLocation(new FluLocation(latitude,longitude)); TODO
	      
	      Eventendpoint.Builder builder = new Eventendpoint.Builder(
	          AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
	          null);

	      builder = CloudEndpointUtils.updateBuilder(builder);

	      Eventendpoint endpoint = builder.build();


	      try {
	        endpoint.insertEvent(newEvent).execute();
	      } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }

	      return null;
	    }
	  }
}


