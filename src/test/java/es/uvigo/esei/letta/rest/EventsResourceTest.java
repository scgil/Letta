package es.uvigo.esei.letta.rest;

import static es.uvigo.esei.letta.dataset.EventsDataset.nonResultEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.nonResultTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.priorityTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.searchPriorityEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.searchUniqueEvent;
import static es.uvigo.esei.letta.dataset.EventsDataset.searchSortedEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.uniqueTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.sortedTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.paginationTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.searchFirstPageEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.searchSecondPageEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.getAllFirstPageEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.getAllSecondPageEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.tooSmallTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.newTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.newDescription;
import static es.uvigo.esei.letta.dataset.EventsDataset.existingCategory;
import static es.uvigo.esei.letta.dataset.EventsDataset.nonExistingCategory;
import static es.uvigo.esei.letta.dataset.EventsDataset.newValidDate;
import static es.uvigo.esei.letta.dataset.EventsDataset.newInvalidDate;
import static es.uvigo.esei.letta.dataset.EventsDataset.newCapacity;
import static es.uvigo.esei.letta.dataset.EventsDataset.tooMuchCapacity;
import static es.uvigo.esei.letta.dataset.EventsDataset.tooSmallCapacity;
import static es.uvigo.esei.letta.dataset.EventsDataset.newPlace;
import static es.uvigo.esei.letta.dataset.EventsDataset.newImg;
import static es.uvigo.esei.letta.dataset.EventsDataset.newIdPartaker;
import static es.uvigo.esei.letta.dataset.EventsDataset.newEvent;
import static es.uvigo.esei.letta.dataset.EventsDataset.newEventImg;
import static es.uvigo.esei.letta.dataset.EventsDataset.numberOfEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.notHoursDate;
import static es.uvigo.esei.letta.dataset.EventsDataset.listPriorityEvents;
import static es.uvigo.esei.letta.dataset.EventsDataset.existentId;
import static es.uvigo.esei.letta.dataset.EventsDataset.nonExistentId;
import static es.uvigo.esei.letta.dataset.EventsDataset.existentEvent;
import static es.uvigo.esei.letta.dataset.EventsDataset.listOrderByDatesEvents;
import static es.uvigo.esei.letta.dataset.UsersDataset.normalLogin;
import static es.uvigo.esei.letta.dataset.UsersDataset.nonExistentLogin;
import static es.uvigo.esei.letta.dataset.UsersDataset.userToken;
import static javax.ws.rs.client.Entity.entity;
import static es.uvigo.esei.letta.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.letta.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.letta.matchers.HasHttpStatus.hasUnauthorized;
import static es.uvigo.esei.letta.matchers.IsEqualToEvent.containsEventsInOrder;
import static es.uvigo.esei.letta.matchers.IsEqualToEvent.containsEventsInAnyOrder;
import static es.uvigo.esei.letta.matchers.IsEqualToEvent.equalsToEvent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import es.uvigo.esei.letta.LettaTestApplication;
import es.uvigo.esei.letta.entities.Event;
import es.uvigo.esei.letta.listeners.ApplicationContextBinding;
import es.uvigo.esei.letta.listeners.ApplicationContextJndiBindingTestExecutionListener;
import es.uvigo.esei.letta.listeners.DbManagement;
import es.uvigo.esei.letta.listeners.DbManagementTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/mem-context.xml")
@TestExecutionListeners({ DbUnitTestExecutionListener.class, DbManagementTestExecutionListener.class,
		ApplicationContextJndiBindingTestExecutionListener.class })
@ApplicationContextBinding(jndiUrl = "java:/comp/env/jdbc/letta", type = DataSource.class)
@DbManagement(create = "classpath:db/hsqldb.sql", drop = "classpath:db/hsqldb-drop.sql")
@DatabaseSetup("/datasets/dataset.xml")
@ExpectedDatabase("/datasets/dataset.xml")
public class EventsResourceTest extends JerseyTest {
	@Override
	protected Application configure() {
		return new LettaTestApplication();
	}

