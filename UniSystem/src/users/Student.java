package users;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import university.Degree;
import university.Module;
import university.Grade;
import database.SqlDriver;

public class Student {
	
	private String title;
	private int registerNumber;
	private String forename;
	private String surname;
	private String startDate;
	private String endDate;
	private String level;
	private String emailAddress;
	private String degree;
	private String personalTutor;
	private String grade;
	Statement pst1 = null;
	
	public Student displayStatus(Student student) {
		
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			Statement pst1 = con.createStatement();
			ResultSet r1 = pst1.executeQuery("SELECT * FROM Student");
			int x1= r1.getInt(1);
			String x2= r1.getString(2);
			String x3= r1.getString(3);
			String x4= r1.getString(4);
			String x5= r1.getString(5);
			String x6= r1.getString(6);
			String x7= r1.getString(7);
			String x8= r1.getString(8);
			String x9= r1.getString(9);
			
			return student(x1,x2,x3,x4,x5,x6,x7,x8,x9);
					
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}return student(0,null,null,null,null,null,null,null,null);
	}


	private Student student(int i, Object object, Object object2, Object object3, Object object4, Object object5,
			Object object6, Object object7, Object object8) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getTitle() {return this.title;}
	public int getRegisterNumber() {return this.registerNumber;}
	public String getForename() {return this.forename;}
	public String getSurname() {return this.surname;}
	public String getStartDate() {return this.startDate;}
	public String getEndDate() {return this.endDate;}
	public String getLevel() {return this.level;}
	public String getEmailAddress() {return this.emailAddress;}
	public String getDegree() {return this.degree;}
	public String getGrade() {return this.grade;}
	public String getPersonalTutor() {return this.personalTutor;}
	
}
