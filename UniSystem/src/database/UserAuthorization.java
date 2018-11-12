package database;

import java.sql.*;


public class UserAuthorization extends SqlDriver {

	public int getAuthorization(String username, String password)
	{
		try(Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)){
			System.out.println("connected");
			String query = "SELECT * FROM users WHERE username = ? and password = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,username);
			pst.setString(2,password);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
			{
				String ut = rs.getString("usertype");
				System.out.print(ut);
				//if username and password correct
				if(ut.equals("admin")) {
					//if user type is admin
					return 1;
				}
			
				if(ut.equals("student")) {
					//if user type is student
					return 2;
				}
		
			}else {
				//no username and password matched
				return 0;
			}
			
		}catch(Exception exc){
			exc.printStackTrace();
			return  0;
		}
		return 0;
	}
}
