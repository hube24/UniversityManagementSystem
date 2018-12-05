package users;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import database.DatabaseSelector;
import database.SqlDriver;
import university.Module;

public class Teacher extends User {
	
	String title;
	String surname;
	String forename;
	
	public Teacher () {
		/*title = t;
		surname = s;
		forename = f;*/
	}
	
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	

	public String getTitle() {
		return title;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getForename() {
		return forename;
	}
	
	public boolean addGrade(int grade, int regNum, String moduleCode, String fstsnd )
	{
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1;
			if(fstsnd == "first")
				pst1= con.prepareStatement("UPDATE ModuleRegistration SET firstGrade = ? WHERE codeOfModule = ? AND registrationNum = ?"); //toedit
			else
				pst1= con.prepareStatement("UPDATE ModuleRegistration SET secondGrade = ? WHERE codeOfModule = ? AND registrationNum = ?"); //toedit
			
			pst1.setInt(1, grade);
			pst1.setString(2, moduleCode);
			pst1.setInt(3, regNum);
			pst1.executeUpdate();
			con.close();
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
		
	}
	
	//returns P if passed, C if conceded pass, F if fail
	String moduleGradeState(int grade, String lvl)
	{
		int pass=0;
		
		if(lvl == "1" || lvl == "2" || lvl == "3") pass = 40;
		if(lvl == "4") pass = 50;
		
		if(grade>=pass)return "P";
		if(grade>=(pass-10))return "C";
		return "F";
	}
	
	//returns P if passed, C if conceded pass, F if fail
	String modulePassed(Student student, Module module)
	{
		String level = student.getCurrentLevel();
		
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1 = con.prepareStatement("SELECT firstGrade, secondGrade FROM ModuleRegistration"
					+ "WHERE registrationNum = ? AND codeOfModule = ?");
			
			pst1.setInt(1, student.getRegistrationID());
			pst1.setString(2, module.getCodeOfModule());
			ResultSet rs = pst1.executeQuery();
			if(rs.next())
			{
				int fstGrade = rs.getInt(1);
				
				if(rs.getObject(2)==null) {
					return moduleGradeState(fstGrade, level);
				}else {
					int sndGrade = rs.getInt(2);
					return moduleGradeState(sndGrade, level);
				}
			}
			con.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			return "";
		}
		return "";
	}
	
	
	String nextLevel(Student student)
	{
		String level = student.getCurrentLevel();
		int numOfLevels = student.getDegree().getNumberOfLevels();
		if(numOfLevels>1)
		{
			if(student.getDegree().getName().endsWith("with a Year of Industry")  && level == "2")
			{
				return "P";
			}
			
			if(level == "1") return "2";
			if(level == "2") return "3";
			if(level == "3" && numOfLevels == 4) return "4";
			if(level == "3" && numOfLevels == 3) return "graduation";
			if(level == "4" && numOfLevels == 4) return "graduation";
			
		}else {
			return "graduation";
		}
		return "";
	}
	
	String nextPeriod(String period)
	{
		char p = period.charAt(0);
		p++;
		return String.valueOf(p);
	}
	
