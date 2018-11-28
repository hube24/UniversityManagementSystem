package users;
import university.Module;

public class Teacher extends User {
	
	String title;
	String surname;
	String forename;
	String email;
	
	public Teacher (String t, String s, String f, String e) {
		title = t;
		surname = s;
		forename = f;
		email = e;
	}
	
	public int updateGrade(Student s, Module m, Grade g) {
		return null;
	}

	public int finalGrade(Student s, Module m, Grade g) {
		return null;
	}
	
	public Student registerStudent() {
		return Student s;
	}
	
	public Degree calculateDegree() {
		return degree;
	}
	

}
