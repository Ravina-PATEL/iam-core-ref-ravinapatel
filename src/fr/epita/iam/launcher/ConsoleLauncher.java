/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.IOException;
import fr.epita.iam.authentication.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.*;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.JDBCIdentityDAO;

/**
 * @author RAVINA
 *
 */

public class ConsoleLauncher {
	
	
	private static final Logger logger =  LogManager.getLogger(ConsoleLauncher.class);
	private static JDBCIdentityDAO dao;
	private ConsoleLauncher()
	{
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		logger.info("Hello, welcome to the IAM application");
		Scanner scanner = new Scanner(System.in);
		dao = new JDBCIdentityDAO();	
		//authentication
		logger.info("Please enter your login");
		String login = scanner.nextLine();
		logger.info("Please enter your password");
		String password = scanner.nextLine();
		
		Authenticate authService = new Authenticate();
		if(!authService.authenticate(login, password)){
			scanner.close();
			return;
		}
		
		// menu
		do{
		String answer = menu(scanner);
		
		switch (answer) {
		case "a":
			// creation
			createIdentity(scanner);
			break;
		case "b":
			updateIdentity(scanner);
			break;
		case "c":
			deleteIdentity(scanner);
			break;
		case "d":
			listIdentities();
			break;
		case "e":
			terminationProcess();
			
			break;
			
			
		default:
			logger.info("This option is not recognized ("+ answer + ")");
			break;
		}
		}while(confirmation(scanner));
		scanner.close();

	}
	private static void terminationProcess() {
		logger.info("Good bye...");
		
	}

	private static boolean confirmation(Scanner scanner) {
		logger.info("Do you want to continue Y/N");
		String flag= scanner.nextLine().toUpperCase();
		if("Y".equals(flag)){
			return true;
		}else if("N".equals(flag)){
			return false;
		}
		return false;
	}

	

		
		
		private static void updateIdentity(Scanner scanner) throws SQLException {
			logger.info("You've selected : Identity Updation");
			logger.info("Please enter the Identity display number");
			String displayNumber = scanner.nextLine();
			logger.info("Please enter the Identity Name");
			String name = scanner.nextLine();
			logger.info("Please enter the Identity email");
			String email = scanner.nextLine();
			Identity newIdentity = new Identity(displayNumber, name, email);
			dao.modifyIdentity(newIdentity);
			logger.info("you succesfully updated this identity :" + newIdentity);
		
		
		}

	    private static void deleteIdentity(Scanner scanner) throws SQLException {
		
			logger.info("You've selected : Identity Deletion");
			logger.info("Please enter the Identity display number for deletion");
			String displayNumber = scanner.nextLine();
			Identity delIdentity = new Identity(displayNumber, null, null);
			dao.delteteIdentity1(delIdentity);
			
			logger.info("you succesfully deleted this identity :" + delIdentity );
	    }

	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	
	
	
	
	
	
	
	private static void listIdentities() throws SQLException {
		logger.info("This is the list of all identities in the system");
		List<Identity> list = dao.readAll();
		int size = list.size();
		for(int i = 0; i < size; i++){
			logger.info( i+ "." + list.get(i));
		}
		
	}

	/**
	 * @param scanner
	 * @throws SQLException 
	 */
	
	
	
	
	private static void createIdentity(Scanner scanner) throws SQLException {
		logger.info("You've selected : Identity Creation");
		logger.info("Please enter the Identity display name");
		String displayName = scanner.nextLine();
		logger.info("Please enter the Identity email");
		String email = scanner.nextLine();
		Identity newIdentity = new Identity(null, displayName, email);
		dao.writeIdentity(newIdentity);
		logger.info("you succesfully created this identity :" + newIdentity);
	}

	/**
	 * @param scanner
	 * @return
	 */
	
	
	private static String menu(Scanner scanner) {
		logger.info("You're authenticated");
		logger.info("Here are the actions you can perform :");
		logger.info("a. Create an Identity");
		logger.info("b. Modify an Identity");
		logger.info("c. Delete an Identity");
		logger.info("d. List Identities");
		logger.info("e. quit");
		logger.info("your choice (a|b|c|d|e) ? : ");
		
		return scanner.nextLine();
	}

	/**
	 * @param login
	 * @param password
	 * 
	 * 

	 */
	
	
	
	
	
 }
