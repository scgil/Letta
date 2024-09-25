package es.uvigo.esei.letta.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.letta.dao.DAOException;
import es.uvigo.esei.letta.dao.PartakersDAO;
import es.uvigo.esei.letta.entities.Partaker;

/**
 * REST resource for managing partakers.
 * 
 * @author Emma Álvarez Conde
 * @author Alberto Mateo Sabucedo González
 */
@Path("/partakers")
@Produces(MediaType.APPLICATION_JSON)
public class PartakersResource {
private final static Logger LOG = Logger.getLogger(PartakersResource.class.getName());
	
	private final PartakersDAO dao;
	
	/**
	 * Constructs a new instance of {@link PartakersResource}.
	 */
	public PartakersResource() {
		this(new PartakersDAO());
	}
	
	// Needed for testing purposes
	PartakersResource(PartakersDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Returns a partaker with the provided identifier.
	 * 
	 * @param id the identifier of the partaker to retrieve.
	 * @return a 200 OK response with a partaker that has the provided identifier.
	 * If the identifier does not corresponds with any partaker, a 400 Bad Request
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
			final Partaker partaker = this.dao.get(id);
			
			return Response.ok(partaker).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid partaker id in get method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a partaker", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}
}
