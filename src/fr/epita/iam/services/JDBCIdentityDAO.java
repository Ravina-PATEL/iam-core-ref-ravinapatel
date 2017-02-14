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
import fr.epita.iam.exception.MyException;
import fr.epita.iam.launcher.ConsoleLauncher;

/**
 * @author RAVINA_PATEL
 *
 */
public class JDBCIdentityDAO {

	// Instance of logger to show
	private static final Logger logger =  LogManager.getLogger(ConsoleLauncher.class);
	private Connection connection;
	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	
	
	// JDBC Connection to Database
	public JDBCIdentityDAO() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/sample;create=true","TOM","TOM");
		logger.info(connection.getSchema());
	}
	
	
	// Insert query to write in Identity Table
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
	

	// Select query to show the list of identities from Identity Table
	public List<Identity> readAll() throws SQLException {
		logger.info(connection.getSchema());
		List<Identity> identities = new ArrayList<Identity>();

		PreparedStatement pstmtSelect = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmtSelect.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String email = rs.getString("IDENTITIES_EMAIL");
			Identity identity = new Identity(uid, displayName, email);
			identities.add(identity);
			
		}
		pstmtSelect.close();
		return identities;

	}
	
	
	// Update Query to modify identity in the Identity Table
	public void modifyIdentity(Identity identity) throws SQLException {
			String updateStatement = "update IDENTITIES set IDENTITIES_DISPLAYNAME = ?, IDENTITIES_EMAIL = ? where IDENTITIES_UID = ?";
			PreparedStatement pstmtUpdate = connection.prepareStatement(updateStatement);
			pstmtUpdate.setString(1, identity.getDisplayName());
			pstmtUpdate.setString(2, identity.getEmail());		
			pstmtUpdate.setString(3, identity.getUid());
			pstmtUpdate.execute();
			pstmtUpdate.close();
	}
	
	
	// Delete Query delete identity in the Identity Table
	public void delteteIdentity1(Identity identity) throws SQLException, MyException {
			verify(identity);
			String deleteStatement = "delete from IDENTITIES where IDENTITIES_UID = ?";
			PreparedStatement pstmtDelete = connection.prepareStatement(deleteStatement);
			pstmtDelete.setString(1, identity.getUid());
			pstmtDelete.execute();
			pstmtDelete.close();
			logger.info("Identity deleted\n");	
	}


	//to check that entered UID is available or not in database table
	public void verify(Identity identity) throws SQLException, MyException {

		String deleteStatement = "select IDENTITIES_UID from IDENTITIES where IDENTITIES_UID = ?";
		PreparedStatement pstmtSelect = connection.prepareStatement(deleteStatement);
		pstmtSelect.setString(1, identity.getUid());
		ResultSet rs = pstmtSelect.executeQuery();
		if(rs.next()){
			pstmtSelect.close();
		}else{
			pstmtSelect.close();			
			throw new MyException("Identity not found");
		}
	
	}
	
}