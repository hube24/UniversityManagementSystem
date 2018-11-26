package users;
import java.util.Collection;

 public class Student extends User {
	int registrationID;
	String title;
	String surname;
	String forename;
	String email;
	Degree degree;
	Teacher personalTutor;
	Grade grade;
	String level;
	int startdate,enddate;
	
 	public Student (int r, String t, String s, String f, String e, Degree d, Teacher tutor, Grade g, String l) {
		registrationID = r;
		title = t;
		surname = s;
		forename = f;
		email = e;
		degree = d;
		personalTutor = tutor;
		grade = g;
		level = l;
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
 	public Teacher getPersonalTutor() {
		return tutor;
	}
	
	public Grade getGrade() {
	 	return grade;
	}
	
	public String getLevel() {
	 	return level;
	}
	
	public int registerPeriod(int a, int b) {
		startdate = a;
		enddate =b;
	}
	
	public String displayStatus() {
		return null;
	}
 	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		return false;
	}
 }