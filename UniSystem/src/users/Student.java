package users;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import university.Degree;
import university.Module;
import database.SqlDriver;

/**
*Student class gives definitions of every needed element
*/
public class Student extends User {
	
	private String title;
	private int registrationNum = -1;
	private String forename;
	private String surname;
	private String email;
	private Degree degree;
	private String tutor;
	private String graduationGrade;
	Statement pst1 = null;
	
	public Student(String u) {
		username = u;
	}
	
	
	public Student(int reg)
	{
		registrationNum = reg;
	}
	
	public Student (String u, int r, String t, String s, String f, String e, Degree d, String tut) {
		username = u;
 		registrationNum = r; 
		title = t;
		surname = s;
		forename = f;
		email = e;
		degree = d;
		tutor = tut;
	}
	
	//this function completes Student's info from database, by registration number.
	public void completeFromDB() {
		

		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			
			PreparedStatement pst1;
			ResultSet rs;
			//check different situations
			if(username==null && registrationNum == -1){
				System.out.print("no registrationNumber is given.");
				return;
			} 
			
			if (registrationNum != -1) {
				pst1 = con.prepareStatement("SELECT * FROM Student WHERE registrationNum = ?");
				pst1.setInt(1, registrationNum);
				rs=pst1.executeQuery();
				
			}else {			
				pst1 = con.prepareStatement("SELECT * FROM Student WHERE username = ?");
				pst1.setString(1, username);
				rs=pst1.executeQuery();
			}			
			
			if(rs.next()) {
			 registrationNum= rs.getInt(1);
			 username= rs.getString(3);
			 title= rs.getString(4);
			 surname= rs.getString(5);
			 forename= rs.getString(6);
			 email= rs.getString(7);
			 tutor= rs.getString(8);				
			} else {
				System.out.print("no student with given degree.");
			}
			
			degree = new Degree(rs.getString(2));
			degree.completeFromDB();
			
			con.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	//return all data of student
	public String getTitle() {return this.title;}
	public int getRegistrationID() {return this.registrationNum;}
	public String getForename() {return this.forename;}
	public String getSurname() {return this.surname;}
	public String getEmail() {return this.email;}
	public Degree getDegree() {return this.degree;}
	public String getPersonalTutor() {return this.tutor;}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setRegistrationID(int registrationID) {
		this.registrationNum = registrationID;
	}
	
	public String getCurrentLevel() {
		
		if(graduationGrade!=null) {
			return "-";
		}else {
			graduationGrade = this.getGraduationGrade();
			if(graduationGrade!=null)return "-";
		}
		
		SqlDriver sqldriver = new SqlDriver();
		//get study level from database
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
		//get period of study from database
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
	
	public String getGraduationGrade() {
		SqlDriver sqldriver = new SqlDriver();
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1 = con.prepareStatement("SELECT graduationGrade FROM Student WHERE registrationNum = ?");
			pst1.setInt(1, this.registrationNum);
			ResultSet rs = pst1.executeQuery();	
			if(rs.next())
			{
				graduationGrade = (String)rs.getString(1);
				return (String)rs.getString(1);
			}
			
		con.close();
		return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public int getRegisteredCredits() {
		SqlDriver sqldriver = new SqlDriver();
		//get registered credits from database
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1 = con.prepareStatement("SELECT Module.credits " + 
					"FROM Module " + 
					"INNER JOIN ModuleRegistration ON Module.codeOfModule = ModuleRegistration.codeOfModule " + 
					"INNER JOIN ModuleDegree ON ModuleRegistration.codeOfModule = ModuleDegree.codeOfModule " + 
					"WHERE ModuleRegistration.registrationNum = ? AND ModuleDegree.level = ?;");
			pst1.setInt(1, this.registrationNum);
			pst1.setString(2, this.getCurrentLevel() );
			ResultSet rs = pst1.executeQuery();	
			
			int credSum = 0;
			
			while(rs.next())
			{
				credSum += (int)rs.getInt(1);
			}
		//end
		con.close();	
		return credSum;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	
}
