package es.uvigo.esei.letta.dataset;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;
import static java.util.Calendar.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Predicate;

import es.uvigo.esei.letta.entities.Event;

public final class EventsDataset {
	private EventsDataset() {
	}

	// Array with all events in the set up dataset
	public static Event[] events() {
		Date[] dates = {
			new GregorianCalendar(2022, JUNE, 4, 13, 45).getTime(), //1 
			new GregorianCalendar(2022, MAY, 31, 11, 30).getTime(), //2
			new GregorianCalendar(2022, JULY, 25, 11, 30).getTime(), //3
			new GregorianCalendar(2020, AUGUST, 20, 11, 30).getTime(), //4
			new GregorianCalendar(2022, MAY, 25, 11, 30).getTime(), //5
			new GregorianCalendar(2022, APRIL, 20, 11, 30).getTime(), //6
			new GregorianCalendar(2021, SEPTEMBER, 29, 11, 30).getTime(), //7
			new GregorianCalendar(2023, SEPTEMBER, 30, 11, 30).getTime(),//8
			new GregorianCalendar(2022, AUGUST, 29, 11, 30).getTime(), //9
			new GregorianCalendar(2022, JULY, 29, 11, 30).getTime(), //10
			new GregorianCalendar(2022, JUNE, 29, 11, 30).getTime(), //11
			new GregorianCalendar(2022, MAY, 29, 11, 30).getTime(), //12
			new GregorianCalendar(2022, APRIL, 29, 11, 30).getTime(), //13
			new GregorianCalendar(2022, MARCH, 29, 11, 30).getTime(), //14
			new GregorianCalendar(2022, FEBRUARY, 29, 11, 30).getTime(), //15
			
		};

		return new Event[] {
				new Event(1, "Una Pelicula", "Hablamos de una pelicula", "cine", dates[0], 4, "Buenos Aires",
						null,1),
				new Event(2, "Un Libro unico", "Hablamos de un libro pepe", "libros", dates[1], 10,
						"Casa campo Alberto", "../Images/ImagenEjemplo",1),
				new Event(3, "Un librito pepe", "Hablamos de un libro pequeño", "libros", dates[2], 3,
						"Piso de Juan", null,2),
				new Event(4, "Un Libro pasado", "Hablamos de un libro pero fue hace tiempo", "libros",
						dates[3], 10, "Casa campo Alberto",null,2),
				new Event(5, "Una Peli del futuro", "Hablamos de una peli que saldrá en el futuro", "cine",
						dates[4], 5, "Casa Paco",null,2),
				new Event(6, "Una Serie de ahora", "Hablamos de una serie de actualidad", "tv",
						dates[5], 6, "Bar Restaurante Maria Pita",null,2),
				new Event(7, "Una Felicidad en yoga", "Hacemos yoga en pareja", "ocio",
						dates[6], 2, "Parque de San Lazaro",null,2),
				new Event(8, "Serie de ficcion", "Hablamos de una serie de ficcion", "tv",
						dates[7], 4, "Casa campo Alberto",null,1),
				new Event(9, "Serie de accion", "Hablamos de una serie de accion", "tv",
						dates[8], 5, "Parque",null,1),
				new Event(10, "Serie de romance", "Hablamos de una serie de romance", "tv",
						dates[9], 8, "Calle",null,1),
				new Event(11, "Serie policial", "Hablamos de una serie policial", "tv",
						dates[10], 5, "Parque",null,2),
				new Event(12, "Serie detectivesca", "Hablamos de una serie detectivesca", "tv",
						dates[11], 4, "Esquina",null,2),
				new Event(13, "Ir a gimnasio", "Hablamos de ir a un gimnasio", "ocio",
						dates[12], 4, "Casa",null,2),
				new Event(14, "Ir a jugar a tenis", "Hablamos de ir a jugar un tenis", "ocio",
						dates[13], 20, "Plaza mayor",null,1),
				new Event(15, "Ir a jugar al futbol", "Hablamos de ir a jugar un futbol", "ocio",
						dates[14], 2, "Trastero",null,2),};
	}
	
	public static Event[] eventsWithIds(int ...ids) {
		Arrays.sort(ids);
		return Arrays.stream(events())
			.filter(event -> Arrays.binarySearch(ids, event.getId()) >= 0)
		.toArray(Event[]::new);
	}
	
	public static Event[] eventsWithNoSortedIds(int ...ids) {
		Event[] events = new Event[ids.length];
		int i=0;
		int j=0;
		boolean found = false;
		
		for(int id: ids) {
				i=0;
				found = false;
			while(i<events().length && found == false) {
				if(events()[i].getId() == id) {
					
					events[j]=(events()[i]);
					found = true;
					j++;
				}
				i++;
			}
		}		
		return events;
	}

	// Array with results for searching testing method
	public static Event[] searchUniqueEvent() {
		return eventsWithIds(1);
	}

	// Array with results for searching testing method
	public static Event[] searchSortedEvents() {
		return eventsWithNoSortedIds(2,3);
	}

	
	// Array with results for searching testing method
	public static Event[] searchPriorityEvents() {
		return eventsWithNoSortedIds(3,2);
	}

	
	//Array with the first page for searching testing pagination method
	public static Event[] searchFirstPageEvents() {
		return eventsWithNoSortedIds(7,6,5,2,1,3,15,14,13,12);
	}
	
