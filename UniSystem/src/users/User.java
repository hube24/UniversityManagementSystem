package users;
public abstract class User {

	private String username;

	private String password;

	public String getUsername() {
		return this.username;
	}
	public void setUsername() {
		this.username=username;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword() {
		this.password=password;
	}

	public abstract boolean login();

}
