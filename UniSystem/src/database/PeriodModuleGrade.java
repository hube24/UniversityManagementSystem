package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import encryption.BCrypt;
import users.Teacher;
import database.SqlDriver;

public class PeriodModuleGrade {
	private String moduleID;
	private int registerNumber;
	private String studyLevel;
	private float initialGrade;
	private float resitGrade;
	private float repeatGrade;
	private float finalGrade;
	private int finalCredit;
	private Connection conn;
	
	//create a PeriodModuleGrade
	public PeriodModuleGrade (String moduleID,int registerNumber,String studyLevel) {
		PeriodGrade(moduleID,registerNumber,studyLevel);
	}
	
	//create a PeriodModuleGrade with extra resit,repeat, final grade and final credit
	public PeriodModuleGrade(String moduleID, int registerNumber, String studyLevel, float initialGrade,
			float resitGrade, float repeatGrade, float finalGrade, int finalCredit) {
		create(moduleID,registerNumber,studyLevel,initialGrade,
				resitGrade,repeatGrade,finalGrade,finalCredit);
	}
	
	//assign type to variables
	public PeriodModuleGrade(String moduleID, int registerNumber, String studyLevel,float initGrade, float resitGrade, float repeatGrade) {
		create1( moduleID,  registerNumber,  studyLevel,initGrade, resitGrade, repeatGrade);
	}
	
	//get the grades
	private boolean create1 (String moduleID, int registerNumber, String studyLevel,float initGrade, float resitGrade, float repeatGrade) {
		this.initialGrade = initGrade;
		this.resitGrade = resitGrade;
		this.repeatGrade = repeatGrade;
		SqlDriver sqldriver = new SqlDriver();
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())){
		PreparedStatement stmt = null;
			//read in the data
			stmt = conn.prepareStatement("UPDATE moduleGrade SET initailGrade = '"+initGrade+"' ,"
					+ "resitGrade = '"+resitGrade+"' , repeatGrade = '"+repeatGrade+"'"
					+ "WHERE module = '"+moduleID+"' AND registerNumber = '"+registerNumber+"'"
							+ "AND studyLevel = '"+studyLevel+"';");
			stmt.execute();
			con.close(); }
		catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	//get grades and credit
	private boolean create(String moduleID, int registerNumber, String studyLevel, float initialGrade,
			float resitGrade, float repeatGrade, float finalGrade, int finalCredit) {
		this.moduleID = moduleID;
		this.registerNumber = registerNumber;
		this.studyLevel = studyLevel;
		this.initialGrade = initialGrade;
		this.resitGrade = resitGrade;
		this.repeatGrade = repeatGrade;
		this.finalGrade = finalGrade;
		this.finalCredit = finalCredit;
		SqlDriver sqldriver = new SqlDriver();
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())){
		PreparedStatement stmt = null;
			//update the data
			stmt = conn.prepareStatement("INSERT INTO moduleGrade (module, registerNumber, studyLevel, initailGrade, resitGrade,"
					+ "repeatGrade, finalGrade, finalCredit)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			
			//set types to different variables
			stmt.setString(1, moduleID);
			stmt.setInt(2, registerNumber);
			stmt.setString(3, studyLevel);
			stmt.setFloat(4, initialGrade);
			stmt.setFloat(5, resitGrade);
			stmt.setFloat(6, repeatGrade);
			stmt.setFloat(7, finalGrade);
			stmt.setInt(8, finalCredit);
			
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	//create periodGrade to get data
	private boolean PeriodGrade(String moduleID,int registerNumber,String studyLevel) {
		PreparedStatement stmt = null;
		SqlDriver sqldriver = new SqlDriver();
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())){
		{
			stmt = conn.prepareStatement("SELECT*FROM moduleGrade WHERE module = '"+moduleID+"' AND registerNumber = '"+registerNumber+"' AND studyLevel = '"+studyLevel+"';");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				this.moduleID = rs.getString("module");
				this.registerNumber = rs.getInt("registerNumber");
				this.studyLevel = rs.getString("studyLevel");
				this.initialGrade = rs.getFloat("initailGrade");
				this.resitGrade = rs.getFloat("resitGrade");
				this.repeatGrade = rs.getFloat("repeatGrade");
				this.finalGrade = rs.getFloat("finalGrade");
				this.finalCredit = rs.getInt("finalCredit");
			}
			con.close();
		}} catch(SQLException e) {
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	//return data
	public String getModuleID() {return this.moduleID;}
	public int getRegisterNumber() {return this.registerNumber;}
	public String getStudyLevel() {return this.studyLevel;}
	public float getInitialGrade() {return this.initialGrade;}
	public float getResitGrade() {return this.resitGrade;}
	public float getRepeatGrade() {return this.repeatGrade;}
	public float getFinalGrade() {return this.finalGrade;}
	public int getFinalCredit() {return this.finalCredit;}
	
}
