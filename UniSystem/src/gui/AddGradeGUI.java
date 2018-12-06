package gui;
/*
This file aims to edit the initial Grade and Resit Grade of Students
*/
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DatabaseSelector;
import database.Session;
import users.Student;
import users.Teacher;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddGradeGUI extends JFrame {
	Student student;
	private String first;
	private String seccond;
	private JPanel contentPane;
	private JTextField txtFirstgrade;
	private JTextField txtSeccondgrade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddGradeGUI frame = new AddGradeGUI(null, null, null);
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
	public AddGradeGUI(Session s, String code, Student stud) {
		setTitle("Edit Grade Page");
		Session currSession = s;
		student = stud;
		student.completeFromDB();
		Teacher teacher = new Teacher();
		DatabaseSelector dbSelector = new DatabaseSelector();
		List<String> gradeList = dbSelector.getStudentGradesList(student, code);
		
		//Update the first and second grade from database
		first = "";
		seccond = "";
		
		if(gradeList.iterator().hasNext()) {
			 first= gradeList.get(0);
			 seccond = gradeList.get(1);			 
		}
		
		//arrange positions of elements
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblModule = new JLabel("Module: ");
		lblModule.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		
		JLabel lblFirstGrade = new JLabel("First Grade:");
		lblFirstGrade.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblResitGrade = new JLabel("Resit Grade");
		lblResitGrade.setFont(new Font("Tahoma", Font.PLAIN, 18));

		txtFirstgrade = new JTextField();		
		txtFirstgrade.setText(first);
		
		txtFirstgrade.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtFirstgrade.setColumns(10);
		
		//Set editable of firstGrade and secondGrade if either of them is edited
		txtSeccondgrade = new JTextField();
		txtSeccondgrade.setText(seccond);
		txtSeccondgrade.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtSeccondgrade.setColumns(10);
		
		//create a Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openCheckGrades(currSession, student.getRegistrationID());
			}
		});
		
		//create a Submit Button and update the grades to the database
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstGrade = txtFirstgrade.getText();				
				try  
				  {  
					teacher.addGrade(Integer.parseInt(firstGrade), student.getRegistrationID(), code, "first"); 
				  }  
				  catch(NumberFormatException nfe)  
				  {  
					  infoBox("That is not a number", "Warning");
				  } 				
				openCheckGrades(currSession, student.getRegistrationID());		   
				
			}
		});
		
		//arrange the positions of elements
		JSeparator separator = new JSeparator();
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String seccondGrade = txtSeccondgrade.getText();
				try  
				  {  
					teacher.addGrade(Integer.parseInt(seccondGrade), student.getRegistrationID(), code, ""); 
				  }  
				  catch(NumberFormatException nfe)  
				  {  
					  infoBox("That is not a number", "Warning");
				  } 				
				openCheckGrades(currSession, student.getRegistrationID());	
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(49)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblModule)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblResitGrade)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtSeccondgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton, 0, 0, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblFirstGrade)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtFirstgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 1, Short.MAX_VALUE)))
					.addContainerGap(56, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblModule)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFirstGrade)
						.addComponent(txtFirstgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblResitGrade)
						.addComponent(txtSeccondgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(24))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	//return to the CheckGrades page
	protected void openCheckGrades(Session s, int r) {		
		CheckGradesGUI frame = new CheckGradesGUI(s,r);
		frame.setVisible(true);				
		dispose();	
		
	}
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}	
}
