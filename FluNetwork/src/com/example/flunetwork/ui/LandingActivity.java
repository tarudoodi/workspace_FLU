package com.example.flunetwork.ui;

import java.io.IOException;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.entity.eventendpoint.Eventendpoint;
import com.example.entity.eventendpoint.model.CollectionResponseEvent;
import com.example.entity.eventendpoint.model.Event;
import com.example.entity.userendpoint.Userendpoint;
import com.example.entity.userendpoint.model.PhoneNumber;
import com.example.entity.userendpoint.model.User;
import com.example.flunetwork.CloudEndpointUtils;
import com.example.flunetwork.R;
import com.example.flunetwork.helper.MyGlobal;
import com.example.flunetwork.ui.EventAdapter;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;


public class LandingActivity extends Activity implements OnClickListener{

	ArrayList<Event> myEvents;  //TODO remove this line 
	//ArrayList<Event> myEvents = Event.getList(); TODO
	EventAdapter myEventAdapter;
	Button addEventButton;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		// initList();
		lv  = (ListView) findViewById(R.id.listView);
		addEventButton = (Button)findViewById(R.id.eventAddButton); // Bind the button within a method

		//We get the ListView component from the layout
		// This is a simple adapter that accepts as parameter
		// Context
		// Data list
		// The row layout that is used during the row creation
		// The keys used to retrieve the data
		// The View id used to show the data. The key number and the view id must match

		//simpleAdpt = new SimpleAdapter(this, planetsList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});
		if(myEvents == null)
		{
			myEvents = new ArrayList<Event>();
		}
			
		new GetEventsTask().execute();
		
		//myEventAdapter = new EventAdapter(myEvents, this);
		//lv.setAdapter(myEventAdapter);
		registerForContextMenu(lv);   // REGISTER FOR CONTEXT MENU  -- FOR THE OPTIONS ON LIST

		// React to user clicks on item

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position,long id) {
				//TextView clickedView = (TextView) view; // We know the View is a TextView so we can cast it
				//setContentView(R.layout.activity_event_detail);
				startActivity(new Intent(getApplicationContext(),EventDetailActivity.class));
				//Toast.makeText(MainActivity.this, "Item with id ["+id+"] - Position ["+position+"] " + "- Planet ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();
			}
		});		


		//user click to add event; just redirect to the addEvent layout.
		// We want to create a context Menu when the user long click on an item
		// Replace this by swipe to hide 

		addEventButton.setOnClickListener(this);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		// Implements our logic
		Toast.makeText(this, "Item id ["+itemId+"]", Toast.LENGTH_SHORT).show();
		return true;
	}


	// This isnt the launcher activity.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
		if(v == addEventButton)
		{
			//create a new intent that will launch the new 'page'
			Intent addEventIntent = new Intent(LandingActivity.this, AddEventActivity.class);
			startActivity(addEventIntent);
		}
	}


	/**
	 * AsyncTask for calling Mobile Assistant API for getting the list of events
	 */
	private class GetEventsTask extends AsyncTask<Void, Void, Void> {

		/**
		 * Calls appropriate CloudEndpoint to indicate that user checked into a place.
		 *
		 * @param params the place where the user is checking in.
		 */
		private CollectionResponseEvent eventList = null;

		@Override
		protected Void doInBackground(Void... params) {
				Eventendpoint.Builder builder = new Eventendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Eventendpoint endpoint = builder.build();


			try {
				eventList = endpoint.listEvent().execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Put some kind of progress bar
			super.onPostExecute(result);
			if(eventList != null)
			{
				myEvents.addAll(eventList.getItems());
				myEventAdapter = new EventAdapter(myEvents, getApplicationContext());
				lv.setAdapter(myEventAdapter);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Could not contact server. Will try to get events later !", Toast.LENGTH_SHORT).show();
			}
		}
	}

}