package users;

/*  the class for administrator to perform the tasks (add accounts to database, add department, add module
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import encryption.BCrypt;
import javax.swing.JOptionPane;

import database.SqlDriver;

public class Administrator {

	SqlDriver sqldriver = new SqlDriver();
	
	public Administrator() {
		// TODO Auto-generated constructor stub
	}
	
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean addDepartment(String code, String name) {

		if (code.length() != 3) {

			infoBox("Department code must be 3 letters long.", "Warning");
			return false;
		}

		code = code.toUpperCase();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			// check if department already exist
			System.out.println("connected");
			String query = "SELECT * FROM Department WHERE codeOfDepartment = ?";
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, code);
			ResultSet rs = pst1.executeQuery();
			if (rs.next()) {
				infoBox("Department with given code already exist.", "Warning");
				con.close();
				return false;
			}

			// inserting new department
			String insertDptQ = "INSERT INTO Department (codeOfDepartment, name)" + "VALUES (?, ?)";
			PreparedStatement pst2 = con.prepareStatement(insertDptQ);
			pst2.setString(1, code);
			pst2.setString(2, name);
			pst2.executeUpdate();
			con.close();
			return true;

		} catch (Exception exc) {
			infoBox("The Department could not be added.", "Warning");
			exc.printStackTrace();
			return false;
		}

	}
	
	
	public boolean addUser(String username, String password, String access)
	{
		
		//password encryption using bcrypt
		String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt()); 
		
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			// check if the user already exist
			System.out.println("connected");
			String query = "SELECT * FROM Users WHERE username = ?";
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, username);
			ResultSet rs = pst1.executeQuery();
			if (rs.next()) {
				infoBox("User with given usernamer already exist.", "Warning");
				con.close();
				return false;
			}

			// inserting new user
			String insertDptQ = "INSERT INTO Users (username, password, access)" + "VALUES (?, ?, ?)";
			PreparedStatement pst2 = con.prepareStatement(insertDptQ);
			pst2.setString(1, username);
			pst2.setString(2, passwordHash);
			pst2.setString(3, access);
			pst2.executeUpdate();
			con.close();
			return true;

		} catch (Exception exc) {
			infoBox("User could not be added.", "Warning");
			exc.printStackTrace();
			return false;
		}
	}
	
	
	public boolean addDegree(String code, String name, String leadDepartment, int numOfLevels, String[] departments)
	{
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			// check number of degrees with same code to obtain unique serial number

			String query = "SELECT COUNT(codeOfDegree) FROM Degree WHERE codeOfDegree LIKE '" + code.substring(0, 3) + "%';";
			PreparedStatement pst1 = con.prepareStatement(query);
			ResultSet rs = pst1.executeQuery();
			rs.next();
			int numOfRows = rs.getInt(1);
            System.out.print(numOfRows);
            
            numOfRows+=1;
            if(numOfRows<10) code = code + "0" + String.valueOf(numOfRows);
            else code = code + String.valueOf(numOfRows);
            
            
			// inserting new degree
			String insertDptQ = "INSERT INTO Degree (codeOfDegree, name, numberOfLevels)" + "VALUES (?, ?, ?)";
			PreparedStatement pst2 = con.prepareStatement(insertDptQ);
			pst2.setString(1, code);
			pst2.setString(2, name);
			pst2.setInt(3, numOfLevels);
			pst2.executeUpdate();
			
			
			// linking to Departments
			
			for(String dptCode : departments)
			{
				String insertDegreeDpt = "INSERT INTO DepartmentDegree (codeOfDepartment, codeOfDegree, isLead)" + "VALUES (?, ?, ?)";
				PreparedStatement pst3 = con.prepareStatement(insertDegreeDpt);
				pst3.setString(1, dptCode);
				pst3.setString(2, code);
				pst3.setBoolean(3, (leadDepartment == dptCode) );
				pst3.executeUpdate();
			}

			con.close();
			return true;
			
		} catch (Exception exc) {
			infoBox("Degree could not be added.", "Warning");
			exc.printStackTrace();
			return false;
		}
	}
	  
	
	public boolean addModule(String code, String name, int numOfCredits, List<Object[]> degrees)
	{

		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			// check if module with given code already exist

			String query = "SELECT * FROM Module WHERE codeOfModule = ?";
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, code);
			ResultSet rs = pst1.executeQuery();
			if (rs.next()) {
				infoBox("Module with given serial number already exist.", "Warning");
				con.close();
				return false;
			}
            
            
			// inserting new module
			String insertModQ = "INSERT INTO Module (codeOfModule, name, credits)" + "VALUES (?, ?, ?)";
			PreparedStatement pst2 = con.prepareStatement(insertModQ);
			pst2.setString(1, code);
			pst2.setString(2, name);
			pst2.setInt(3, numOfCredits);
			pst2.executeUpdate();
			
			
			// linking to Departments
			
			for(Object[] degreeRow : degrees)
			{
				String insertModuleDegree = "INSERT INTO ModuleDegree (codeOfModule, codeOfDegree, level, isCore, term)" + "VALUES (?, ?, ?, ?, ?)";
				PreparedStatement pst3 = con.prepareStatement(insertModuleDegree);
				pst3.setString(1, code);
				pst3.setString(2, (String)degreeRow[0]);
				pst3.setString(3, (String)degreeRow[1]);
				pst3.setBoolean(4, (boolean)degreeRow[2]);
				pst3.setString(5, (String)degreeRow[3]);
				pst3.executeUpdate();
			}

			con.close();
			return true;
			
		} catch (Exception exc) {
			infoBox("Module could not be added.", "Warning");
			exc.printStackTrace();
			return false;
		}
	}

}
