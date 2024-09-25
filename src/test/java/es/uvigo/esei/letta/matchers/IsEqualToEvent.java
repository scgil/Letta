package es.uvigo.esei.letta.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import es.uvigo.esei.letta.entities.Event;

public class IsEqualToEvent extends IsEqualToEntity<Event> {
	public IsEqualToEvent(Event entity) {
		super(entity);
	}

	@Override
	protected boolean matchesSafely(Event actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("id", Event::getId, actual)
				&& checkAttribute("title", Event::getTitle, actual)
				&& checkAttribute("description", Event::getDescription, actual)
				&& checkAttribute("category", Event::getCategory, actual)
				&& checkAttribute("eventDate", Event::getDate, actual)
				&& checkAttribute("capacity", Event::getCapacity, actual)
				&& checkAttribute("place", Event::getPlace, actual)
				&& checkAttribute("img", Event::getImg, actual)
				&& checkAttribute("idPartaker", Event::getIdPartaker, actual);
		}
	}

	/**
	 * Factory method that creates a new {@link IsEqualToEntity} matcher with
	 * the provided {@link Event} as the expected value.
	 * 
	 * @param event the expected event.
	 * @return a new {@link IsEqualToEntity} matcher with the provided
	 * {@link Event} as the expected value.
	 */
	@Factory
	public static IsEqualToEvent equalsToEvent(Event event) {
		return new IsEqualToEvent(event);
	}
	
	/**
	 * Factory method that returns a new {@link Matcher} that includes several
	 * {@link IsEqualToEvent} matchers, each one using an {@link Event} of the
	 * provided ones as the expected value.
	 * 
	 * @param events the events to be used as the expected values.
	 * @return a new {@link Matcher} that includes several
	 * {@link IsEqualToEvent} matchers, each one using an {@link Event} of the
	 * provided ones as the expected value.
	 * @see IsEqualToEntity#containsEntityInAnyOrder(java.util.function.Function, Object...)
	 */
	@Factory
	public static Matcher<Iterable<? extends Event>> containsEventsInAnyOrder(Event ... events) {
		return containsEntityInAnyOrder(IsEqualToEvent::equalsToEvent, events);
	}

	@Factory
	public static Matcher<Iterable<? extends Event>> containsEventsInOrder(Event ... events) {
		return containsEntityInOrder(IsEqualToEvent::equalsToEvent, events);
	}

}
