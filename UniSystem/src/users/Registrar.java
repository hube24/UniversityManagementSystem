package users;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import university.Degree;
import university.Module;
import database.SqlDriver;


public class Registrar extends User {

String universityDomain = "@hogwart.ac.uk";	

public static void infoBox(String infoMessage, String titleBar) {
	JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
}	
	
SqlDriver sqldriver = new SqlDriver();

	public boolean addStudent( Student student)
	{
		String username = student.getUsername();
		String title = student.getTitle();
		String forname = student.getForename();
		String surname = student.getSurname();
		String personalTutor = student.getPersonalTutor();
		String email = student.getEmail();
		Degree degree = student.getDegree();
		int registrationNum = student.getRegistrationID();



		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			if(email.equals(""))
			{
					// check number of people with same surname and first letter of forname
	
					String query = "SELECT COUNT(*) FROM Student WHERE forname LIKE '" + forname.substring(0, 1) + "%' AND surname = '" + surname + "';";
					PreparedStatement pst1 = con.prepareStatement(query);
					ResultSet rs = pst1.executeQuery();
					rs.next();
					int numOfRows = rs.getInt(1);
		            System.out.print(numOfRows);
		            numOfRows += 1;
		            
		            email = forname.substring(0, 1) + surname + String.valueOf(numOfRows) +  universityDomain;
			}
			
			//check if username is already assigned to student
			String query = "SELECT * FROM Student WHERE username = ?";
			PreparedStatement pst3 = con.prepareStatement(query);
			pst3.setString(1, username);
			ResultSet rs = pst3.executeQuery();
			if (rs.next()) {
				infoBox("Student with given username already exist.", "Warning");
				con.close();
				return false;
			}
			
			//add student
			String insertStuQ = "INSERT INTO Student (codeOfDegree, username, title, surname, forname, email, personalTutor)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst2 = con.prepareStatement(insertStuQ);
			pst2.setString(1, degree.getCode());
			pst2.setString(2, username);
			pst2.setString(3, title);
			pst2.setString(4, surname);
			pst2.setString(5, forname);
			pst2.setString(6, email);
			pst2.setString(7, personalTutor);
			pst2.executeUpdate();
			
			
			
			con.close();
			return true;
			
		} catch (Exception exc) {
			
			infoBox("Student could not be added.", "Warning");
			exc.printStackTrace();
			return false;
		}

	}
	
	public boolean moduleRegister(Module module, Student student) {
		return false;
	}

	public boolean degreeRegister(Degree degree, Student student) {
		return false;
	}

	public boolean checkRegistration() {
		return false;
	}

	public boolean checkCredits() {
		return false;
	}

}
