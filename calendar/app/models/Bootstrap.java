package models;

import java.util.Date;

import play.jobs.*;

@OnApplicationStart
public class Bootstrap extends Job {
    
    public void doJob() {
    	
    	User user;
    	Event event;
    	Date now = new Date();
    	
    	
    	// Username: Sepp
    	// Password: test
    	user = new User("Sepp", "test");
    	event=new Event("Sepp's Event 1", now, now, true, user);
    	user.getCalendarIterator().next().addEvent(event, user);
    	event=new Event("Sepp's Event 2", now, now, false, user);
    	user.getCalendarIterator().next().addEvent(event, user);
    	Database.addUser(user);
    	
    	// Username: Fritz
    	// Password: 123
    	user = new User("Fritz", "123");
    	event=new Event("Fritz's Event 1", now, now, false, user);
    	user.getCalendarIterator().next().addEvent(event, user);
    	event=new Event("Fritz's Event 2", now, now, true, user);
    	user.getCalendarIterator().next().addEvent(event, user);
    	Database.addUser(user);
    	
    	// Username: Hans
    	// Password: hello
    	user = new User("Hans", "hello");
    	event=new Event("Hans's Event 1", now, now, true, user);
    	user.getCalendarIterator().next().addEvent(event, user);
    	event=new Event("Hans's Event 2", now, now, true, user);
    	user.getCalendarIterator().next().addEvent(event, user);
    	Database.addUser(user);
    	
    	// Username: Kurt
    	// Password: password
    	user = new User("Kurt", "password");
    	event=new Event("Kurt's Event 1", now, now, false, user);
    	Calendar cal1 = new Calendar("Kurt's Kalender", user);
    	user.addCalendar(cal1);
    	user.getCalendarIterator().next().addEvent(event, user);
    	event=new Event("Kurt's Event 2", now, now, true, user);
    	user.getCalendarIterator().next().addEvent(event, user);
    	Database.addUser(user);
    	
    }
    
}