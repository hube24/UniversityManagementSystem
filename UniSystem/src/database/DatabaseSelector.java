package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import university.Degree;
import users.Student;

public class DatabaseSelector extends SqlDriver{

	public DatabaseSelector() { 
		// TODO Auto-generated constructor stub
	}	
	
	public List<String[]> GetTableList(String query)
	{
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {

			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement(query);
			ResultSet rs = pst1.executeQuery();
			
			int nCol = rs.getMetaData().getColumnCount();
			List<String[]> table = new ArrayList<>();
			
			while( rs.next()) {
			    String[] row = new String[nCol];
			    for( int iCol = 1; iCol <= nCol; iCol++ ){
			            Object obj = rs.getObject( iCol );
			            row[iCol-1] = (obj == null) ? null:obj.toString();
			    }
			    table.add( row );
			}
			con.close(); 
			return table; 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		return null;
	}
	
	public void delete(String query, String id) {	
		
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, id);
			pst1.executeUpdate();
			con.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void delete(String query, int id) {	
		
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setInt(1, id);
			pst1.executeUpdate();
			con.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public boolean notInDatabase(String query, String id) {
		try (Connection con = DriverManager.getConnection(DB,DBuser, DBpassword)) {
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, id);
			ResultSet rs = pst1.executeQuery();			
			if(rs.next()) {
				con.close();
				return false;				
				
			} else {
				con.close();
				return true; 
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} 
		return false;		
	}
	
	
	public boolean deleteUser(String username) {
		if(notInDatabase("SELECT * FROM Student WHERE username = ?", username)) {
			delete("DELETE FROM Users WHERE username = ?", username);
			return true;
		}else {
			return false;
		}
	}	
	
	public boolean deleteDepartment(String code) {
		if(notInDatabase("SELECT * FROM DepartmentDegree WHERE codeOfDepartment = ?", code)) {
			delete("DELETE FROM Department WHERE codeOfDepartment = ?", code);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean deleteDegree(String code) {
		if(notInDatabase("SELECT * FROM ModuleDegree WHERE codeOfDegree = ?", code)) {
			delete("DELETE FROM DepartmentDegree WHERE codeOfDegree = ?", code);
			delete("DELETE FROM Degree WHERE codeOfDegree = ?", code);			
			return true;
		}else {
			return false;
		}
	}
	
	public boolean deleteModule(String code) {
		if(notInDatabase("SELECT * FROM ModuleRegistration WHERE codeOfModule = ?", code)) {
			delete("DELETE FROM ModuleDegree WHERE codeOfModule = ?", code);
			delete("DELETE FROM Module WHERE codeOfModule = ?", code);			
			return true;
		}else {
			return false;
		}
	}
	
	public void deleteStudent(int regNum) {
			delete("DELETE FROM ModuleRegistration WHERE registrationNum = ?", regNum);
			delete("DELETE FROM StudentStudyPeriod WHERE registrationNum = ?", regNum);
			delete("DELETE FROM Student WHERE registrationNum = ?", regNum);	
	}
	
	public List<String[]> getDegreesFromDepartment(String code){
		
		return GetTableList("SELECT * FROM Degree INNER JOIN DepartmentDegree ON Degree.codeOfDegree = DepartmentDegree.codeOfDegree "
				+ "WHERE DepartmentDegree.codeOfDepartment = '"+ code + "';");
				
	}
	public List<String[]> getModulesFromDegrees(String code){
		
		return GetTableList("SELECT * FROM Module INNER JOIN ModuleDegree ON Module.codeOfModule = ModuleDegree.codeOfModule "
				+ "WHERE ModuleDegree.codeOfDegree = '"+ code + "';");
				
	}

	public List<String[]> GetDepartmentList()
	{ 
		return GetTableList("SELECT * FROM Department");
	}	
	
	public List<String[]> GetDegreesList()
	{
		return GetTableList("SELECT * FROM Degree");
	}
	public List<String[]> GetUsersList()
	{
		return GetTableList("SELECT * FROM Users");
	}
	public List<String[]> GetModulesList()
	{
		return GetTableList("SELECT * FROM Module");
	}
	public List<String[]> getUserWithAccessList(String acs)
	{
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement("SELECT username FROM Users WHERE access = ?");
			pst1.setString(1, acs);
			ResultSet rs = pst1.executeQuery();
			
			int nCol = rs.getMetaData().getColumnCount();
			List<String[]> table = new ArrayList<>();
			while( rs.next()) {
			    String[] row = new String[nCol];
			    for( int iCol = 1; iCol <= nCol; iCol++ ){
			            Object obj = rs.getObject( iCol );
			            row[iCol-1] = (obj == null) ? null:obj.toString();
			    }
			    table.add( row );
			}
			con.close(); 
			return table; 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}


	public List<String[]> GetPeriodsOfStudyList() {
		return GetTableList("SELECT * FROM PeriodOfStudy");
	}
	
	public List<String[]> getOptionalModules(Degree degree, String level)
	{
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement(" SELECT * FROM Module INNER JOIN ModuleDegree ON Module.codeOfModule = ModuleDegree.codeOfModule" + 
							" WHERE ModuleDegree.codeOfDegree = ? AND ModuleDegree.isCore = '0' AND ModuleDegree.level = ?");
			pst1.setString(1, degree.getCode());
			pst1.setString(2, level);
			ResultSet rs = pst1.executeQuery();
			
			int nCol = rs.getMetaData().getColumnCount();
			List<String[]> table = new ArrayList<>();
			while( rs.next()) {
			    String[] row = new String[nCol];
			    for( int iCol = 1; iCol <= nCol; iCol++ ){
			            Object obj = rs.getObject( iCol );
			            row[iCol-1] = (obj == null) ? null:obj.toString();
			    }
			    table.add( row );
			}
			con.close(); 
			return table; 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	/*
	public double getDegreeAvarage(Student student) {
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement(" SELECT * FROM StudentStudyPeriod WHERE registrationNum = ?");
			pst1.setInt(1, student.getRegistrationID());
			ResultSet rs = pst1.executeQuery();
			
			int nCol = rs.getMetaData().getColumnCount();
			
			//weighted avarage 
			double avup = 0;
			double avdown = 0;
			
			int 
			
			while( rs.next()) {
				String level = (String)rs.getString(4);
				
				int grade = (int)rs.getInt(3);
				
				if(level = )
			}
			con.close(); 
			return (double)avup/avdown;

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
	*/
	
	public List<String[]> getRegisteredModules(Student student) {
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			//get all rows in table 
			String level = student.getCurrentLevel();
			PreparedStatement pst1 = con.prepareStatement("SELECT * " + 
														"FROM Module " + 
														"INNER JOIN ModuleRegistration ON Module.codeOfModule = ModuleRegistration.codeOfModule " + 
														"INNER JOIN ModuleDegree ON ModuleRegistration.codeOfModule = ModuleDegree.codeOfModule " + 
														"WHERE ModuleRegistration.registrationNum = ? AND ModuleDegree.level = ?;");
												
			pst1.setInt(1, student.getRegistrationID());
			pst1.setString(2, level);
			ResultSet rs = pst1.executeQuery();
			
			int nCol = rs.getMetaData().getColumnCount();
			List<String[]> table = new ArrayList<>();
			while( rs.next()) {
			    String[] row = new String[nCol];
			    for( int iCol = 1; iCol <= nCol; iCol++ ){
			            Object obj = rs.getObject( iCol );
			            row[iCol-1] = (obj == null) ? null:obj.toString();
			    }
			    table.add( row );
			}
			con.close(); 
			return table; 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
	
	public List<String> getStudentGradesList(Student student, String code) {
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement(" SELECT ModuleRegistration.firstGrade, ModuleRegistration.secondGrade FROM Module INNER JOIN ModuleRegistration ON Module.codeOfModule = ModuleRegistration.codeOfModule" + 
		            " WHERE ModuleRegistration.registrationNum = ? AND ModuleRegistration.codeOfModule = ?");
			pst1.setInt(1, student.getRegistrationID());
			pst1.setString(2, code);
			ResultSet rs = pst1.executeQuery();			
			List<String> list = new  ArrayList<>();
			 String first =  null;
			 String seccond = null;
			if(rs.next()) {
				
				 first =  rs.getString(1);
				 seccond = rs.getString(2);
				 list.add(first);
				 list.add(seccond); 
				 }

			con.close(); 
			return list;

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
	
	public List<String[]> getCoreModulesList(Degree degree, String level) {
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {
			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement(" SELECT * FROM Module INNER JOIN ModuleDegree ON Module.codeOfModule = ModuleDegree.codeOfModule" + 
					" WHERE ModuleDegree.codeOfDegree = ? AND ModuleDegree.isCore = '1' AND ModuleDegree.level = ?");
			pst1.setString(1, degree.getCode());
			pst1.setString(2, level);
			ResultSet rs = pst1.executeQuery();
			
			int nCol = rs.getMetaData().getColumnCount();
			List<String[]> table = new ArrayList<>();
			while( rs.next()) {
			    String[] row = new String[nCol];
			    for( int iCol = 1; iCol <= nCol; iCol++ ){
			            Object obj = rs.getObject( iCol );
			            row[iCol-1] = (obj == null) ? null:obj.toString();
			    }
			    table.add( row );
			}
			con.close(); 
			return table; 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
	
	public List<String[]> GetStudentsList(){
		return GetTableList("SELECT * FROM Student");
	}

	public int getCount(String query)
	{
		try (Connection con = DriverManager.getConnection(DB,DBuser, DBpassword)) {
			PreparedStatement pst1 = con.prepareStatement(query);
			ResultSet rs = pst1.executeQuery();			
			if(rs.next()) {
				int count = rs.getInt(1);
				con.close();
				return count;
			}
			
			con.close();
			return 0; 
			
		} catch (Exception exc) {
			exc.printStackTrace();
		} 
		return 0;
	}

	
	public int getStudentCount(){
		return getCount("SELECT COUNT(*) FROM Student");
	}
	
}
