/**
 * 
 */
package fr.epita.iam.services;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.*;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.launcher.ConsoleLauncher;

/**
 * @author RAVINA
 *
 */
public class JDBCIdentityDAO {

	
	private static final Logger logger =  LogManager.getLogger(ConsoleLauncher.class);
	private Connection connection;
	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public JDBCIdentityDAO() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/sample;create=true","TOM","TOM");
		logger.info(connection.getSchema());
	}
	
	
	public void writeIdentity(Identity identity) throws SQLException {

		String insertStatement = "insert into IDENTITIES (IDENTITIES_DISPLAYNAME, IDENTITIES_EMAIL, IDENTITIES_BIRTHDATE) "
				+ "values(?, ?, ?)";
		PreparedStatement pstmtInsert = connection.prepareStatement(insertStatement);
		pstmtInsert.setString(1, identity.getDisplayName());
		pstmtInsert.setString(2, identity.getEmail());
		Date now = new Date();
		pstmtInsert.setDate(3, new java.sql.Date(now.getTime()));

		pstmtInsert.execute();
		pstmtInsert.close();
	}

	public List<Identity> readAll() throws SQLException {
		logger.info(connection.getSchema());
		List<Identity> identities = new ArrayList<Identity>();

		PreparedStatement pstmtSelect = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmtSelect.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String email = rs.getString("IDENTITIES_EMAIL");
			//Date birthDate = rs.getDate("IDENTITIES_BIRTHDATE");
			Identity identity = new Identity(uid, displayName, email);
			identities.add(identity);
			
		}
		pstmtSelect.close();
		return identities;

	}
	
	
	public void modifyIdentity(Identity identity) throws SQLException {

		String updateStatement = "update IDENTITIES set IDENTITIES_DISPLAYNAME = ?, IDENTITIES_EMAIL = ? where IDENTITIES_UID = ?";
		PreparedStatement pstmtUpdate = connection.prepareStatement(updateStatement);
		pstmtUpdate.setString(1, identity.getDisplayName());
		pstmtUpdate.setString(2, identity.getEmail());		
		pstmtUpdate.setString(3, identity.getUid());

		pstmtUpdate.execute();
		pstmtUpdate.close();
	}
	
	public void delteteIdentity1(Identity identity) throws SQLException {

		String deleteStatement = "delete from IDENTITIES where IDENTITIES_UID = ?";
		PreparedStatement pstmtDelete = connection.prepareStatement(deleteStatement);
		pstmtDelete.setString(1, identity.getUid());
		pstmtDelete.execute();
		pstmtDelete.close();
		logger.info("Identity deleted\n");
	
	}


	
	

	
	
}