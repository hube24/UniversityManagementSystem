package database;

import java.sql.*;

import encryption.BCrypt;


public class UserAuthorization extends SqlDriver {

	public int getAuthorization(String username, String password)
	{
		try(Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)){
			System.out.println("connected");
			String query = "SELECT * FROM Users WHERE username = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,username);
			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				String passwordHash = rs.getString("password");
				if(BCrypt.checkpw(password, passwordHash)){
					
					//if username and password are correct
					String ut = rs.getString("access");
					System.out.print(ut);
					if(ut.equals("admin")) {
						//if user type is admin
						con.close();
						return 1;
					}
				
					if(ut.equals("student")) {
						//if user type is student
						con.close();
						return 2;
					}
				}else{
					//wrong password
				}
			}else {
				//no username matched
				con.close();
				return 0;
			}
			
		}catch(Exception exc){
			exc.printStackTrace();
			return  0;
		}
		return 0;
	}
}
