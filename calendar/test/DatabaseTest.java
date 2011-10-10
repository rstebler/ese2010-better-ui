import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Calendar;
import models.Database;
import models.Event;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class DatabaseTest extends UnitTest  {
	
	private User user;

	@Before
	public void initialize() throws Exception {
		this.user = new User("user10", "password1");
		Database.users.put("user10", user);
	}

	@Test
	public void testAddUserUser() {
		User user = new User("user1", "password1");
		Integer i = Database.users.size();
		Database.addUser(user);
		assertTrue(Database.users.containsKey(user.getName()));
		assertNotNull(Database.users.get(user.getName()));
		assertEquals(user, Database.users.get(user.getName()));
		assertTrue(Database.users.size() == i+1);
		Database.users.remove("user1");
	}

	@Test
	public void testAddUserStringString() {
		Integer i = Database.users.size();
		Database.addUser("user2","password2");
		assertTrue(Database.users.containsKey("user2"));
		assertNotNull(Database.users.get("user2"));
		assertEquals("user2", Database.users.get("user2").getName());
		assertTrue(Database.users.size() == i+1);
		Database.users.remove("user2");
	}

	@Test
	public void testDeleteUser() {
		User user = new User("user3", "password3");
		Integer i = Database.users.size();
		Database.addUser(user);
		assertTrue(Database.users.containsKey("user3"));
		assertTrue(Database.users.size() == i+1);
		Database.deleteUser("user3", "password3");
		assertFalse(Database.users.containsKey("user3"));
		assertTrue(Database.users.size() == i);
	}

	@Test
	public void testChangePassword() {
		Database.changePassword(user.getName(), user.getPassword(), "new password");
		assertEquals("new password", user.getPassword());
	}

	@Test
	public void testGetUserList() {
    	List<User> userList = Database.getUserList();
    	assertNotNull(userList);
    	assertEquals(userList.size(), Database.users.size());
    	assertTrue(userList.contains(user));
	}
	
	@Test
	public void testgetNextCalendarID() {
		int i = Database.getNextCalendarID();
		int j = Database.getNextCalendarID();
		assertTrue(i >= 0);
		assertTrue(j == i+1);
	}
	
	@Test
	public void testgetNextEventID() {
		int i = Database.getNextEventID();
		int j = Database.getNextEventID();
		assertTrue(i >= 0);
		assertTrue(j == i+1);
	}
	
	@After
	public void cleanup() throws Exception {
		Database.users.remove(this.user.getName());
	}

}
