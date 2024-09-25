package es.uvigo.esei.letta.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test; 

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class UserUnitTest {
	
	@Test
	public void testUserStringString() {
		final String login = "userTest";
		final String password = "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b";
	
		final User user = new User(login, password);
		
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo(password)));
	}

	@Test(expected = NullPointerException.class)
	public void testUserStringNullLogin() {
		new User(null, "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b");
	}
	
	@Test(expected = NullPointerException.class)
	public void testUserStringNullPassword() {
		new User("userTest", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserStringIllegalPassword() {
		new User("userTest", "Emma no va a funcionar");
	}

	@Test
	public void testSetLogin() {
		
		final String login = "userTest";
		final String newLogin = "Nuevo login";
		final String password = "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b";
	
		final User user = new User(login, password);
		
		user.setLogin(newLogin);
		
		assertThat(user.getLogin(), is(equalTo(newLogin)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullLogin() {
		final User user = new User("userTest", "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b");
		
		user.setLogin(null);
	}

	@Test
	public void testSetPassword() {
		final String login = "userTest";
		final String password = "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b";
		final String newPassword = "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7c";
	
		final User user = new User(login, password);
		
		user.setPassword(newPassword);
		
		assertThat(user.getPassword(), is(equalTo(newPassword)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullPassword() {
		final User user = new User("userTest", "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b");
		
		user.setPassword(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalPassword() {
		final User user = new User("userTest", "ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b");
		
		user.setPassword("Password incorrecta");
	}
	
}