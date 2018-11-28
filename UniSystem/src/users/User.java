package users;
public abstract class User {

	protected String username;

	protected String password;

	public String getUsername() {
		return username;
	}
	public void setUsername(String u) {
		username=u;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String p) {
		password=p;
	}

}
