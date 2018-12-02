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
	private int registrationNum = -1;
	private String forename;
	private String surname;
	private String email;
	private Degree degree;
	private String tutor;
	private Grade grade;
	Statement pst1 = null;
	
	public Student(String u) {
		username = u;
	}
	
	
	public Student(int reg)
	{
		registrationNum = reg;
	}
	
	public Student (String u, int r, String t, String s, String f, String e, Degree d, String tut, Grade g) {
		username = u;
 		registrationNum = r;
		title = t;
		surname = s;
		forename = f;
		email = e;
		degree = d;
		tutor = tut;
		grade = g;
	}
	
	//this function completes Student's info from database, by registration number.
	public void completeFromDB() {
		

		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			
			Statement pst1 = con.createStatement();
			ResultSet rs;
			if(username.equals(null) && registrationNum == -1){
				System.out.print("no registrationNumber is given.");
				return;
			} if (username.equals(null)) {
				rs = pst1.executeQuery("SELECT * FROM Student WHERE registrationNum = '" + registrationNum + "'");
			}else {			
				rs = pst1.executeQuery("SELECT * FROM Student WHERE username = '" + username + "'");
			}			
			
			if(rs.next()) {
			int registrationNum= rs.getInt(1);
			String codeOfDegree= rs.getString(2);
			String username= rs.getString(3);
			String title= rs.getString(4);
			String surname= rs.getString(5);
			String forename= rs.getString(6);
			String email= rs.getString(7);
			String personalTutor= rs.getString(8);				
			} else {
				System.out.print("no student with given degree.");
			}
			con.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	public String getTitle() {return this.title;}
	public int getRegistrationID() {return this.registrationNum;}
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
		this.registrationNum = registrationID;
	}
	
	public String getCurrentLevel() {
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1 = con.prepareStatement("SELECT level FROM StudentStudyPeriod " + 
												 "WHERE registrationNum = ? AND label = (SELECT MAX(label) " +
												 "FROM StudentStudyPeriod " +
												 "WHERE registrationNum = ?) ");
			pst1.setInt(1, this.registrationNum);
			pst1.setInt(2, this.registrationNum);
			
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
	
	public String getCurrentPeriodOfStudy()
	{
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
		PreparedStatement pst1 = con.prepareStatement("SELECT * FROM PeriodOfStudy WHERE label = "+
														 "( SELECT MAX(label) " +
														 "FROM StudentStudyPeriod " +
														 "WHERE registrationNum = ?)");
			pst1.setInt(1, this.registrationNum);
			ResultSet rs = pst1.executeQuery();	
			
			String label;
			String startDate;
			String endDate;
			if(rs.next())
			{
				label =  (String)rs.getString(1);
				startDate = (String)rs.getString(2);
				endDate =  (String)rs.getString(3);
				return label; //+ " " + startDate + "-" + endDate;
			}else{
				con.close();	
				return "";
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
	}
	
	public int getRegisteredCredits( String periodLabel) {
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1 = con.prepareStatement("SELECT * FROM Module INNER JOIN ModuleRegistration ON Module.codeOfModule = ModuleRegistration.codeOfModule" + 
		            " WHERE ModuleRegistration.registrationNum = ? ;");
			pst1.setInt(1, this.registrationNum);
			ResultSet rs = pst1.executeQuery();	
			
			int credSum = 0;
			
			while(rs.next())
			{
				credSum += (int)rs.getInt(3);
			}
			
		con.close();	
		return credSum;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	
}
