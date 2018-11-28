package database;

public class Session {
	
	String username;
	String password;
	String access;
	
	
	public Session(String u, String p, String a) {
		username = u;
		password = p;
		access = a;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public String getAccess() {
		return access;
	}
	
	public String getPassword() {
		return password;
	}

	
}
