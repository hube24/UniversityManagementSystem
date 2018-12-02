package university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.SqlDriver;

public class Degree {

	String name;
	String code;
	int numberOfLevels;
	Department leadDepartment;
	Department[] listOfDepartments;
	Module[] listOfModules;
	
	
	public Degree(String c) {
		code = c;
		// TODO Auto-generated constructor stub
	}
	
	public Degree(String n, String c, int nOL) {
		name = n;
		code = c;
		numberOfLevels = nOL;
		// TODO Auto-generated constructor stub
	}
	
	//this function completes Degree info from database, by code of Degree
	public void completeFromDB()
	{
		if(code.equals(null)){
			System.out.print("no degree code");
			return;
		}
		
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement("SELECT * FROM Degree WHERE codeOfDegree = '" + code + "'");
			ResultSet rs = pst1.executeQuery();
			
			if( rs.next()) {
	            name = rs.getString( 2 );
	            numberOfLevels = rs.getInt( 3 );
	            leadDepartment = new Department(code.substring(0,3));
	            leadDepartment.completeFromDB();
	            
			}else{
				System.out.print("no degree with given code");
			}
			con.close(); 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		
		// to do: add deparments & modules
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public Department getLeadDepartment() {
		return leadDepartment;
	}
	
	public int getNumberOfLevels() {
		return numberOfLevels;
	}
	
	public Department[] getListOfDepartments() {
		return listOfDepartments;
	}
	
	public void setLeadDepartment(Department leadDepartment) {
		this.leadDepartment = leadDepartment;
	}
	
	public void setListOfDepartments(Department[] listOfDepartments) {
		this.listOfDepartments = listOfDepartments;
	}
	
	
}
