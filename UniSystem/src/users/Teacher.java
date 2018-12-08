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
	
	public static void main(String[] args )
	{
		Student stud = new Student(100038);
		stud.completeFromDB();
		
		if(stud.getDegree().getName().endsWith("with a Year in Industry"))
		{
			System.out.println("PPPPP");
		}
	}
	
	
	public Teacher () {
	}
	//create an infoBox which provides infoMessage and titleBar
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean addGrade(int grade, int regNum, String moduleCode, String fstsnd )
	{
		System.out.println(" addGrade function. ");
		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			PreparedStatement pst1;
			if(fstsnd.equals("first"))
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
		
		if(lvl.equals("1") || lvl.equals("2")|| lvl.equals("3")) pass = 40;
		if(lvl.equals("4")) pass = 50;
		
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
	
	//returns different progression depending on levels
	String nextLevel(Student student)
	{
		System.out.println("nextLevel function");
		String level = student.getCurrentLevel();
		System.out.println("curr level = " + level);
		int numOfLevels = student.getDegree().getNumberOfLevels();
		System.out.println("num of levels for this degree " + numOfLevels);
		if(numOfLevels>1)
		{
			if(student.getDegree().getName().endsWith("with a Year in Industry")  && level.equals("2"))
			{
				return "P";
			}
			
			if(level.equals("1")) return "2";
			if(level.equals("2")) return "3";
			if(level.equals("P")) return "3";
			if(level.equals("3") && numOfLevels == 4) return "4";
			if(level.equals("3") && numOfLevels == 3) return "graduation";
			if(level.equals("4")&& numOfLevels == 4) return "graduation";
			
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
	
	void graduate(Student student, boolean bachelor)
	{

		SqlDriver sqldriver = new SqlDriver();
		
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			System.out.println("graduate function");
			//count avarage grade lvl 1 dont count
			int gradeLvl2 = -1;
			int gradeLvl3 = -1;
			int gradeLvl4 = -1;
			
			String getPeriodsGrades = "SELECT finalGrade, level FROM StudentStudyPeriod WHERE registrationNum = ?";
			PreparedStatement pst1 = con.prepareStatement(getPeriodsGrades);
			pst1.setInt(1, student.getRegistrationID());
			ResultSet rs = pst1.executeQuery();
			while(rs.next())
			{
				int grade = rs.getInt(1);
				String lvl = rs.getString(2);
				
				if(lvl.equals("2"))gradeLvl2 = grade;
				if(lvl.equals("3"))gradeLvl3 = grade;
				if(lvl.equals("4"))gradeLvl4 = grade;
				System.out.println("graduate from lvl " + lvl + " = " + grade);
			}
		
			//avarage mean
			int avup = 0;
			int avdown = 0;
			
			if(gradeLvl2!=(-1)) { avup += (gradeLvl2*1); avdown+=1; }
			if(gradeLvl3!=(-1)) { avup += (gradeLvl3*2); avdown+=2; }
			if(gradeLvl4!=(-1) && !bachelor) { avup += (gradeLvl4*2); avdown+=2; }
			
			double finalGrade = avup/avdown;
			System.out.println("graduation grade = " + finalGrade);
			
			//set graduation grade 

			String degreeName[] = student.getDegree().getName().split(" ",2);
			String degreeType = degreeName[0]; //MEng, MSc, BSc, BEng etc.
			String graduationGrade = graduationGradeState(finalGrade, degreeType);
			
			if(bachelor) {
				if(degreeType.equals("MSc")) degreeType = "BSc";
				if(degreeType.equals("MEng")) degreeType = "BEng";
			}
			
			System.out.println("graduation grade type = " + graduationGrade);
			
			String setGraduationGrade = "UPDATE Student SET graduationGrade = ? WHERE registrationNum = ?";
			PreparedStatement pst2 = con.prepareStatement(setGraduationGrade);
			
			pst2.setString(1,degreeType + " " + graduationGrade);
			pst2.setInt(2, student.getRegistrationID());
			pst2.executeUpdate();
			
			System.out.println("Graduation grade set");
			
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private String graduationGradeState(double finalGrade, String degreeType) {
		
		if(degreeType.equals("MSc")) {
			if(finalGrade < 39.5) return "fail";
			if(finalGrade >= 39.5 && finalGrade < 44.5) return "fail";
			if(finalGrade >= 44.5 && finalGrade < 49.5) return "fail";
			if(finalGrade >= 49.5 && finalGrade < 59.5) return "pass";
			if(finalGrade >= 59.5 && finalGrade < 69.5) return "merit";
			if(finalGrade >= 69.5) return "distinction";
		}
		
		if(degreeType.equals("BSc") || degreeType.equals("BEng")) {
			if(finalGrade < 39.5) return "fail";
			if(finalGrade >= 39.5 && finalGrade < 44.5) return "pass (non-honours)";
			if(finalGrade >= 44.5 && finalGrade < 49.5) return "third class";
			if(finalGrade >= 49.5 && finalGrade < 59.5) return "lower second";
			if(finalGrade >= 59.5 && finalGrade < 69.5) return "upper second";
			if(finalGrade >= 69.5) return "first class";
		}
		
		if(degreeType.equals("MComp") || degreeType.equals("MEng")) {
			if(finalGrade < 39.5) return "fail";
			if(finalGrade >= 39.5 && finalGrade < 44.5) return "fail";
			if(finalGrade >= 44.5 && finalGrade < 49.5) return "fail";
			if(finalGrade >= 49.5 && finalGrade < 59.5) return "lower second";
			if(finalGrade >= 59.5 && finalGrade < 69.5) return "upper second";
			if(finalGrade >= 69.5) return "first class";
		}
		return null;
	}

	void progressToNextLevel(Student student, int finalGrade)
	{
		System.out.println("progressToNextLevel function");

		String currLevel = student.getCurrentLevel();
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
			pst1.executeUpdate();
			System.out.println(" set finale grade:  " + finalGrade + " for period:" + currPeriod);
			
			if(!nextLevel.equals("graduation")) {
				String insertStuPerQ = "INSERT INTO StudentStudyPeriod (registrationNum, label, level)" + "VALUES (?, ?, ?)";
				PreparedStatement pst2 = con.prepareStatement(insertStuPerQ);
				pst2.setInt(1, student.getRegistrationID());
				pst2.setString(2, nextPeriod);
				pst2.setString(3, nextLevel);
				pst2.executeUpdate();
				
				System.out.println(" insert new studentStudyPeriod with level: " + nextLevel);
				
				Registrar registrar = new Registrar();
				
				Module[] coreModules = registrar.getCoreModules(student.getDegree(),nextLevel);
				
				for(Module m : coreModules)
				{
					if(!registrar.moduleRegister(m, student))
					{
						infoBox("Couldn't register student to core modules.", "Warning");
					}else {
						System.out.println(" registered studentfor module: " + m.getCodeOfModule());
					}
				}
			
			}else {
				//TODO
				System.out.println(" graduating student. ");
				con.close();
				graduate(student, false); 
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
		System.out.println(" repeatLevel function ");
		String currPeriod = student.getCurrentPeriodOfStudy();
		String currLevel = student.getCurrentLevel();
		SqlDriver sqldriver = new SqlDriver();
		
		
		if(currLevel.equals("4") && student.getDegree().getNumberOfLevels()==4)
		{
			infoBox("Student failed level 4 and has to graduate with bachelor degree.", "so close, sad");
			graduate(student, true);
			return;
		}
		
		if(!currPeriod.equals("A")) {
			
			char p = currPeriod.charAt(0);
			p--;
			String previousPeriod = String.valueOf(p);
			
			
			//check if level was alread repeated
			System.out.println(" checking if student already is repeating a level. ");
			try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
				String lastLevel = "SELECT level FROM StudentStudyPeriod WHERE label = ? AND registrationNum = ?";
				PreparedStatement pst1 = con.prepareStatement(lastLevel);
				pst1.setString(1, previousPeriod);
				pst1.setInt(2, student.getRegistrationID());
				ResultSet rs = pst1.executeQuery();
				if(rs.next()) {
					if(rs.getString(1).equals(currLevel))
					{
						//fail to death
						failToProgress(student);
						con.close();
						return;
					}else {
						infoBox("Student failed level, and have to repeat failed modules in the next period of study.", "Sad.");
					}
				}
				con.close();
				
				} catch (Exception exc) {
					exc.printStackTrace();
				}
		}
		
		//clear failed grades
		System.out.println(" clearing failed modules ");
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {

		for(Module module : failedModules) {
			String clearGrades = "UPDATE ModuleRegistration SET firstGrade = NULL, secondGrade = NULL WHERE codeOfModule = ? AND registrationNum = ?";
			PreparedStatement pst1 = con.prepareStatement(clearGrades);
			pst1.setString(1,module.getCodeOfModule());
			pst1.setInt(2, student.getRegistrationID());
			pst1.executeUpdate();
			
			System.out.println(" module grade cleared " + module.getCodeOfModule());
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
			System.out.println(" registered student to same level. ");
			
			con.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	
	private void failToProgress(Student student) {
		SqlDriver sqldriver = new SqlDriver();
		try (Connection con = DriverManager.getConnection(sqldriver.getDB(), sqldriver.getDBuser(), sqldriver.getDBpassword())) {
			String setPeriodFinalGrade = "UPDATE Student SET graduationGrade = 'failed to progress' WHERE registrationNum = ?";
			PreparedStatement pst1 = con.prepareStatement(setPeriodFinalGrade);
			pst1.setInt(1, student.getRegistrationID());
			pst1.executeUpdate();
			System.out.println(" student failed to progress. ");
			
			con.close();
			infoBox("Student unfortunately failed to progress", "Sad.");
		} catch (Exception exc) {
			
			exc.printStackTrace();
		}		
	}

	// return if student is able to progress, count final grade for period of study
	public boolean ableToProgress(Student student)
	{
		// 1 - check all modules
		// 2 - count number of obtained credits
		// 3 - count final grade
		// 4 - set final grade for period of study
		// 5 - return if student is able to progress
		System.out.println("start ableToProgress");
		
		String level = student.getCurrentLevel();
		int modulesPassed = 0;
		int modulesFailed = 0;
		int concededPasses = 0;
		int creditsSum = 0;
		
		System.out.println("student current level = " + level);
		
		if(level.equals("P")) {
			
			progressToNextLevel(student, 100);
			return true;
		}
		
		
		int pass=0;		
		if(level.equals("1") || level.equals("2") || level.equals("3")) pass = 40;
		if(level.equals("4")) pass = 50;
		
		System.out.println("Pass bare = " + pass);
		
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
			
			System.out.print("Check module " + codeOfModule);
			
			if(row[5]==null) {
				infoBox("Module "+ codeOfModule + " is not marked yet, please set grades for this module.","Warning");
				return false;
			}
			
			
			int firstGrade =0;
			int secondGrade =0;
			if(row[5]!=null)firstGrade = Integer.valueOf(row[5]);
			if(row[6]!=null)secondGrade = Integer.valueOf(row[6]);
			int grade = Math.max(firstGrade, Math.min(pass, secondGrade));
			
			System.out.print("   module fstGrade = " + firstGrade + " second " + secondGrade);
			System.out.print( "  module final = " + grade);
			Module module = new Module(codeOfModule,name,credits);
			
			//returns creditsSum, average grade, mean grade if passed, conceded pass or fail
			if(moduleGradeState(grade, level).equals("P")) 
			{
				modulesPassed++;
				creditsSum+=credits;
				avup += credits*grade;
				avdown += credits;
				
				System.out.println(" - P");
				
			}
			if(moduleGradeState(grade, level).equals("C")) 
			{
				concededPasses++;
				avup += credits*grade;
				avdown += credits;
				failedModules.add(module);
				System.out.println(" - C");
			}
			if(moduleGradeState(grade, level).equals("F")) 
			{
				modulesFailed++;
				avup += credits*grade;
				avdown += credits;
				failedModules.add(module);
				System.out.println(" - F");
			}
			
		}
		
		int creditsToObtain = (level.equals("4"))?180:120;
		
		int meanGrade = (int)(avup/avdown);
		System.out.println(" mean grade : " + meanGrade);
		System.out.println("concede passes = " + concededPasses + " cred " + (creditsToObtain - creditsSum));
		
		if(level.equals("4")) {
			if(creditsToObtain - creditsSum <= 15 && modulesFailed==0 && concededPasses<=1 && meanGrade>=pass)
			{
				//passed
				System.out.println(" level passed. ");
				progressToNextLevel(student, meanGrade);
				return true;
			}else {
				//failed
				System.out.println(" level failed. ");
				repeatLevel(student, failedModules);
				return false;
			}
		}else {
			if(creditsToObtain - creditsSum <= 20 && modulesFailed==0  && concededPasses<=1 && meanGrade>=pass)
			{
				//passed
				System.out.println(" level passed. ");
				progressToNextLevel(student, meanGrade);
				return true;
			}else {
				//failed
				System.out.println(" level failed. ");
				repeatLevel(student, failedModules);
				return false;
			}
		}
	}
	
}
