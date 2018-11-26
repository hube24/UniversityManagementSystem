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
	
	public List<String[]> GetDepartmentList()
	{
		try (Connection con = DriverManager.getConnection(DB, DBuser, DBpassword)) {

			//get all departments
			String query = "SELECT * FROM Department";
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
}
