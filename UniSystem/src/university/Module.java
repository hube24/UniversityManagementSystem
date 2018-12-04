package university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.SqlDriver;

public class Module {

	String codeOfModule;
	String name;
	int numberOfCredits;
	
	public Module(String code, String n, int credits) {
		codeOfModule = code;
		name = n;
		numberOfCredits = credits;
	}
	
	public String getCodeOfModule() {
		return codeOfModule;
	}
	public String getName() {
		return name;
	}
	public int getNumberOfCredits() {
		return numberOfCredits;
	}
	
	public void completeFromDB()
	{
		if(codeOfModule.equals(null)){
			System.out.print("no module code");
			return;
		}
		
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			//get all rows in table 
			PreparedStatement pst1 = con.prepareStatement("SELECT * FROM Module WHERE codeOfModule = ?");
			pst1.setString(1, codeOfModule);
			ResultSet rs = pst1.executeQuery();
			
			if( rs.next()) {
	            name = rs.getString( 2 );
	            numberOfCredits = rs.getInt( 3 );
			}else{
				System.out.print("no module with given code");
			}
			con.close(); 

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}	
}
