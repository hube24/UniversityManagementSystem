package users;
import university.Module;

public class Teacher extends User {
	
	String title;
	String surname;
	String forename;
	
	public Teacher (String t, String s, String f, String e) {
		title = t;
		surname = s;
		forename = f;
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
	

}
