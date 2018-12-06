package gui;

/* A page to check the student's status(credits,final grade) each year and get the mean grade
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import users.Student;
import users.Teacher;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class CheckGradesGUI extends JFrame {
	
	private String codeOfModule;
	private Student student;
	private JPanel contentPane;
	private static Student st;
	private static String periodOfStudy;
	private JTable table;
	private JTable table_1;
	private boolean gradesFilled;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {			
				public void run() {
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					       if ("Nimbus".equals(info.getName())) {
					           UIManager.setLookAndFeel(info.getClassName());
					           break;
					        }
					    }
					} catch (Exception e) {
					}
				try {
					CheckGradesGUI frame = new CheckGradesGUI(null, -1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Create the frame.
	 */
	public CheckGradesGUI(Session s, int regNum) {
		
		Session currSession = s;
		student = new Student(regNum);
		student.completeFromDB();
		Teacher teacher = new Teacher();
		gradesFilled = true;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1307, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		JLabel lblRegistrationNumber = new JLabel("Registration Number: "+ student.getRegistrationID());
		lblRegistrationNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRegistrationNumber.setBounds(10, 11, 410, 41);
		contentPane.add(lblRegistrationNumber);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 105, 864, 275);
		contentPane.add(scrollPane);
		
		//create a table with strings defined
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code of the Module", "Name of the Module", "Initial Grade", "Resit Grade", "Result", "Final Grade", "Add/Update Grade"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(107);
		table.getColumnModel().getColumn(1).setPreferredWidth(109);
		table.getColumnModel().getColumn(5).setPreferredWidth(73);
		table.getColumnModel().getColumn(6).setPreferredWidth(108);
		table.setRowHeight(35);
		
		Action open = new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {	
				
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        String code = (String)table.getModel().getValueAt(modelRow, 0); 
		        openAddGrade(currSession, code, student);
		    }
		};
		
		ButtonColumn buttonColumn = new ButtonColumn(table, open, 6);
		
		JLabel lblDegree = new JLabel("");
		lblDegree.setText(student.getDegree().getCode() + " - " + student.getDegree().getName());
		lblDegree.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDegree.setBounds(10, 40, 307, 35);
		contentPane.add(lblDegree);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(909, 105, 372, 275);
		contentPane.add(scrollPane_1);
		
		//create a table with strings defined
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Level", "Period of Study", "Final Result", "Grade"
			}
		));
		table_1.getColumnModel().getColumn(1).setPreferredWidth(91);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(91);
		scrollPane_1.setViewportView(table_1);
		
		DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();	
		
		//get currentPeriodOfStudy data
		String currPeriod = student.getCurrentPeriodOfStudy();
		
		System.out.println(currPeriod);

		
		List <String[]> previousModules = dbSelector.getPreviousGrades(student);
		for( String[] row : previousModules) {
			if(row[2]!=null) {
				model1.addRow(new String[] { row[3], row[1], row[2],getGrade(student, Integer.valueOf(row[2]))});
			}else {
				model1.addRow(new String[] { row[3], row[1], row[2],"fail"});
			}			
		}	
		
		//create a Back Button and arrange its position
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openTeacher(currSession);
			}
		});
		btnNewButton.setBounds(1021, 415, 159, 41);
		contentPane.add(btnNewButton);
		
		//create an Overall Grade label and arrange its position
		JButton btnNewButton_1 = new JButton("Progress student ");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String level = student.getCurrentLevel();
				int registeredCredits = student.getRegisteredCredits();
				int rightCredits = (level.equals("4"))?180:120;
				if(registeredCredits != rightCredits) {
					infoBox("Student is not registered for required number of modules, please contact a Registrar.","Warning.");
					return;
				}
				
				if(!gradesFilled) {
					infoBox("Please set grade(s) for every module","Warning");
					return;
				}				
				
				Teacher teacher = new Teacher();
				if(teacher.ableToProgress(student)) {
					infoBox("Student progressed sucessfully","Done.");
				}
			}
		});
		btnNewButton_1.setBounds(341, 415, 159, 41);
		contentPane.add(btnNewButton_1);

		JLabel lblCurrentModules = new JLabel("Current modules:");
		lblCurrentModules.setBounds(10, 86, 184, 14);
		contentPane.add(lblCurrentModules);
		
		JLabel lblPreviousPeriodsOf = new JLabel("Previous Periods of Study");
		lblPreviousPeriodsOf.setBounds(998, 86, 194, 14);
		contentPane.add(lblPreviousPeriodsOf);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 73, 1182, 2);
		contentPane.add(separator);
		
		JLabel lblCurrentLevel = new JLabel("Current Level: ");
		lblCurrentLevel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCurrentLevel.setBounds(304, 27, 138, 14);
		contentPane.add(lblCurrentLevel);
		
		JLabel levelLabel = new JLabel("1");
		levelLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		levelLabel.setBounds(444, 27, 138, 14);
		contentPane.add(levelLabel);

		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		levelLabel.setText(student.getCurrentLevel());
		
	//	System.out.println(teacher.ableToProgress(student));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//set requirements that needed to update the Grade
		List <String[]> moduleList = dbSelector.getRegisteredModules(student);
		System.out.println(moduleList);
		
			for( String[] row : moduleList) {
				if(row[6]==null && row[5]==null) {
					model.addRow(new String[] {row[0], row[1], row[5], row[6],row[5],row[5], "Add/Update Grade"}); 
				}if(row[6]==null && row[5]!=null){			
					model.addRow(new String[] {row[0], row[1], row[5], row[6],row[5], getGrade(student, Integer.valueOf(row[5])), "Add/Update Grade"});  
				}if(row[6]!=null && student.getDegree().getNumberOfLevels()==3){
					model.addRow(new String[] {row[0], row[1], row[5], row[6],"40",getGrade(student, Integer.valueOf(40)), "Add/Update Grade"}); 
				}if(row[6]!=null && student.getDegree().getNumberOfLevels()!=3){
					model.addRow(new String[] {row[0], row[1], row[5], row[6],"50",getGrade(student, Integer.valueOf(50)), "Add/Update Grade"}); 
				}
				 
			}			
	}
	
	//return to the Add GradeGUI page
	public void openAddGrade(Session s, String code, Student student) {
		AddGradeGUI frame = new AddGradeGUI(s,code, student);
		frame.setVisible(true);		
		codeOfModule = code;	
		dispose();
	}
	
	public void openTeacher(Session s) {
		TeacherGUI frame =  new TeacherGUI(s);
		frame.setVisible(true);
		dispose();
	}
	public String getGrade(Student s, int i) {
		if(student.getDegree().getNumberOfLevels()==3) {
			if(i<39.5) {
				return "fail";
			}else if(i<=44.4){
				return "pass (no-honours)";
			}else if (i<=49.4) {
				return "third class";
			}else if(i<=59.4){
				return "lower second";
			}else if(i<=69.4){
				return "upper second";
			}else {
				return "first class";
			}
		}else if (student.getDegree().getNumberOfLevels()==4) {
			if (i<=49.4) {
				return "fail";
			}else if(i<=59.4){
				return "lower second";
			}else if(i<=69.4){
				return "upper second";
			}else {
				return "first class";
			}
		}else {
			if (i<=49.4) {
				return "fail";
			}else if(i<=59.4){
				return "pass";
			}else if(i<=69.4){
				return "merit";
			}else {
				return "distinction";
			}
		}
	}
}
