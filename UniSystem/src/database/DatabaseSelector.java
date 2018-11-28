package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	
	public void deleteUser(String username) {
		delete("DELETE FROM Users WHERE username = ?", username);
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
	
}
