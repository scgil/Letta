package es.uvigo.esei.letta.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.letta.dao.DAOException;
import es.uvigo.esei.letta.dao.EventsDAO;
import es.uvigo.esei.letta.entities.Event;

/**
 * REST resource for managing events.
 * 
 * @author Emma Álvarez Conde
 * @author Noa López Marchante
 */
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {
private final static Logger LOG = Logger.getLogger(EventsResource.class.getName());
	
	private final EventsDAO dao;
	
	/**
	 * Constructs a new instance of {@link EventsResource}.
	 */
	public EventsResource() {
		this(new EventsDAO());
	}
	
	// Needed for testing purposes
	EventsResource(EventsDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Returns a event with the provided identifier.
	 * 
	 * @param id the identifier of the event to retrieve.
	 * @return a 200 OK response with a event that has the provided identifier.
	 * If the identifier does not corresponds with any event, a 400 Bad Request
	 * response with an error message will be returned. If an error happens
	 * while retrieving the list, a 500 Internal Server Error response with an
	 * error message will be returned.
	 */
	@GET
	@Path("/{id}")
	public Response get(
		@PathParam("id") int id
	) {
		try {
			final Event event = this.dao.get(id);
			
			return Response.ok(event).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid event id in get method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a event", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}

	/**
	 * Returns the searched list of future events stored in the system.
	 * 
	 * @return a 200 OK response with the searched list of events stored in the
	 * system. If an error happens while retrieving the list, a 500 Internal
	 * Server Error response with an error message will be returned.
	 */
	@GET
	public Response listSearch(@QueryParam("titleContent") String titleContent,
							@QueryParam("pageNumber") int pageNumber,
							@QueryParam("pageSize") int pageSize) {
		try {
			return Response.ok(this.dao.listSearch(titleContent, pageNumber, pageSize)).build();
		}catch (IllegalArgumentException e) {             
			LOG.log(Level.FINE, "Invalid title or description content in search method", e);                          
			return Response.status(Response.Status.BAD_REQUEST)                 
					.entity(e.getMessage())             
					.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}


	
	/**
	 * Returns the searched list of five future events stored in the system.
	 * 
	 * @return a 200 OK response with the searched list of events stored in the
	 * system. If an error happens while retrieving the list, a 500 Internal
	 * Server Error response with an error message will be returned.
	 */
	@GET
	@Path("recent")
	public Response listEvents() {
		try {
			final List<Event> events = this.dao.listEvents();
			
			return Response.ok(events).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a list of events", e);
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}
	
	/**
	 * Returns the list of all events stored in the system page by the number of the pageSize starting for the pageNumber.
	 * 
	 * @return a 200 OK response with the events list stored in the
	 * system. If an error happens while retrieving the list, a 500 Internal
	 * Server Error response with an error message will be returned.
	 */
	@GET
	@Path("getAll")
	public Response listGetAll(@QueryParam("pageNumber") int pageNumber,
							@QueryParam("pageSize") int pageSize) {
		try {
			return Response.ok(this.dao.listGetAll(pageNumber, pageSize)).build();
		}catch (IllegalArgumentException e) {             
			LOG.log(Level.FINE, "Invalid page size or page number in get all events method", e);                          
			return Response.status(Response.Status.BAD_REQUEST)                 
					.entity(e.getMessage())             
					.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting all events", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	
	/**
	 * Returns the number of future events stored on the database.
	 * 
	 * @return a 200 OK response with the number of events in the future.
	 * If an error happens while retrieving the number, a 500 Internal Server 
	 * Error response with an error message will be returned.
	 */
	@GET
	@Path("getAllNumber")
	public Response getAllNumber() {
		try {
			final int number = this.dao.getAll();
			
			return Response.ok(number).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting the number of events", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}
	
	/**
	 * Returns the number of items at the list of events stored in the system.
	 * 
	 * @return a 200 OK response with the searched number of items at the list of events stored in the
	 * system. If an error happens while retrieving the list, a 500 Internal
	 * Server Error response with an error message will be returned.
	 */
	@GET
	@Path("/size")
	public Response sizeListSearch(@QueryParam("titleContent") String titleContent) {
		try {
			return Response.ok(this.dao.sizeListSearch(titleContent)).build();
		}catch (IllegalArgumentException e) {             
			LOG.log(Level.FINE, "Invalid title or description content in search method", e);                          
			return Response.status(Response.Status.BAD_REQUEST)                 
					.entity(e.getMessage())             
					.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
