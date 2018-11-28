package database;

import java.sql.*;

public class SqlDriver {

	final String DB="jdbc:mysql://stusql.dcs.shef.ac.uk/team037";
	final String DBuser = "team037";
	final String DBpassword = "02b8cefc";
	
	
	public SqlDriver() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDB() {
		return DB;
	}
	
	public String getDBpassword() {
		return DBpassword;
	}
	
	public String getDBuser() {
		return DBuser;
	}
	
}