	/*
	boolean setGraduationGrade(Student student)
	{

		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			
			
			String setGraduationGrade = "UPDATE Student SET graduationGrade = ? WHERE registrationNum = ?";
			PreparedStatement pst3 = con.prepareStatement(setGraduationGrade);
			
			String degreeName[] = student.getDegree().getName().split(" ",2);
			String degreeType = degreeName[0]; //MEng, MSc, BSc, BEng etc.
			String graduationGrade = graduationGradeState(finalGrade, degreeType);
			
			pst3.setString(1,finalGrade);
			pst3.setString(2, currPeriod);
			pst3.setInt(3, student.getRegistrationID());
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	*/
	void progressToNextLevel(Student student, int finalGrade)
	{
		
		String nextLevel = nextLevel(student);
		String currPeriod = student.getCurrentPeriodOfStudy();
		String nextPeriod = nextPeriod(currPeriod);
		
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			//set current period final grade
			String setPeriodFinalGrade = "UPDATE StudentStudyPeriod SET finalGrade = ? WHERE label = ? AND registrationNum = ?";
			PreparedStatement pst1 = con.prepareStatement(setPeriodFinalGrade);
			pst1.setInt(1,finalGrade);
			pst1.setString(2, currPeriod);
			pst1.setInt(3, student.getRegistrationID());
			
			if(nextLevel != "graduation") {
				String insertStuPerQ = "INSERT INTO StudentStudyPeriod (registrationNum, label, level)" + "VALUES (?, ?, ?)";
				PreparedStatement pst2 = con.prepareStatement(insertStuPerQ);
				pst2.setInt(1, student.getRegistrationID());
				pst2.setString(2, nextPeriod);
				pst2.setString(3, nextLevel);
				pst2.executeUpdate();
				
				//TODO
				//register core modules for the next level
			}else {
				//TODO
				//set graduation grade 
			}
			con.close();
			
		} catch (Exception exc) {
			
			infoBox("Student could not be progressed.", "Warning");
			exc.printStackTrace();
		}
				
	}
	
	void repeatLevel(Student student, List<Module> failedModules)
	{
		//check if not repeating
		
		String currPeriod = student.getCurrentPeriodOfStudy();
		String currLevel = student.getCurrentLevel();
		SqlDriver sqldriver = new SqlDriver();
		
		if(currPeriod!="A") {
			
			char p = currPeriod.charAt(0);
			p--;
			String previousPeriod = String.valueOf(p);
			
			
			
			try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
				String lastLevel = "SELECT level FROM StudentStudyPeriod WHERE label = ? AND registrationNum = ?";
				PreparedStatement pst1 = con.prepareStatement(lastLevel);
				pst1.setString(1, previousPeriod);
				pst1.setInt(2, student.getRegistrationID());
				ResultSet rs = pst1.executeQuery();
				if(rs.next()) {
					if(rs.getString(1) == currLevel)
					{
						//fail to death
						failToProgress(student);
					}
				}
				con.close();
				
				} catch (Exception exc) {
					exc.printStackTrace();
				}
		}
		
		//clear failed grades
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

		for(Module module : failedModules) {
			String clearGrades = "UPDATE ModuleRegistration SET firstGrade = NULL, secondGrade = NULL WHERE codeOfModule = ? AND registrationNum = ?";
			PreparedStatement pst1 = con.prepareStatement(clearGrades);
			pst1.setString(1,module.getCodeOfModule());
			pst1.setInt(2, student.getRegistrationID());
			pst1.executeUpdate();
		}
			con.close();
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		//register new period of study
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

			String insertStuPerQ = "INSERT INTO StudentStudyPeriod (registrationNum, label, level)" + "VALUES (?, ?, ?)";
			PreparedStatement pst2 = con.prepareStatement(insertStuPerQ);
			pst2.setInt(1, student.getRegistrationID());
			pst2.setString(2, nextPeriod(currPeriod));
			pst2.setString(3, currLevel);
			pst2.executeUpdate();

			con.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	
	private void failToProgress(Student student) {
		SqlDriver sqldriver = new SqlDriver();
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			String setPeriodFinalGrade = "UPDATE Student SET graduationGrade = 'failed' WHERE registrationNum = ?";
			PreparedStatement pst1 = con.prepareStatement(setPeriodFinalGrade);
			pst1.setInt(1, student.getRegistrationID());
			con.close();
			infoBox("Student unfortunately failed to progress", "Sad.");
		} catch (Exception exc) {
			
			exc.printStackTrace();
		}		
	}

	// return if student is able to progress, count final grade for period of study
	boolean ableToProgress(Student student)
	{
		// 1 - check all modules
		// 2 - count number of obtained credits
		// 3 - count final grade
		// 4 - set final grade for period of study
		// 5 - return if student is able to progress
		String level = student.getCurrentLevel();
		int modulesPassed = 0;
		int modulesFailed = 0;
		int concededPasses = 0;
		int creditsSum = 0;
		
		int pass=0;		
		if(level == "1" || level == "2" || level == "3") pass = 40;
		if(level == "4") pass = 50;
		
		//mean grade
		int avup=0;
		int avdown=0;
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		List<String[]> modulesList = dbSelector.getRegisteredModules(student);
		List<Module> failedModules = new ArrayList();
		for(String[] row : modulesList)
		{
			String codeOfModule = row[0];
			String name = row[1];
			int credits = Integer.valueOf(row[2]);
			int registrationNum = Integer.valueOf(row[3]);
			
			/*
			if(row[5]==null && row[6] == null) {
				infoBox("Module "+ codeOfModule + " is not marked yet, please set grades for this module.","Warning");
				return false;
			}
			*/
			
			int firstGrade =0;
			int secondGrade =0;
			if(row[5]!=null)firstGrade = Integer.valueOf(row[5]);
			if(row[6]!=null)secondGrade = Integer.valueOf(row[6]);
			int grade = Math.max(firstGrade, Math.min(pass, secondGrade));
			
			Module module = new Module(codeOfModule,name,credits);
			
			if(moduleGradeState(grade, level)=="P") 
			{
				modulesPassed++;
				creditsSum+=credits;
				avup += credits*grade;
				avdown += credits;
			}
			if(moduleGradeState(grade, level)=="C") 
			{
				concededPasses++;
				avup += credits*grade;
				avdown += credits;
				failedModules.add(module);
			}
			if(moduleGradeState(grade, level)=="F") 
			{
				modulesFailed++;
				failedModules.add(module);
			}
			
		}
		
		int creditsToObtain = (level=="4")?180:120;
		
		int meanGrade = (int)(avup/avdown);
		
		if(level == "4") {
			if(creditsToObtain - creditsSum < 15 && modulesFailed==0 && concededPasses<=1 && meanGrade>=pass)
			{
				//passed
				progressToNextLevel(student, meanGrade);
				return true;
			}else {
				//failed
				repeatLevel(student, failedModules);
				return false;
			}
		}else {
			if(creditsToObtain - creditsSum < 20 && modulesFailed==0  && concededPasses<=1 && meanGrade>=pass)
			{
				//passed
				progressToNextLevel(student, meanGrade);
				return true;
			}else {
				//failed
				repeatLevel(student, failedModules);
				return false;
			}
		}
	}
	
}
