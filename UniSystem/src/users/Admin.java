package users;

public class Admin extends User {

	String title;
	String surname;
	String forename;
	String email;

	public Admin (String t, String s, String f, String e) {
		title = t;
		surname = s;
		forename = f;
		email = e;
	}
	public String getTitle() {
		return title;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getForename() {
		return forename;
	}
	
	public String getEmail() {
		return email;
	}
	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		return false;
	}

}
