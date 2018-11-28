package users;
import java.util.Collection;
import university.*;

 public class Student extends User {
	int registrationID;
	String title;
	String surname;
	String forename;
	String email;
	Degree degree;
	String tutor;
	Grade grade;
	String level;
	int startdate,enddate;
	
 	public Student (String u, int r, String t, String s, String f, String e, Degree d, String tut, Grade g) {
		username = u;
 		registrationID = r;
		title = t;
		surname = s;
		forename = f;
		email = e;
		degree = d;
		tutor = tut;
		grade = g;
	}
	
	public int getRegistrationID() {
		return registrationID;
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
	
	public Degree getDegree() {
		return degree;
	}
 	public String getPersonalTutor() {
		return tutor;
	}
	
	public Grade getGrade() {
	 	return grade;
	}
	
	public String getLevel() {
	 	return level;
	}
	
}
 