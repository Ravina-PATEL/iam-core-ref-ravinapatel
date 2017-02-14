package fr.epita.iam.authentication;
public class Authenticate {
	
	String username;
	String password;
	
	public Authenticate(String username, String password){
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
	
	
	public boolean validate(Authenticate auth) {
		return "adm".equals(auth.getUsername()) && "pwd".equals(auth.getPassword());
	}
}
