package fr.epita.iam.authentication;
import org.apache.logging.log4j.*;


import fr.epita.iam.launcher.ConsoleLauncher;

public class Authenticate {
	private static final Logger logger =  LogManager.getLogger(ConsoleLauncher.class);
	String username;
	String password;
	
	public Authenticate(String username, String password)
	{
		
		this.username=username;
		this.password=password;
		
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public boolean authenticate(String username, String password) {
		
		// authentication method
		
		if("adm" .equals(username) && "pwd" .equals(password))
		return true;
		else 
			logger.info("Unauthorised user");
			return false;
	}
}
