package com.example.flunetwork.ui;

import java.io.IOException;

import com.example.entity.userendpoint.Userendpoint;
import com.example.entity.userendpoint.model.PhoneNumber;
import com.example.flunetwork.CloudEndpointUtils;
import com.example.flunetwork.R;
import com.example.helper.GPSTracker;
import com.example.helper.MyGlobal;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterActivity extends Activity implements OnClickListener{

	Button registerBtn;
	double latitude;
	double longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		registerBtn = (Button)findViewById(R.id.regButton); 
		registerBtn.setOnClickListener(this);
		MyGlobal.currentLoc = new GPSTracker(this);	
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(MyGlobal.currentLoc.canGetLocation())
		{
			MyGlobal.currentLoc.getLocation();
			latitude = MyGlobal.currentLoc.getLatitude();
			longitude = MyGlobal.currentLoc.getLongitude();
		}
		else
		{
			MyGlobal.currentLoc.showSettingsAlert();
		}
		
		if(MyGlobal.currentUser == null)
		{
			registerBtn.setVisibility(Button.VISIBLE);
		}
		else
		{
			startActivity(new Intent(this,LandingActivity.class));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events_display, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v == registerBtn)
		{
			new CreateUserTask().execute();
			startActivity(new Intent(this,LandingActivity.class));
		}
	}
	
	
	/**
	   * AsyncTask for calling Mobile Assistant API for checking into a place (e.g., a store)
	   */
	  private class CreateUserTask extends AsyncTask<Void, Void, Void> {

	    /**
	     * Calls appropriate CloudEndpoint to indicate that user checked into a place.
	     *
	     * @param params the place where the user is checking in.
	     */
	    @Override
	    protected Void doInBackground(Void... params) {
	      
	      // Set the ID of the store where the user is.
	      // This would be replaced by the actual ID in the final version of the code.
	      MyGlobal.currentUser.setUserName("Taru");
	      MyGlobal.currentUser.setPhoneNumber(new PhoneNumber().setNumber("1234567890"));
	      //user.setLocation(new FluLocation(latitude,longitude)); TODO
	      
	      Userendpoint.Builder builder = new Userendpoint.Builder(
	          AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
	          null);

	      builder = CloudEndpointUtils.updateBuilder(builder);

	      Userendpoint endpoint = builder.build();


	      try {
	        endpoint.insertUser(MyGlobal.currentUser).execute();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }

	      return null;
	    }
	  }
}
