package models;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class User {
	
	private String name;
	private String password;
	private HashMap<Integer, Calendar> calendars = new HashMap<Integer, Calendar>();
	
	public User(String name, String password) {
		this.name = name;
		Calendar calendar = new Calendar(this.name + "'s Calendar", this);
		this.calendars.put(calendar.getID(), calendar);
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}

	public Iterator<Calendar> getCalendarIterator() {
		return getCalendarIterator(this);
	}
	
	public Iterator<Calendar> getCalendarIterator(User user) {
		return user.getCalendarList().iterator();
	}
	
	public List<Calendar> getCalendarList() {
		return getCalendarList(this);
	}
	
	public List<Calendar> getCalendarList(User user) {
		List<Calendar> calendars = new LinkedList<Calendar>();
		for(Calendar calendar: user.calendars.values()) {
			calendars.add(calendar);
		}
		return calendars;
	}
	
	public int GetNumberOfCalendars() {
		return this.calendars.size();
	}
	
	public void addCalendar(String name, User owner) {
		Calendar calendar = new Calendar(name, this);
		addCalendar(calendar);
	}
	
	public void addCalendar(Calendar calendar) {
		if(calendar != null && calendar.getOwner().equals(this)) {
			this.calendars.put(calendar.getID(), calendar);
		}
	}
	
	public void removeCalendar(Calendar calendar) {
		if(calendar != null && calendar.getOwner().equals(this)) {
			this.calendars.remove(calendar.getID());
		}
	}
	
	public Calendar getCalendarByID(int calendarID) {
		return getCalendarByID(this, calendarID);
	}

	public Calendar getCalendarByID(User user, int calendarID) {
		if(user.calendars.containsKey(calendarID)) {
			return user.calendars.get(calendarID);
		}
		return null;
	}
	
}