	@Override
	protected void configureClient(ClientConfig config) {
		super.configureClient(config);

		// Enables JSON transformation in client
		config.register(JacksonJsonProvider.class);
		config.property("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
	}

	/*      
	 * Search Test 1: Does a search where we only get one event as result.      
	*/     
	@Test     
	public void testSearchOne() throws IOException {
		final Response response = target("events")
			.queryParam("titleContent", uniqueTitle())
			.queryParam("pageNumber", 1)
			.queryParam("pageSize", 10)
			.request().get();
		
		assertThat(response, hasOkStatus());          
		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {	});

		assertThat(events, containsEventsInOrder(searchUniqueEvent()));      
	}

	
	/*
	 * Search Test 2: Does a search where we get several results with any particular
	 * order of priority.
	 */
	@Test
	public void testSearchSorted() throws IOException {
		final Response response = target("events")
				.queryParam("titleContent", sortedTitle())
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 10)
			.request().get();

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});

		assertThat(events, containsEventsInOrder(searchSortedEvents()));
	}

	/*
	 * Search Test 3: Does a search where we get results with a priority order for
	 * the ones who has the search String in the title versus the ones that got it
	 * in description
	 */
	@Test
	public void testSearchPriority() throws IOException {
		final Response response = target("events")
				.queryParam("titleContent", priorityTitle())
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 10)
			.request().get();
		
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});

		assertThat(events, containsEventsInOrder(searchPriorityEvents()));

	}

	/*
	 * Search Test 4: Does a search where we dont get any result at all
	 */
	@Test
	public void testSearchNone() throws IOException {
		final Response response = target("events")
				.queryParam("titleContent", nonResultTitle())
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 10)
			.request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});

		assertThat(events, containsEventsInAnyOrder(nonResultEvents()));

	}
	
	/*
	 * Search Test 5: Page 1 (10). Return 10 events for the first page of a search
	 */
	@Test
	public void testSearchFirstPage() throws IOException {
		final Response response = target("events")
				.queryParam("titleContent", paginationTitle())
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 10)
			.request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});
		
		assertThat(events, containsEventsInOrder(searchFirstPageEvents()));

	}
	
	/*
	 * Search Test 6: Page 2 (4). Return 4 events for the second page of a search
	 */
	@Test
	public void testSearchSecondPage() throws IOException {
		final Response response = target("events")
				.queryParam("titleContent", paginationTitle())
				.queryParam("pageNumber", 2)
				.queryParam("pageSize", 10)
			.request().get();
		assertThat(response, hasOkStatus());
		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});

		assertThat(events, containsEventsInOrder(searchSecondPageEvents()));
	}
	
	/*
	 * Search Test 7: Doesn't search because the param is not valid, too small.
	 */
	@Test
	public void testSearchTextTooSmall() throws IOException {
		final Response response = target("events")
				.queryParam("titleContent", tooSmallTitle())
			.request().get();
		assertThat(response, hasBadRequestStatus());
	}

	
	/*
	 * 
	 * This section is from AddEvent testing.
	 * This task was delayed in the project so all this commented section is in standby.
	 */
	
	
	
	/*
	 * Adding Test 1: Adds new event successfully without image
	 */
	/*
	@Test
	@ExpectedDatabase("/datasets/datasetEventsAdd.xml")
	public void testAdd() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());
		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasOkStatus());
		final Event event = response.readEntity(Event.class);

		assertThat(event, is(equalsToEvent(newEvent())));
	}

	/*
	 * Adding Test 2: Adds new event successfully with image
	 */
	/*
	@Test
	@ExpectedDatabase("/datasets/datasetEventsAddImg.xml")
	public void testAddImg() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("img", newImg());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasOkStatus());
		final Event event = response.readEntity(Event.class);

		assertThat(event, is(equalsToEvent(newEventImg())));
	}

	/*
	 * Adding Test 3: Tries to add a new event missing title
	 */
	/*
	@Test
	public void testMissingTitle() throws IOException {
		final Form form = new Form();
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());
	}

	/*
	 * Adding Test 4: Tries to add a new event missing description
	 */
	/*
	@Test
	public void testMissingDescription() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());
	}

	/*
	 * Adding Test 5: Tries to add a new event missing category
	 */
	/*
	@Test
	public void testMissingCategory() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 6: Tries to add a new event missing date
	 */
	/*
	@Test
	public void testMissingDate() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 7: Tries to add a new event missing capacity
	 */
	/*
	@Test
	public void testMissingCapacity() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 8: Tries to add a new event missing place
	 */
	/*
	@Test
	public void testMissingPlace() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 9: Tries to add a new event with invalid category
	 */
	/*
	@Test
	public void testInvalidCategory() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", nonExistingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 10: Tries to add a new event with a date in the past
	 */
	/*
	@Test
	public void testPastDate() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newInvalidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 11: Tries to add a new event with a date without hours
	 */
	/*
	@Test
	public void testDateWithoutHours() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", notHoursDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 12: Tries to add a new event with capacity lower than allowed
	 */
	/*
	@Test
	public void testTooSmallCapacity() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", tooSmallCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 13: Tries to add a new event with capacity greater than allowed
	 */
	/*
	@Test
	public void testTooMuchCapacity() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", tooMuchCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 14: Tries to add a new event without idPartaker
	 */
	/*
	@Test
	public void testMissingIdPartaker() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("place", newPlace());
		form.param("capacity", newCapacity());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());

	}

	/*
	 * Adding Test 15: Tries to add a new event without being logged in the system
	 */
	/*
	@Test
	public void testAddUnauthorized() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasUnauthorized());
	}

	/*
	 * Adding Test 16: Tries to add a new event with a non existing user in the
	 * system
	 */
	/*
	@Test
	public void testAddNonExistentLogin() throws IOException {
		final Form form = new Form();
		form.param("title", newTitle());
		form.param("description", newDescription());
		form.param("category", existingCategory());
		form.param("date", newValidDate());
		form.param("capacity", newCapacity());
		form.param("place", newPlace());
		form.param("idPartaker", newIdPartaker());

		final Response response = target("events").request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic " + userToken(nonExistentLogin()))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasUnauthorized());
	}
	

	/*      
	 * GetAll Test 1: Does a getAll where we expect 10 events for the first page ordered by date requesting a pageSize of 10.   
	*/     	

	@Test     
	public void testGetAllOne() throws IOException {
		final Response response = target("events/getAll")
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 10)
			.request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});

		assertThat(events, containsEventsInOrder(getAllFirstPageEvents()));

	}
	/*      
	 * GetAll Test 2: Does a getAll where we expect 5 events for the second page ordered by date requesting a pageSize of 5.   
	*/ 
	@Test     
	public void testGetAllTwo() throws IOException {
		final Response response = target("events/getAll")
				.queryParam("pageNumber", 2)
				.queryParam("pageSize", 5)
			.request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});

		assertThat(events, containsEventsInOrder(getAllSecondPageEvents()));

	}
	

	/*
	 * Listing Test 1: List just events in order and does not show events from the past
	 */
	@Test
	public void testListFive() throws IOException {
		final Response response = target("events/recent").request().get();
		
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});
		assertThat(events, containsEventsInOrder(listOrderByDatesEvents()));

	}
	
	
	/*
	 * Listing Test 2: Does a list when we do not have any event
	 */
	@Test
	@DatabaseSetup("/datasets/datasetEmpty.xml")
	@ExpectedDatabase("/datasets/datasetEmpty.xml")
	public void testListNone() throws IOException {
		final Response response = target("events/recent").request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>() {
		});

		assertThat(events, containsEventsInAnyOrder(nonResultEvents()));

	}
	
	/*
	 * View event Test 1: Gets an event with the provided identifier
	 */
	@Test
	public void testViewOne() throws IOException {
		final Response response = target("events/"+existentId()).request().get();
		assertThat(response, hasOkStatus());

		final Event event = response.readEntity(new GenericType<Event>() {
		});

		assertThat(event, is(equalsToEvent(existentEvent())));

	}
	
	
	/*
	 * View event Test 2: Tries to get an event with the provided identifier
	 * ,but it doesn't belong to any event in the database
	 */
	@Test
	public void testNotView() throws IOException {
		final Response response = target("events/"+nonExistentId()).request().get();
		assertThat(response, hasBadRequestStatus());

	}

	
	
	/*
	 * Get number of events Test 1: Gets the number of events on the database
	 */
	@Test
	public void testGetAllNumberEvents() throws IOException {
		final Response response = target("events/getAllNumber").request().get();
		assertThat(response, hasOkStatus());

		final int number =  response.readEntity(new GenericType<Integer>() {
		}).intValue();
		assertTrue(number == numberOfEvents());

	}
	
	/*
	 * Get number of events Test 2: Gets the number of events on a empty database
	 */
	@Test
	@DatabaseSetup("/datasets/datasetEmpty.xml")
	@ExpectedDatabase("/datasets/datasetEmpty.xml")
	public void tesGetAllNumberEventsEmptyDatabase() throws IOException {
		final Response response = target("events/getAllNumber").request().get();
		assertThat(response, hasOkStatus());

		final int number =  response.readEntity(new GenericType<Integer>() {
		}).intValue();
		assertTrue(number == 0);

	}


}