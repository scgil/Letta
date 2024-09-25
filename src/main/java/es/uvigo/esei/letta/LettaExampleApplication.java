package es.uvigo.esei.letta;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import es.uvigo.esei.letta.rest.EventsResource;


/**
 * Configuration of the REST application. This class includes the resources and
 * configuration parameter used in the REST API of the application.
 * 
 *
 */
@ApplicationPath("/rest/*")
public class LettaExampleApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(EventsResource.class);
		return classes;
	}
	
	@Override
	public Map<String, Object> getProperties() {
		// Activates JSON automatic conversion in JAX-RS
		return Collections.singletonMap(
			"com.sun.jersey.api.json.POJOMappingFeature", true
		);
	}
}