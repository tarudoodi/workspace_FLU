package com.example.flunetwork.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.api.client.util.DateTime;
import com.example.entity.eventendpoint.model.Event;
import com.example.flunetwork.R;
import com.example.helper.GPSTracker;
import com.example.helper.MyGlobal;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class EventDetailActivity extends FragmentActivity{

	GoogleMap map;
	Event currentEvent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapDisplayLoc)).getMap();
		
		currentEvent = new Event();
		currentEvent.setEventDescription("This is a default description");
		currentEvent.setEventLat(29.6433692 + .01);
		currentEvent.setEventLong(-82.3474775);
		currentEvent.setEventName("Awesome Event");
		//currentEvent.setEventTime(new Date());
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MyGlobal.currentLoc = new GPSTracker(this);
		if(MyGlobal.currentLoc.canGetLocation())
		{
			MyGlobal.currentLoc.getLocation();
		}
		else
		{
			MyGlobal.currentLoc.showSettingsAlert();
		}
		
		LatLng UserLatLng = new LatLng(MyGlobal.currentLoc.getLatitude(), MyGlobal.currentLoc.getLongitude());
		LatLng EventLatLng = new LatLng(currentEvent.getEventLat(),currentEvent.getEventLong());
		
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		String eventAdrressLine = "";
		List<Address> addresses = null;
		try 
		{
			addresses = geoCoder.getFromLocation(currentEvent.getEventLat(), currentEvent.getEventLong(), 1);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(addresses != null)
		{
			eventAdrressLine = addresses.get(0).getAddressLine(0);
		}
		else 
		{
			eventAdrressLine = "Event Location";
		}
		Marker userLocation = map.addMarker(new MarkerOptions().position(UserLatLng)
				.title("Your location").flat(false).icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher)));
		Marker eventLocation = map.addMarker(new MarkerOptions().position(EventLatLng)
				.title(eventAdrressLine).flat(false));
		// Move the camera instantly to current location with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(EventLatLng, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_detail, menu);
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

}
