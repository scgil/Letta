package es.uvigo.esei.letta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

import es.uvigo.esei.letta.entities.Event;

/**
 * DAO class for the {@link Event} entities.
 * 
 * @author Sergio Castro Gil
 * @author Emma Álvarez Conde
 *
 */
public class EventsDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(EventsDAO.class.getName());

	/**
	 * Returns an event stored persisted in the system.
	 * 
	 * @param id identifier of an event.
	 * @return a event with the provided identifier.
	 * @throws DAOException             if an error happens while retrieving the
	 *                                  event.
	 * @throws IllegalArgumentException if the provided id does not corresponds with
	 *                                  any persisted event.
	 */
	public Event get(int id) throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM events WHERE id=?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);

				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id of the event");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting an event", e);
			throw new DAOException(e);
		}
	}
	/**
	 * Returns the number of future events stored on the database
	 * 
	 * @return the number of events on the future.
	 * @throws DAOException             if an error happens while retrieving the
	 *                                  event.
	 */
	
	public int getAll() throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT COUNT(*) FROM events WHERE eventDate >= CURDATE()";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					result.next();
					return result.getInt(1);
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error counting events", e);
			throw new DAOException(e);
		}
	}


	/**
	 * Persists a new person in the system. An identifier will be assigned
	 * automatically to the new Event.
	 * 
	 * @param title       title of the new event. Can't be {@code null}.
	 * @param description description of the new event. Can't be {@code null}.
	 * @param category    category of the new event. Can't be {@code null}.
	 * @param date        date of the new event. Can't be {@code null}.
	 * @param capacity    capacity of the event. Can't be {@code null}.
	 * @param place       place where event is celebrated. Can't be {@code null}.
	 * @param img         image of the event.
	 * @param idPartaker  Creator of an event. Can't be {@code null}.
	 * @return a {@link Event} entity representing the persisted Event.
	 * @throws DAOException             if an error happens while persisting the new
	 *                                  Event.
	 * @throws IllegalArgumentException if title, description, category, date or
	 *                                  capacity are {@code null}.
	 */
	public Event add(String title, String description, String category, Date date, Integer capacity, String place,
			String img, Integer idPartaker) throws DAOException, IllegalArgumentException {
		if (title == null || description == null || category == null || date == null || capacity == null
				|| place == null || idPartaker == null) {
			throw new IllegalArgumentException(
					"title, description, category, date, capacity, place and idPartaker can't be null");
		}

		if (capacity < 2 || capacity > 20) {
			throw new IllegalArgumentException("capacity must be between 2 and 20");
		}
		if (date.before(new Date())) {
			throw new IllegalArgumentException("date must be later than de current date");
		}

		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO events VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, title);
				statement.setString(2, description);
				statement.setString(3, category);
				statement.setTimestamp(4, new java.sql.Timestamp(date.getTime()));
				statement.setInt(5, capacity);
				statement.setString(6, place);
				statement.setInt(8, idPartaker);

				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Event(resultKeys.getInt(1), title, description, category, date, capacity, place,
									img, idPartaker);
						} else {
							LOG.log(Level.SEVERE, "Error retrieving inserted id");
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding an Event", e);
			throw new DAOException(e);
		}
	}

	/**
	 * Returns a list with all the events from a search persisted in the system.
	 * 
	 * @return a list with all the events from a search persisted in the system.
	 * @throws DAOException if an error happens while retrieving the events.
	 * 
	 */
	public List<Event> listSearch(String find, int pageNumber, int pageSize) throws DAOException, IllegalArgumentException {

		

		if (find.trim().length() < 2) {
			throw new IllegalArgumentException("Deben introducirse al menos dos caracteres en la busqueda");
		}
		
		if(pageNumber < 1 || pageSize < 1){
			throw new IllegalArgumentException("El número de página o el tamaño de página deben ser mayores que 0");
		}


		try (final Connection conn = this.getConnection()) {
			final String query1 = "SELECT * FROM events WHERE UPPER(title) LIKE UPPER(?) AND eventDate >= CURDATE() ORDER BY eventDate";
			final String query2 = "SELECT * FROM events WHERE UPPER(description) LIKE UPPER(?) AND eventDate >= CURDATE() ORDER BY eventDate";
			
			final List<Event> events = new LinkedList<>();

			final int start = (pageNumber-1)*pageSize;
			final List<Event> results = new LinkedList<>();

			try (final PreparedStatement statement = conn.prepareStatement(query1)) {
				statement.setString(1, "%" + find + "%");
			
				try (final ResultSet result = statement.executeQuery()) {
					while (result.next()) {
						events.add(rowToEntity(result));
					}
						int end=start+pageSize;

						if(end <= events.size()){
							
							for(int j=start; j < end; j++){

								results.add(events.get(j));
							}
							
						}else {

							for(int j=start; j < events.size();j++){
								results.add(events.get(j));
							}
						}
				}
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "Error listing events by title", e);
				throw new DAOException(e);
			}
			try (final PreparedStatement statement = conn.prepareStatement(query2)) {
				statement.setString(1, "%" + find + "%");

				try (final ResultSet result = statement.executeQuery()) {
					int i;
                    if(events.size()>start) {
                        i = events.size(); 
                    }else {
                        i = start;
                    }
					while (result.next()) {
						Event actual = rowToEntity(result);
						if (!events.contains(actual)) {
							events.add(actual);
						}
					}

					while(i < events.size() && results.size() < pageSize){
						results.add(events.get(i));
						i++;
					}	
					
				}
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "Error listing events by description", e);
				throw new DAOException(e);
			}
			return results;
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
		
	}

	/**
	 * Returns a list with the 5 in comming events persisted in the system.
	 * 
	 * @return a list with the 5 in comming events persisted in the system.
	 * @throws DAOException if an error happens while retrieving the events.
	 * 
	 */
	public List<Event> listEvents() throws DAOException {

		try (final Connection conn = this.getConnection()) {
			final String query1 = "SELECT * FROM events WHERE eventDate >= CURDATE() ORDER BY eventDate LIMIT 5";
			final List<Event> events = new LinkedList<>();
			try (final ResultSet result = conn.prepareStatement(query1).executeQuery()) {
				while (result.next()) {
					events.add(rowToEntity(result));
				}
				return events;
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "Error listing events", e);
				throw new DAOException(e);
			}

		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns a list with all the for coming events persisted in the system.
	 * 
	 * @return a list with all the events for coming events persisted in the system.
	 * @throws DAOException if an error happens while retrieving the events.
	 * 
	 */
	public List<Event> listGetAll(int pageNumber, int pageSize) throws DAOException, IllegalArgumentException {

		if(pageNumber < 1 || pageSize < 1){
			throw new IllegalArgumentException("El número de página o el tamaño de página deben ser mayores que 0");
		}
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM events WHERE eventDate >= CURDATE() ORDER BY eventDate";
			final List<Event> events = new LinkedList<>();
			final int start = (pageNumber-1)*pageSize;
			final List<Event> results = new LinkedList<>();
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					while (result.next()) {
						events.add(rowToEntity(result));
					}
					int end=start+pageSize;
					if(end <= events.size()){ 
						for(int j=start; j < end; j++){
							results.add(events.get(j));
						}
					}else {
						for(int j=start; j < events.size();j++){
							results.add(events.get(j));
						}
					}
					
					return results;		
				}
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "Error listing all events", e);
				throw new DAOException(e);
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing all events", e);
			throw new DAOException(e);
		}
	}

	/**
	 * Persists a new attendance to event.
	 * 
	 * @param idPartaker identifier of the Partaker. Can't be {@code null}.
	 * @param idEvent    identifier of an Event. Can't be {@code null}.
	 * @throws DAOException             if an error happens while persisting the new
	 *                                  attendance.
	 * @throws IllegalArgumentException if idPartaker and idEvent are {@code null}.
	 */

	public void joinEvent(Integer idPartaker, Integer idEvent) throws DAOException, IllegalArgumentException {
		if (idPartaker == null || idEvent == null) {
			throw new IllegalArgumentException("idPartaker and idEvent can't be null");
		}

		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO attendance VALUES(?, ?)";

			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setInt(1, idPartaker);
				statement.setInt(2, idEvent);

				if (statement.executeUpdate() != 1) {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding an attendant", e);
			throw new DAOException(e);
		}

	}
	
	/**
	 * Returns a the number of items at the events from a search persisted in the system.
	 * 
	 * @return a number of items at the events from a search persisted in the system.
	 * @throws DAOException if an error happens while retrieving the events.
	 * 
	 */
	public int sizeListSearch(String find) throws DAOException, IllegalArgumentException{
		if (find.trim().length() < 2) {
			throw new IllegalArgumentException("Deben introducirse al menos dos caracteres en la busqueda");
		}
		try (final Connection conn = this.getConnection()) {
			final String query1 = "SELECT * FROM events WHERE UPPER(title) LIKE UPPER(?) AND eventDate >= CURDATE() ORDER BY eventDate";
			final String query2 = "SELECT * FROM events WHERE UPPER(description) LIKE UPPER(?) AND eventDate >= CURDATE() ORDER BY eventDate";
			final List<Event> events = new LinkedList<>();
			try (final PreparedStatement statement = conn.prepareStatement(query1)) {
				statement.setString(1, "%" + find + "%");
				
				try (final ResultSet result = statement.executeQuery()) {
					while (result.next()) {
						events.add(rowToEntity(result));
					}
				}
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "Error listing events by title", e);
				throw new DAOException(e);
			}
			try (final PreparedStatement statement = conn.prepareStatement(query2)) {
				statement.setString(1, "%" + find + "%");
				
				try (final ResultSet result = statement.executeQuery()) {					
					while (result.next()) {
						Event actual = rowToEntity(result);
						if(!events.contains(actual)) {
							events.add(actual);
						}
					}
				}
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "Error listing events by description", e);
				throw new DAOException(e);
			}

			return events.size();

		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
	}

	private Event rowToEntity(ResultSet row) throws SQLException {
		return new Event(row.getInt("id"), row.getString("title"), row.getString("description"),
				row.getString("category"), row.getTimestamp("eventDate"), row.getInt("capacity"),
				row.getString("place"), row.getString("img"), row.getInt("idPartaker"));
	}
}