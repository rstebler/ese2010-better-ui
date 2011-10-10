package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import controllers.*;

import play.*;
import play.data.validation.CheckWith;
import play.data.validation.InPast;
import play.data.validation.Required;
import play.mvc.*;

import models.*;
@With(Secure.class)
public class Application extends Controller {

    public static void index() {
    	getCalendars(Security.connected());
    }
    
    public static void getUsers() {
    	User me = Database.users.get(Security.connected());
    	List<User> users = Database.getUserList();
        render(me, users);
    }
    
    public static void getCalendars(String username) {
    	User me = Database.users.get(Security.connected());
    	User user = Database.users.get(username);
    	List<Calendar> calendars = new LinkedList<Calendar>();
    	if(me != null && user != null) {
    		calendars = me.getCalendarList(user);
    	}
    	render(me, user, calendars);
    }
    
    public static void getEvents(String username, int calendarID, String dateString) {
    	User me = Database.users.get(Security.connected());
    	User user = Database.users.get(username);
    	
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		if(dateString != null) {
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				System.out.println("Error: Could not parse Date. Current date will be used.");
				date = new Date();
			}
		} else {
			date = new Date();
		}
    	
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		String selectedDate = dateFormat.format(date);
		String todayDate = dateFormat.format(new Date());
    	DateFormat dateFormat2 = new SimpleDateFormat("MMMM", new Locale("en","US"));  
    	String monthName = dateFormat2.format(date);
		Iterator<Event> events = null;
    	Calendar calendar = me.getCalendarByID(user, calendarID);
    	if(me != null && calendar != null) {
    		events = calendar.getEventIterator(date, me);
    	}
    	render(me, calendar, events, cal, selectedDate, todayDate, monthName, dateFormat);
    }
    
  public static void editEvent(int calendarID, int eventID, String dateString, String newEventName, String startDate, String startTime, String endDate, String endTime, boolean isPublic) {
	User me = Database.users.get(Security.connected());
	Calendar calendar = me.getCalendarByID(calendarID);
	Event event = null;
	Date date = new Date();
	DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	String timeString = dateFormat.format(date);
	if(me != null && calendar != null) {
		event = calendar.getEventByID(eventID);
	}
	render(me, calendar, event, dateString, timeString, newEventName, startDate, startTime, endDate, endTime, isPublic);
}
    
    public static void saveEvent(int calendarID, int eventID, @Required String newEventName, @Required String startDate, @Required String startTime, @Required String endDate, @Required String endTime, @Required boolean isPublic) {
    	//--- Date Parsing ---
    	
    	Date newStartTime = new Date();
    	Date newStartDate = new Date();
		Date newStart = new Date();
    	Date newEndTime = new Date();
    	Date newEndDate = new Date();
		Date newEnd = new Date();
		DateFormat dateFormatDate = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat dateFormatTime = new SimpleDateFormat("HH:mm");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Boolean invalidData = false;
		
		try {
			newStartDate = dateFormatDate.parse(startDate);
		} catch (ParseException e) {
			validation.addError("Start.invalidDate", "Invalid Date");
			invalidData = true;
		}
		try {
			newStartTime = dateFormatTime.parse(startTime);
		} catch (ParseException e) {
			validation.addError("Start.invalidTime", "Invalid Time");
			invalidData = true;
		}
		
		try {
			newEndDate = dateFormatDate.parse(endDate);
		} catch (ParseException e) {
			validation.addError("End.invalidDate", "Invalid Date");
			invalidData = true;
		}
		try {
			newEndTime = dateFormatTime.parse(endTime);
		} catch (ParseException e) {
			validation.addError("End.invalidTime", "Invalid Time");
			invalidData = true;
		}
	    
		try {
			newStart = dateFormat.parse(startDate+" "+startTime);
		} catch (ParseException e) {
		}
			try {
				newEnd = dateFormat.parse(endDate+" "+endTime);
		} catch (ParseException e) {
		}
		
	    if(validation.hasErrors() || newEnd.before(newStart)) {
	  		if(newEnd.before(newStart) && !invalidData) {
	   			validation.addError("endAfterStart", "Start before End");
	   		}
		   	params.flash(); // add http parameters to the flash scope
		   	validation.keep(); // keep the errors for the next request
		   	editEvent(calendarID, eventID, startDate, newEventName, startDate, startTime, endDate, endTime, isPublic);
	    }
		
	    //--- Date Parsing End ---
	    
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarByID(calendarID);
    	Event event = null;
	    
    	if(me != null && calendar != null) {
    		event = calendar.getEventByID(eventID);
   			
    		if(event != null) {
       			event.setName(newEventName, me);
       			event.setStart(newStart, me);
    			event.setEnd(newEnd, me);
    			event.setIsPublic(isPublic, me);
    		} else {
    			event = new Event(newEventName, newStart, newEnd, isPublic, me);
    			calendar.addEvent(event, me);
    		}
    	}
		String dateString = dateFormatDate.format(newStart);
    	getEvents(me.getName(), calendarID, dateString);
    }
    
    public static void deleteEvent(int calendarID, int eventID) {
    	User me = Database.users.get(Security.connected());
    	Calendar calendar = me.getCalendarByID(calendarID);
	    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    Date date = new Date();
    	if(me != null && calendar != null) {
    		Event event = calendar.getEventByID(eventID);	
    		date = (event != null)?event.getStart():date;
    		calendar.removeEvent(event, me);
    	}
    	String dateString = dateFormat.format(date);
    	getEvents(me.getName(), calendarID, dateString);
    }

}