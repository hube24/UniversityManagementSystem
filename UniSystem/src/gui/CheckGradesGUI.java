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

public class CheckGradesGUI extends JFrame {
	
	private String codeOfModule;
	private Student student;
	private JPanel contentPane;
	private static Student st;
	private static String periodOfStudy;
	private JTable table;
	private JTable table_1;

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

	/**
	 * Create the frame.
	 */
	public CheckGradesGUI(Session s, int regNum) {
		
		Session currSession = s;
		student = new Student(regNum);
		student.completeFromDB();
		Teacher teacher = new Teacher();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1218, 560);
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
		scrollPane.setBounds(10, 105, 794, 275);
		contentPane.add(scrollPane);
		
		//create a table with strings defined
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code of the Module", "Name of the Module", "Initial Grade", "Resit Grade", "Final Grade", "Add/Update Grade"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		//get data in the columns and set width
		table.getColumnModel().getColumn(0).setPreferredWidth(107);
		table.getColumnModel().getColumn(1).setPreferredWidth(109);
		table.getColumnModel().getColumn(5).setPreferredWidth(127);
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
		
		ButtonColumn buttonColumn = new ButtonColumn(table, open, 5);
		
		JLabel lblDegree = new JLabel("");
		lblDegree.setText(student.getDegree().getCode() + " - " + student.getDegree().getName());
		lblDegree.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDegree.setBounds(10, 40, 307, 35);
		contentPane.add(lblDegree);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(836, 105, 356, 275);
		contentPane.add(scrollPane_1);
		
		//create a table with strings defined
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code of Module", "Level", "Period of study", "Final Grade"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(91);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(91);
		scrollPane_1.setViewportView(table_1);
		
		DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();
		
		//I don't know how to pass period of study to this table.
		
		
		List <String[]> finalList = dbSelector.getPreviousModules(student);	
		//I don't know how to connect both loops.
		/*for( String[] row : finalList) {			
				model1.addRow(new String[] {row[0], null, null, null}); 
			}*/
		
		//get currentPeriodOfStudy data
		String currPeriod = student.getCurrentPeriodOfStudy();
		if(currPeriod!="A") { 					
			char p = currPeriod.charAt(0);
			p--;
			currPeriod = String.valueOf(p);
		}
		System.out.println(currPeriod);
		List <String[]> previousModules = dbSelector.getPreviousGrades(student, currPeriod);
		System.out.println("Jestem"+previousModules);
		for( String[] row : previousModules) {			
			model1.addRow(new String[] {row[0], row[3], currPeriod, row[2]}); 
		}
		
		//create a Back Button and arrange its position
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openTeacher(currSession);
			}
		});
		btnNewButton.setBounds(843, 415, 159, 41);
		contentPane.add(btnNewButton);
		
		//create an Overall Grade label and arrange its position
		JButton btnNewButton_1 = new JButton("Progress student ");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Teacher teacher = new Teacher();
				if(teacher.ableToProgress(student)) {
					infoBox("Student progressed sucessfully","Done.");
				}
			}
		});
		btnNewButton_1.setBounds(386, 415, 125, 41);
		contentPane.add(btnNewButton_1);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
	//	System.out.println(teacher.ableToProgress(student));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//set requirements that needed to update the Grade
		List <String[]> moduleList = dbSelector.getRegisteredModules(student);
		System.out.println(moduleList);
		for( String[] row : moduleList) {
			if(row[6]==null) {
				model.addRow(new String[] {row[0], row[1], row[5], row[6],row[5], "Add/Update Grade"}); 
			}else if(Integer.valueOf(row[6])>=40){			
				model.addRow(new String[] {row[0], row[1], row[5], row[6],"40", "Add/Update Grade"});  
			}else {
				model.addRow(new String[] {row[0], row[1], row[5], row[6],"Failed", "Add/Update Grade"}); 
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
}
