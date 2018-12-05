package database;

//Session class to control the data
public class Session {
	
	String username;
	String password;
	String access;
	
	//determine the variables
	public Session(String u, String p, String a) {
		username = u;
		password = p;
		access = a;
	}
	
	//get different Data
	public String getUsername() {
		return username;
	}
	
	public String getAccess() {
		return access;
	}
	
	public String getPassword() {
		return password;
	}
	
	//end
	public void endSession() throws Throwable
	{
		this.finalize();
	}
	
}
