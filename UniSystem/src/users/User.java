package users;
public abstract class User {
	
	//create elements in User
	protected String username;

	protected String password;
	
	//get Username
	public String getUsername() {
		return username;
	}
	//set Username
	public void setUsername(String u) {
		username=u;
	}
	//get Password
	public String getPassword() {
		return password;
	}
	//setPassword
	public void setPassword(String p) {
		password=p;
	}

}
