package models;

import java.util.Comparator;

public class EventDateComparator implements Comparator<Event> {

	@Override
	public int compare(Event event1, Event event2) {
		if(event1 != null && event2 != null && event1.getStart().after(event2.getStart())) {
			return 1;
		}
		
		return 0;
	}

}
