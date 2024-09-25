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

public class EventUnitTest {
	@Test
	public void testEventIntStringStringStringDateIntString() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final int id = 1;
		final String title = "Libros de Harry Potter";
		final String description = "Hablamos sobre Harry Potter";
		final String category = Event.categories.LIBROS.toString();
		final Date date = calendar.getTime();
		final int capacity = 12;
		final String place = "Casa campo Alberto";
		final String img ="";
		final int idPartaker =1;
		
		final Event event = new Event(id, title, description, category, date, capacity, place, img, idPartaker);
		
		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getCapacity(), is(equalTo(capacity)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getImg(), is(equalTo(img)));
		assertThat(event.getIdPartaker(), is(equalTo(idPartaker)));
	}

	@Test(expected = NullPointerException.class)
	public void testEventIntStringStringStringDateIntStringStringIntNullTitle() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		new Event(1, null, "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(),calendar.getTime(),12,"Casa campo Alberto","",1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventIntStringStringStringDateIntStringStringIntNullDescription() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		new Event(1, "Libros de Harry Potter", null, Event.categories.LIBROS.toString(),calendar.getTime(),12,"Casa campo Alberto","",1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventIntStringStringStringDateIntStringStringIntNullCategory() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", null,calendar.getTime(),12,"Casa campo Alberto","",1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEventIntStringStringStringDateIntStringStringIntIllegalCategory() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", "Otros",calendar.getTime(),12,"Casa campo Alberto","",1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEventIntStringStringStringDateIntStringStringIntNotEnoughtCapacity() {
	    Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
        new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 1, "Casa campo Alberto", "",1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEventIntStringStringStringDateIntStringStringIntTooMuchCapacity() {
	    Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
        new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 21,"Casa campo Alberto", "",1);
	}
	
	/*@Test(expected = IllegalArgumentException.class)
	public void testEventIntStringStringStringDateIntStringStringDateOnThePast() {
	    Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)-1, 05, 12, 12, 30);
        new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12, "Casa campo Alberto","");
	}*/
	
	@Test(expected = NullPointerException.class)
	public void testEventIntStringStringStringDateIntStringStringIntNullDate() {
        new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), null, 12, "Casa campo Alberto","",1);
	}

	@Test(expected = NullPointerException.class)
	public void testEventIntStringStringStringDateIntStringStringIntNullPlace() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(),calendar.getTime(),12,null,"",1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventIntStringStringStringDateIntStringStringIntNullIdPartaker() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(),calendar.getTime(),12,"Casa campo Alberto","",0);
	}


	@Test
	public void testSetTitle() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final int id = 1;
		final String description = "Hablamos sobre Harry Potter";
		final String category = Event.categories.LIBROS.toString();
		final Date date = calendar.getTime();
		final int capacity = 12;
		final String place = "Casa campo Alberto";
		final String img ="";
		final int idPartaker =1;
		
		final Event event = new Event(id, "Libros de Harry Potter", description, category, date, capacity, place, img,idPartaker);
		event.setTitle("Libros de Stephen King");
		
		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo("Libros de Stephen King")));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getCapacity(), is(equalTo(capacity)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getImg(), is(equalTo(img)));
		assertThat(event.getIdPartaker(), is(equalTo(idPartaker)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullTitle() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12,"Casa campo Alberto", "",1);
		
		event.setTitle(null);
	}

	@Test
	public void testSetDescription() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final int id = 1;
		final String title = "Libros de Harry Potter";
		final String category = Event.categories.LIBROS.toString();
		final Date date = calendar.getTime();
		final int capacity = 12;
		final String place = "Casa campo Alberto";
		final String img ="";
		final int idPartaker =1;
		
		
		final Event event = new Event(id, title, "Hablamos sobre Harry Potter", category, date, capacity, place, img,idPartaker);
		event.setDescription("Hablamos sobre Stephen King");
		
		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo("Hablamos sobre Stephen King")));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getCapacity(), is(equalTo(capacity)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getImg(), is(equalTo(img)));
		assertThat(event.getIdPartaker(), is(equalTo(idPartaker)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullDescription() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12,"Casa campo Alberto", "",1);
		
		event.setDescription(null);
	}
	
	@Test
	public void testSetCategory() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final int id = 1;
		final String title = "Libros de Harry Potter";
		final String description = "Hablamos sobre Harry Potter";
		final Date date = calendar.getTime();
		final int capacity = 12;
		final String place = "Casa campo Alberto";
		final String img ="";
		final int idPartaker= 1;
		
		final Event event = new Event(id, title, description, Event.categories.LIBROS.toString(), date, capacity, place, img, idPartaker);
		event.setCategory(Event.categories.CINE.toString());
		
		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(Event.categories.CINE.toString())));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getCapacity(), is(equalTo(capacity)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getImg(), is(equalTo(img)));
		assertThat(event.getIdPartaker(), is(equalTo(idPartaker)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullCategory() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12,"Casa campo Alberto", "",1);
		
		event.setCategory(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalCategory() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12, "Casa campo Alberto", "" , 1);
		event.setCategory("Otro");
	}
	
	public void testSetCapacity() {
        Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
        final int id = 1;
        final String title = "Libros de Harry Potter";
        final String description = "Hablamos sobre Harry Potter";
        final String category = Event.categories.LIBROS.toString();
        final Date date = calendar.getTime();
        final int capacity = 12;
        final String place = "Casa campo Alberto";
        final String img ="";
        final int idPartaker = 1;

        final Event event = new Event(id, title, description, category, date, capacity, place, img , idPartaker);
        event.setCapacity(15);
        assertThat(event.getId(), is(equalTo(id)));
        assertThat(event.getTitle(), is(equalTo(title)));
        assertThat(event.getDescription(), is(equalTo(description)));
        assertThat(event.getCategory(), is(equalTo(category)));
        assertThat(event.getDate(), is(equalTo(date)));
        assertThat(event.getCapacity(), is(equalTo(15)));
        assertThat(event.getPlace(), is(equalTo(place)));
        assertThat(event.getImg(), is(equalTo(img)));
		assertThat(event.getIdPartaker(), is(equalTo(idPartaker)));
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetTooMuchCapacity() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
        final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12, "Casa campo Alberto", "",1);
        event.setCapacity(21);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetNotEnoughtCapacity() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12,"Casa campo Alberto", "",1);
        event.setCapacity(1);
	}
	
	public void testSetDate() {
        Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
        final int id = 1;
        final String title = "Libros de Harry Potter";
        final String description = "Hablamos sobre Harry Potter";
        final String category = Event.categories.LIBROS.toString();
        final Date date = calendar.getTime();
        final int capacity = 12;
        final String place = "Casa campo Alberto";
        final String img ="";
        final int idPartaker=1;
        
        final Event event = new Event(id, title, description, category, date, capacity, place, img, idPartaker);
		calendar.set(2021, 06, 12, 12, 30);
        event.setDate(calendar.getTime());
        assertThat(event.getId(), is(equalTo(id)));
        assertThat(event.getTitle(), is(equalTo(title)));
        assertThat(event.getDescription(), is(equalTo(description)));
        assertThat(event.getCategory(), is(equalTo(category)));
        assertThat(event.getDate(), is(equalTo(calendar.getTime())));
        assertThat(event.getCapacity(), is(equalTo(capacity)));
        assertThat(event.getPlace(), is(equalTo(place)));
        assertThat(event.getImg(), is(equalTo(img)));
		assertThat(event.getIdPartaker(), is(equalTo(idPartaker)));
    }

	/*@Test(expected = IllegalArgumentException.class)
	public void testSetDateOnThePast() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12,"Casa campo Alberto", "",1);
		calendar.set(Calendar.getInstance().get(Calendar.YEAR)-1, 05, 12, 12, 30);
		event.setDate(calendar.getTime());
	}*/
	
	@Test(expected = NullPointerException.class)
	public void testSetNullDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12,"Casa campo Alberto", "",1);
		event.setDate(null);
	}
	
	@Test
	public void testSetPlace() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final int id = 1;
		final String title = "Libros de Harry Potter";
		final String description = "Hablamos sobre Harry Potter";
		final String category = Event.categories.LIBROS.toString();
		final Date date = calendar.getTime();
		final int capacity = 12;
		final String img ="";
		final int idPartaker=1;
		
		final Event event = new Event(id, title, description, category, date, capacity, "Casa campo Alberto", img, idPartaker);
		event.setPlace("Casa campo Ivan");
		
		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getCapacity(), is(equalTo(capacity)));
		assertThat(event.getPlace(), is(equalTo("Casa campo Ivan")));
		assertThat(event.getImg(), is(equalTo(img)));
		assertThat(event.getIdPartaker(), is(equalTo(idPartaker)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullPlace() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event event = new Event(1, "Libros de Harry Potter", "Hablamos sobre Harry Potter", Event.categories.LIBROS.toString(), calendar.getTime(), 12,"Casa campo Alberto", "",1);
		
		event.setPlace(null);
	}
	

	@Test
	public void testEqualsObject() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR)+1, 05, 12, 12, 30);
		final Event eventA = new Event(1, "Title A", "Description A", Event.categories.LIBROS.toString(), calendar.getTime(), 2, "Casa campo Alberto", "" ,2);
		final Event eventB = new Event(1, "Title B", "Description B", Event.categories.CINE.toString(),calendar.getTime(), 3, "Casa campo Alberto", "" ,1);
		
		assertTrue(eventA.equals(eventB));
	}

	@Test
	public void testEqualsHashcode() {
		EqualsVerifier.forClass(Event.class)
			.withIgnoredFields("title", "description", "category", "date", "capacity", "place","img","idPartaker")
			.suppress(Warning.STRICT_INHERITANCE)
			.suppress(Warning.NONFINAL_FIELDS)
		.verify();
	}
}



