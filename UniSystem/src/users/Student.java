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

public class Student extends User {
	
	private String title;
	private int registrationID;
	private String forename;
	private String surname;
	private String email;
	private Degree degree;
	private String tutor;
	private Grade grade;
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


	public String getTitle() {return this.title;}
	public int getRegistrationID() {return this.registrationID;}
	public String getForename() {return this.forename;}
	public String getSurname() {return this.surname;}
	public String getEmail() {return this.email;}
	public Degree getDegree() {return this.degree;}
	public Grade getGrade() {return this.grade;}
	public String getPersonalTutor() {return this.tutor;}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setRegistrationID(int registrationID) {
		this.registrationID = registrationID;
	}
	
	public String getCurrentLevel() {
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1 = con.prepareStatement("SELECT level FROM StudentStudyPeriod " + 
												 "WHERE registrationNum = ? AND label = (SELECT MAX(label) " +
												 "FROM StudentStudyPeriod " +
												 "WHERE registrationNum = ?) ");
			pst1.setInt(1, this.registrationID);
			pst1.setInt(2, this.registrationID);
			
			ResultSet rs = pst1.executeQuery();	
			if(rs.next())
			{
				String lvl = (String)rs.getString(1);
				con.close();
				return lvl;
			}
			
		con.close();	
		return null;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}
	}
}
