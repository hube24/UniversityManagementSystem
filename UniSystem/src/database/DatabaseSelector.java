package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		return GetTableList("SELECT username FROM Users WHERE access = '"+ acs +"'");
	}


	public List<String[]> GetPeriodsOfStudyList() {
		return GetTableList("SELECT * FROM PeriodOfStudy");
	}
	
	public List<String[]> getOptionalModules(Degree degree, String level)
	{
		return GetTableList(" SELECT * FROM Module INNER JOIN ModuleDegree ON Module.codeOfModule = ModuleDegree.codeOfModule" + 
							" WHERE ModuleDegree.codeOfDegree = '" + degree.getCode() + "' AND ModuleDegree.isCore = '0' AND ModuleDegree.level = '" + level + "';");
	}

	public List<String[]> getRegisteredModules(Student student) {
		return GetTableList(" SELECT * FROM Module INNER JOIN ModuleRegistration ON Module.codeOfModule = ModuleRegistration.codeOfModule" + 
				            " WHERE ModuleRegistration.registrationNum = '" + student.getRegistrationID() + "';");
	}
	
	public List<String[]> getCoreModulesList(Degree degree, String level) {
		return GetTableList(" SELECT * FROM Module INNER JOIN ModuleDegree ON Module.codeOfModule = ModuleDegree.codeOfModule" + 
				" WHERE ModuleDegree.codeOfDegree = '" + degree.getCode() + "' AND ModuleDegree.isCore = '1' AND ModuleDegree.level = '" + level + "';");
	}
	
}
