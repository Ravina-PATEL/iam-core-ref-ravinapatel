package fr.epita.iam.authentication;

public class Authenticate {
	public boolean authenticate(String username, String password) {
		// authentication method
		if ("adm" .equals(username) && "pwd" .equals(password))
		return true;
		else 
			return false;
	}
}