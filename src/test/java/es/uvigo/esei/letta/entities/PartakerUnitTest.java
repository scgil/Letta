package es.uvigo.esei.letta.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test; 

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PartakerUnitTest {
	@Test
	public void testPartakerIntStringStringInt() {
		final int id = 1;
		final String name = "Juan";
		final String surname = "Perez Rodriguez";
		final int userId =1;
		
		final Partaker partaker = new Partaker(id,name, surname, userId);
		
		assertThat(partaker.getId(), is(equalTo(id)));
		assertThat(partaker.getName(), is(equalTo(name)));
		assertThat(partaker.getSurname(), is(equalTo(surname)));
		assertThat(partaker.getUserId(), is(equalTo(userId)));
		
	}

	@Test(expected = NullPointerException.class)
	public void testPartakerIntStringStringIntNullName() {
		new Partaker(1,null, "Perez Rodriguez", 1);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testPartakerIntStringStringIntNullSurname() {
		new Partaker(1,"Juan", null, 1);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testPartakerIntStringStringIntNullUserId() {
		new Partaker(1,"Juan", "Perez Rodriguez", 0);
				
	}
	
	@Test
	public void testSetName() {
		final int id = 1;
		final String name = "Juan";
		final String surname = "Perez Rodriguez";
		final int userId =1;
		
		final Partaker partaker = new Partaker(id,name, surname, userId);
		partaker.setName("Jose");
		
		assertThat(partaker.getId(), is(equalTo(id)));
		assertThat(partaker.getName(), is(equalTo("Jose")));
		assertThat(partaker.getSurname(), is(equalTo(surname)));
		assertThat(partaker.getUserId(), is(equalTo(userId)));
		
	}
	
	@Test
	public void testSetSurname() {
		final int id = 1;
		final String name = "Juan";
		final String surname = "Perez Rodriguez";
		final int userId =1;
		
		final Partaker partaker = new Partaker(id,name, surname, userId);
		partaker.setSurname("Martinez Rodriguez");
		
		assertThat(partaker.getId(), is(equalTo(id)));
		assertThat(partaker.getName(), is(equalTo(name)));
		assertThat(partaker.getSurname(), is(equalTo("Martinez Rodriguez")));
		assertThat(partaker.getUserId(), is(equalTo(userId)));
		
	}
	
	@Test
	public void testSetUserId() {
		final int id = 1;
		final String name = "Juan";
		final String surname = "Perez Rodriguez";
		final int userId =1;
		
		final Partaker partaker = new Partaker(id,name, surname, userId);
		partaker.setUserId(2);
		
		assertThat(partaker.getId(), is(equalTo(id)));
		assertThat(partaker.getName(), is(equalTo(name)));
		assertThat(partaker.getSurname(), is(equalTo(surname)));
		assertThat(partaker.getUserId(), is(equalTo(2)));
		
	}

		

	@Test
	public void testEqualsObject() {
		final Partaker partakerA = new Partaker(1, "Juan", "Perez Rodriguez", 2);
		final Partaker partakerB = new Partaker(1, "Jose", "Martinez Fernandez", 1);
		
		assertTrue(partakerA.equals(partakerB));
	}

	@Test
	public void testEqualsHashcode() {
		EqualsVerifier.forClass(Partaker.class)
			.withIgnoredFields("name", "surname", "userId")
			.suppress(Warning.STRICT_INHERITANCE)
			.suppress(Warning.NONFINAL_FIELDS)
		.verify();
	}
}
