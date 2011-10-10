package models;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
	
	private boolean isPublic = false;
	private String name;
	private Date start;
	private Date end;
	private User owner;
	private Integer ID;
	
	public Event(String name, Date start, Date end, boolean isPublic, User owner) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.isPublic = isPublic;
		this.owner = owner;
		this.ID = Database.getNextEventID();
	}
	
	public void setName(String name, User caller) {
		if(caller.equals(this.owner)) {
			this.name = name;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setStart(Date start, User caller) {
		if(caller.equals(this.owner)) {
			this.start = start;
		}
	}
	
	public Date getStart() {
		return this.start;
	}
	
	public void setEnd(Date end, User caller) {
		if(caller.equals(this.owner)) {
			this.end = end;
		}
	}
	
	public Date getEnd() {
		return this.end;
	}
	
	public void setIsPublic(boolean isPublic, User caller) {
		if(caller.equals(this.owner)) {
			this.isPublic = isPublic;
		}
	}
	
	public boolean getIsPublic() {
		return this.isPublic;
	}
	
	public String printDate(Date date) {
	    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date);
	}
	
	public String printTime(Date date) {
	    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		return dateFormat.format(date);
	}

	public void setID(int id) {
		this.ID = id;
	}
	
	public Integer getID() {
		return this.ID;
	}

}
