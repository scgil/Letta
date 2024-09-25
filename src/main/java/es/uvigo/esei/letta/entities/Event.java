package es.uvigo.esei.letta.entities;

import static java.util.Objects.requireNonNull;
import java.util.Date;

/**
 * An entity that represents a event.
 * 
 * @author Ivan Blanco Alvarez
 * @author Noa Lopez Marchante
 */
public class Event {
	private int id;
	private String title;
	private String description;
	public static enum categories {DEPORTES, CINE, TEATRO, TV, SERIES, LIBROS, OCIO};
	private String category;
	private Date date;
	private int capacity;
	private String place;
	private String img;
	private int idPartaker;
	
	
	// Constructor needed for the JSON conversion
	Event() {}
	
	/**
	 * Constructs a new instance of {@link Event}.
	 *
	 * @param id identifier of the event.
	 * @param title title of the event.
	 * @param description description of the event.
	 * @param category category of the event.
	 * @param date date of the event.
	 * @param capacity capacity of the event.
	 * @param place place of the event.
	 * @param img image of the event.
	 */
	public Event(int id, String title, String description, String category, Date date, int capacity, String place, String img, int idPartaker) {
		this.id = id;
		this.setTitle(title);
		this.setDescription(description);
		this.setCategory(category);
		this.setDate(date);
		this.setCapacity(capacity);
		this.setPlace(place);
		this.setImg(img);
		this.setIdPartaker(idPartaker);
	}
	
	/**
	 * Returns the identifier of the event.
	 * 
	 * @return the identifier of the event.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the title of the event.
	 * 
	 * @return the title of the event.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of this event.
	 * 
	 * @param title the new title of the event.
	 * @throws NullPointerException if the {@code title} is {@code null}.
	 */
	public void setTitle(String title) {
		this.title = requireNonNull(title, "Title can't be null");
	}

	/**
	 * Returns the description of the event.
	 * 
	 * @return the description of the event.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of this event.
	 * 
	 * @param description the new description of the event.
	 * @throws NullPointerException if the {@code description} is {@code null}.
	 */
	public void setDescription(String description) {
		this.description = requireNonNull(description, "Description can't be null");
	}

	/**
	 * Returns the category of the event.
	 * 
	 * @return the category of the event.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Set the category of this event.
	 * 
	 * @param category the new category of the event.
	 * @throws NullPointerException if the {@code category} is {@code null}.
	 * @throws IllegalArgumentException if the {@code category} is not in the define values.
	 */
	public void setCategory(String category) {
		int i=0;
		if (category != null) {
			while(i< categories.values().length && !categories.values()[i].toString().equalsIgnoreCase(category)) {
				i++;
			}
		
			if (i!=categories.values().length) {
				this.category = category;
			}else {
				throw new IllegalArgumentException("Category must be in the already define values");
			}
		}else {
			throw new NullPointerException("Category can't be null");
		}
	}

	/**
	 * Returns the date of the event.
	 * 
	 * @return the date of the event.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the date of this event.
	 * 
	 * @param date the new date of the event.
	 * @throws NullPointerException if the {@code date} is {@code null}.
	 * @throws IllegalArgumentException if the {@code date} is before the actual date.
	 */
	public void setDate(Date date) {
//		Calendar calendar = Calendar.getInstance();
//		if(calendar.getTime().after(date)) {
//			throw new IllegalArgumentException("Date must be after the actual date");	
//		}else {
			this.date = requireNonNull(date, "Date can't be null");
//		}
	}

	/**
	 * Returns the capacity of the event.
	 * 
	 * @return the capacity of the event.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Set the capacity of this event.
	 * 
	 * @param capacity the new capacity of the event.
	 * @throws IllegalArgumentException if the {@code capacity} is lower than 2 and high than 20.
	 */
	public void setCapacity(int capacity) {
		if(capacity < 2 || capacity > 20) {
			throw new IllegalArgumentException("Capacity must be between 2 and 20");
		}else {
			this.capacity = capacity;
		}
	}

	/**
	 * Returns the place of the event.
	 * 
	 * @return the place of the event.
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Set the place of this event.
	 * 
	 * @param place the new place of the event.
	 * @throws NullPointerException if the {@code place} is {@code null}.
	 */
	public void setPlace(String place) {
		this.place = requireNonNull(place, "Place can't be null");
	}

	/**
	 * Returns the image of the event.
	 * 
	 * @return the image of the event.
	 */
	public String getImg() {
		return img;
	}

	/**
	 * Set the image of this event.
	 * 
	 * @param img the new image of the event.
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * Returns the idPartaker (creator) of the event.
	 * 
	 * @return the idPartaker of the event.
	 */
	public int getIdPartaker() {
		return idPartaker;
	}

	/**
	 * Set the idPartaker (creator) of the event.
	 * 
	 * @param idPartaker the new image of the event.
	 * @throws NullPointerException if the {@code idPartaker} is {@code null}.
	 */
	public void setIdPartaker(int idPartaker) {
		if(idPartaker > 0) {
			this.idPartaker = idPartaker;
		}else{
			throw new NullPointerException("IdPartaker can't be null");
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
		if (!(obj instanceof Event))
			return false;
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}
}

