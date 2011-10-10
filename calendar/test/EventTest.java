import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

import models.*;


public class EventTest extends UnitTest {
	
	private Event event;
	private User caller;
	
	@Before
	public void initialize() throws Exception {
		this.caller = new User("User1", "password1");
		this.event = new Event("NewEvent", new Date(1234), new Date(12345), false, caller);
	}
	
	@Test
	public void testGetName() {
		assertEquals("NewEvent", event.getName());
	}

	@Test
	public void testSetName() {
		event.setName("New Name", caller);
		assertEquals("New Name", event.getName());
	}
	
	@Test
	public void testGetStart() {
		assertEquals(new Date(1234), event.getStart());
	}
	
	@Test
	public void testSetStart() {
		Date date = new Date(123123);
		event.setStart(date, caller);
		assertEquals(date, event.getStart());
	}
	
	@Test
	public void testGetEnd() {
		assertEquals(new Date(12345), event.getEnd());
	}

	@Test
	public void testSetEnd() {
		Date date = new Date(12321);
		event.setEnd(date, caller);
		assertEquals(date, event.getEnd());
	}
	
	@Test
	public void testGetIsPublic() {
		assertFalse(event.getIsPublic());
	}

	@Test
	public void testSetIsPublic() {
		event.setIsPublic(true, caller);
		assertTrue(event.getIsPublic());
	}

	@Test
	public void testPrintDate() {
		Date date = new Date((long)123456789 * 1000);
		assertEquals("11/29/1973", event.printDate(date));
	}

}
