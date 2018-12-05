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

public class StudentStatusGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
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
		setBounds(100, 100, 692, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 650, 325);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {			
			},
			new String[] {
				"Unit", "Credits", "Title", "1st Sitting", "2nd Sitting" 
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 openStudentScreen(currSession);
			}
		});
		btnBack.setBounds(247, 368, 167, 35);
		contentPane.add(btnBack);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		List <String[]> moduleList = dbSelector.getRegisteredModules(student);
		System.out.println(moduleList);
		for( String[] row : moduleList) {
			model.addRow(new String[] {row[0], row[2], row[1], row[5], row[6]});
			
		}
	}
	
	protected void openStudentScreen( Session s ) {
		// TODO Auto-generated method stub
		StudentGUI frame = new StudentGUI(s);
		frame.setVisible(true);
		dispose();
	}
}
