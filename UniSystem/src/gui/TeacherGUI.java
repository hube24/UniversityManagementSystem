package gui;

/* The main page of Teachers, after entering student registernumber, to the page which can modify student's information
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DatabaseSelector;
import database.Session;
import users.Student;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Button;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class TeacherGUI extends JFrame {
	
	Session currSession;
	private int registrationNum;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherGUI frame = new TeacherGUI(null);
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
	public TeacherGUI(Session s) {
		currSession = s;		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcomeTeacher = new JLabel("Welcome, Teacher");
		lblWelcomeTeacher.setBounds(10, 10, 171, 56);
		lblWelcomeTeacher.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblWelcomeTeacher);
		
		Button button = new Button("Log out");
		button.setBounds(628, 10, 78, 20);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to logout? ","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					
					try {
						currSession.endSession();
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					LoginScreen frame = new LoginScreen();
					frame.setVisible(true);
					dispose();
				}
			}
		});
		contentPane.add(button);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 48, 525, 2);
		contentPane.add(separator);
		
		Action open = new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        int regNum = (int)table.getModel().getValueAt(modelRow, 0);
		        
		        System.out.println(regNum);		        
   
		        openCheckGrades(currSession, regNum);
		    }

		};		 
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 696, 365);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {				
			},
			new String[] {
				"Registration Number", "Degree", "Name", "Level", "Check Student's Grades"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(112);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.setRowHeight(35);
		ButtonColumn buttonColumn = new ButtonColumn(table, open, 4);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		buttonColumn.setMnemonic(KeyEvent.VK_D);		
		
		
		List <String[]> studentssList = dbSelector.GetStudentsList();
		for( String[] row : studentssList) {			
			Integer regNum = Integer.valueOf(row[0]);
			String degree = row[1];
			String name = row[3] + " " + row[4] + " " + row[5];			
			Student student = new Student(regNum);
			String level = student.getCurrentLevel();
							
			model.addRow(new Object[] {regNum, degree, name, level, "Grades"});
			
		}
	}
	
	protected void openCheckGrades(Session s, int r) {		
		CheckGradesGUI frame = new CheckGradesGUI(s,r);
		frame.setVisible(true);		
		registrationNum = r;
		dispose();	
		
	}
}
