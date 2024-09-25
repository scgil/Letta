package es.uvigo.esei.letta;

import static java.util.Collections.unmodifiableSet;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest/*")
public class LettaTestApplication extends LettaExampleApplication {
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<>(super.getClasses());
		
		return unmodifiableSet(classes);
	}
}
