package university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.SqlDriver;

public class Department {

	String code;
	String name;

	public Department(String c) {
		code = c;
	}
	
	public Department(String c, String n) {
		code = c;
		name = n;
	}
	
	public void completeFromDB()
	{
		if(code.equals(null)){
			System.out.print("no department code");
			return;
		}
		
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1 = con.prepareStatement("SELECT name FROM Department WHERE codeOfDepartment = ?");
			pst1.setString(1, code);
			ResultSet rs = pst1.executeQuery();
			
			if( rs.next()) {
	            name = rs.getString( 1 );
			}else{
				System.out.print("no department with given code");
			}
			con.close(); 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
}
