package com.example.flunetwork.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.entity.eventendpoint.model.Event;
import com.example.flunetwork.R;


public class EventAdapter extends ArrayAdapter<Event> {
	   
//	   private List<events> planetList;
	   //ArrayList<Event> myEvents = Event.getList(); TODO
	   private Context context;
	    
	   public EventAdapter(List<Event> myEvents , Context ctx) {
	       super(ctx, R.layout.event_list_item, myEvents);
	   //    this.myEvents = (ArrayList<Event>) myEvents; TODO
	       this.context = ctx;
	   }
	    
	   public View getView(int position, View convertView, ViewGroup parent) {
	        
	       // First let's verify the convertView is not null
	       if (convertView == null) {
	           // This a new view we inflate the new layout
	           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	           convertView = inflater.inflate(R.layout.event_list_item, parent, false);
	       }
	           // Now we can fill the layout with the right values
	           TextView eventNameView = (TextView) convertView.findViewById(R.id.eventName);
	           TextView eventLocationView = (TextView) convertView.findViewById(R.id.eventLocation);
	           TextView eventTimeView = (TextView) convertView.findViewById(R.id.eventTime);
	           //Event eventId = myEvents.get(position); TODO
	           
	           // TODO remove comments
	           //eventNameView.setText(eventId.getEventName());
	           //eventLocationView.setText(eventId.getEventLocation().getLatitude().toString()+", "+eventId.getEventLocation().getLongitude().toString());
	           //eventTimeView.setText(eventId.getEventTime().toString());
	           //distView.setText("" + p.getDistance());
	       return convertView;
	   }
	   
		public ArrayList<Event> getList()
		{
			ArrayList<Event> EventList = null;
			for(int i=1;i<9;i++)
			{
				//TODO
				/*Event event= new Event();
				event.setEventLocation(new FluLocation(0,0))= "location " + i;
				event.eventName = "Event number " + i;
				event.eventTime = "Time "+ i;
				
				EventList.add(event);*/
			}
			return EventList;
		}
}
