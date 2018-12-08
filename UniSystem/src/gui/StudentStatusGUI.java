package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;
import users.Student;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Font;

public class StudentStatusGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentStatusGUI frame = new StudentStatusGUI(null);
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
	public StudentStatusGUI(Session s) {
		setTitle("Student Status");
		Session currSession = s;
		Student student = new Student(s.getUsername());
		student.completeFromDB();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 661);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		//create a ScrollPane and modify its properites
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 45, 650, 262);
		contentPane.add(scrollPane);
		
		//creat a Table has Strings defined
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {			
			},
			new String[] {
				"Unit", "Credits", "Title", "1st Sitting", "2nd Sitting" 
			}
		));
		scrollPane.setViewportView(table);
		
		//create a Back Button and arrange its properties
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 openStudentScreen(currSession);
			}
		});
		btnBack.setBounds(256, 574, 167, 35);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 380, 650, 141);
		contentPane.add(scrollPane_1);
		
		//create a table with strings defined
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Level", "Period of Study", "Final Grade", "Result"
			}
		));
		table_1.getColumnModel().getColumn(1).setPreferredWidth(91);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(91);
		table_1.setRowHeight(30);
		scrollPane_1.setViewportView(table_1);
		
		DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();	
		
		//get currentPeriodOfStudy data
		String currPeriod = student.getCurrentPeriodOfStudy();
		
		System.out.println(currPeriod);

		
		List <String[]> previousModules = dbSelector.getPreviousGrades(student);
		for( String[] row : previousModules) {
			if(row[2]!=null) {
				model1.addRow(new String[] { row[3], row[1], row[2],"passed level"});
			}else {
				model1.addRow(new String[] { row[3], row[1], row[2],"failed level"});
			}			
		}
		
		JLabel lblPreviousLevels = new JLabel("Previous Levels:");
		lblPreviousLevels.setBounds(12, 351, 146, 14);
		contentPane.add(lblPreviousLevels);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 338, 650, 2);
		contentPane.add(separator);
		
		JLabel lblCurrentLevelGrades = new JLabel("Current level grades:");
		lblCurrentLevelGrades.setBounds(12, 20, 199, 14);
		contentPane.add(lblCurrentLevelGrades);
		
		String graduationGrade = student.getGraduationGrade();
		if(graduationGrade!=null) {
			JLabel lblFinalGrade = new JLabel("Graduation grade: " + graduationGrade);
			lblFinalGrade.setFont(new Font("Calibri", Font.PLAIN, 15));
			lblFinalGrade.setBounds(12, 541, 493, 22);
			contentPane.add(lblFinalGrade);
		}
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 561, 650, 2);
		contentPane.add(separator_1);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		//get modulesList
		List <String[]> moduleList = dbSelector.getRegisteredModules(student);
		System.out.println(moduleList);
		for( String[] row : moduleList) {
			//Assign module to each row
			model.addRow(new String[] {row[0], row[2], row[1], row[5], row[6]});
			
		}
	}
	
	//return the StudentGUI page
	protected void openStudentScreen( Session s ) {
		// TODO Auto-generated method stub
		StudentGUI frame = new StudentGUI(s);
		frame.setVisible(true);
		dispose();
	}
}
