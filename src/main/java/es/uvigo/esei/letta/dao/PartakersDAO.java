package es.uvigo.esei.letta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.letta.entities.Partaker;
/**
 * DAO class for the {@link Partaker} entities.
 * 
 * @author Emma Álvarez Conde
 * @author Sergio Castro Gil
 * @author Alberto Mateo Sabucedo González - Revisión.
 *
 */
public class PartakersDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(PartakersDAO.class.getName());
	
	
	
	/**
	 * Returns a partaker stored persisted in the system.
	 * 
	 * @param id identifier of a partaker.
	 * @return a partaker with the provided identifier.
	 * @throws DAOException if an error happens while retrieving the partaker.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted partaker.
	 */
	public Partaker get(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM partakers WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id of the partaker");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting an partaker", e);
			throw new DAOException(e);
		}
	}
	
	private Partaker rowToEntity(ResultSet row) throws SQLException {
		return new Partaker(
			row.getInt("id"),
			row.getString("name"),
			row.getString("surname"),
			row.getInt("userId")
		);
	}
}
