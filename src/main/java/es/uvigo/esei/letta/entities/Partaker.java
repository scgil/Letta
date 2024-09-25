package es.uvigo.esei.letta.entities;

import static java.util.Objects.requireNonNull;


/**
 * An entity that represents a partaker.
 * 
 * @author Sergio Castro Gil
 * @author Emma Álvarez Conde
 */
public class Partaker {
	private int id;
	private String name;
	private String surname;
	private int userId;
	
	
	// Constructor needed for the JSON conversion
	Partaker() {}
	
	/**
	 * Constructs a new instance of {@link Partaker}.
	 *
	 * @param id identifier of the partaker.
	 * @param name name of the partaker.
	 * @param surname surname of the partaker.
	 * @param userId user identifier of the partaker.

	 */
	public Partaker(int id, String name, String surname, int userId) {
		this.id = id;
		this.setName(name);
		this.setSurname(surname);
		this.setUserId(userId);
	}
	
	/**
	 * Returns the identifier of the partaker.
	 * 
	 * @return the identifier of the partaker.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the name of the partaker.
	 * 
	 * @return the name of the partaker.
	 */
	public String getName() {
		return name;
	}

    /**
	 * Returns the surname of the partaker.
	 * 
	 * @return the surname of the partaker.
	 */
	public String getSurname() {
		return surname;
	}

    /**
	 * Returns the user identifier of the partaker.
	 * 
	 * @return the userId of the partaker.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Set the name of this partaker.
	 * 
	 * @param name the new name of the partaker.
	 * @throws NullPointerException if the {@code name} is {@code null}.
	 */
	public void setName(String name) {
		this.name = requireNonNull(name, "Name can't be null");
	}

    /**
	 * Set the surname of this partaker.
	 * 
	 * @param surname the new surname of the partaker.
	 * @throws NullPointerException if the {@code surname} is {@code null}.
	 */
	public void setSurname(String surname) {
		this.surname = requireNonNull(surname, "Surname can't be null");
	}

    /**
	 * Set the user identifier of this partaker.
	 * 
	 * @param userId the new user id of the partaker.
	 * @throws NullPointerException if the {@code userId} is {@code null}.
	 */
	public void setUserId(int userId) {
		if(userId > 0) {
			this.userId = userId;
		}else{
			throw new NullPointerException("userId can't be null");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Partaker))
			return false;
		Partaker other = (Partaker) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