	//Array with the first page for getAll testing pagination method
	public static Event[] getAllFirstPageEvents() {
		return eventsWithNoSortedIds(7,15,14,6,13,5,12,2,1,11);
	}
	
	//Array with the second page for searching testing pagination method
	public static Event[] searchSecondPageEvents() {
			return eventsWithNoSortedIds(11,10,9,8);
	}
	
	//Array with the second page for getAll testing pagination method	
	public static Event[] getAllSecondPageEvents() {
			return eventsWithNoSortedIds(5,12,2,1,11);
		}
		
	
	// Array with no results at all for testing purposes.
	public static Event[] nonResultEvents() {
		return new Event[]{};
	}

	public static Event[] eventsWithout(int... ids) {
		Arrays.sort(ids);

		final Predicate<Event> hasValidId = event -> binarySearch(ids, event.getId()) < 0;

		return stream(events()).filter(hasValidId).toArray(Event[]::new);
	}

	public static Event event(int id) {
		return stream(events()).filter(event -> event.getId() == id).findAny()
				.orElseThrow(IllegalArgumentException::new);
	}

	// String for search method testing
	public static String uniqueTitle() {
		return "Pelicula";
	}

	// String for search method testing
	public static String sortedTitle() {
		return "Libr";
	}

	// String for search method testing
	public static String priorityTitle() {
		return "PEPE";
	}

	//String for search method testing
	public static String paginationTitle() {
		return "UN";
	}
	
	// String for search method testing
	public static String nonResultTitle() {
		return "cacatua";
	}

	// String for search method testing
	public static String tooSmallTitle() {
		return "a";
	}

	// String for title in adding method testing
	public static String newTitle() {
		return "Titulo nuevo";
	}

	// String for description in adding method testing
	public static String newDescription() {
		return "Descripcion nueva";
	}

	// Valid string for category in adding method testing
	public static String existingCategory() {
		return "libros";
	}

	// Invalid string for category in adding method testing
	public static String nonExistingCategory() {
		return "No soy una categoria";
	}
	
	public static int numberOfEvents() {
		return 14;
	}

	// Valid String for date in adding method testing (Until 30th June 2022, change
	// the year and files in ../db/)
	public static String newValidDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, java.util.Calendar.JUNE, 30, 11, 00);
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(calendar.getTime());
	}

	// Valid String for date in adding method testing (Until 30th June 2022, change
	// the year and files in ../db/)
	public static String notHoursDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, java.util.Calendar.JUNE, 30, 11, 00);
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(calendar.getTime());
	}

	// Invalid String for date in adding method testing
	public static String newInvalidDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, java.util.Calendar.JUNE, 30, 11, 00);
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(calendar.getTime());
	}

	// Valid Date for date in adding method testing (Until 30th June 2022, change
	// the year and files in ../db/)
	public static Date newValidDateDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, java.util.Calendar.JUNE, 30, 11, 00);
		return calendar.getTime();
	}

	// Invalid Date for date in adding method testing
	public static Date newInvalidDateDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, java.util.Calendar.JUNE, 30, 11, 00);
		return calendar.getTime();
	}

	// Valid String for Capacity in adding method testing
	public static String newCapacity() {
		return "10";
	}

	// Invalid String for capacity in adding method testing
	public static String tooSmallCapacity() {
		return "1";
	}

	// Invalid String for capacity in adding method testing
	public static String tooMuchCapacity() {
		return "21";
	}

	// Valid int for Capacity in adding method testing
	public static int newCapacityInt() {
		return 10;
	}

	// Invalid int for capacity in adding method testing
	public static int tooSmallCapacityInt() {
		return 1;
	}

	// Invalid int for capacity in adding method testing
	public static int tooMuchCapacityInt() {
		return 21;
	}

	// String for Place in adding method testing
	public static String newPlace() {
		return "Lugar nuevo";
	}

	// String for image in adding method testing
	public static String newImg() {
		return "images/ImagenEjemplo";
	}
	
	// String for description in adding method testing
	public static String newIdPartaker() {
		return "3";
	}
	
	//
	public static Event[] listPriorityEvents(){
		
		return eventsWithNoSortedIds(7, 6, 5, 2, 1);
	}
	
	public static Event[] listOrderByDatesEvents(){
		
		return eventsWithNoSortedIds(7, 15, 14, 6, 13);
	}

	public static Event newEvent() {
		return new Event(5, newTitle(), newDescription(), existingCategory(), newValidDateDate(), newCapacityInt(),
				newPlace(), "",3);
	}

	public static Event newEventImg() {
		return new Event(5, newTitle(), newDescription(), existingCategory(), newValidDateDate(), newCapacityInt(),
				newPlace(), newImg(),3);
	}
	
	public static String existentId() {
		return "2";
	}
	
	
	public static String nonExistentId() {
		return "1584";
	}
	
	public static Event existentEvent() {
		return new Event(2, "Un Libro unico", "Hablamos de un libro pepe", "libros", new GregorianCalendar(2022, MAY, 31, 11, 30).getTime()
				, 10, "Casa campo Alberto", "../Images/ImagenEjemplo",1);
	}

}