package gui;

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
		Session currSession = s;
		student = stud;
		student.completeFromDB();
		Teacher teacher = new Teacher();
		DatabaseSelector dbSelector = new DatabaseSelector();
		List<String> gradeList = dbSelector.getStudentGradesList(student, code);
		
		first = "";
		seccond = "";
		
		if(gradeList.iterator().hasNext()) {
			 first= gradeList.get(0);
			 seccond = gradeList.get(1);			 
		}
		
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
		
		txtSeccondgrade = new JTextField();
		txtSeccondgrade.setText(seccond);
		txtSeccondgrade.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtSeccondgrade.setColumns(10);
		if(first != null) {
			txtFirstgrade.setEditable(false);			
		}else {
			txtSeccondgrade.setEditable(false);
		}
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openCheckGrades(currSession, student.getRegistrationID());
			}
		});
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstGrade = txtFirstgrade.getText();
				String seccondGrade = txtSeccondgrade.getText();
				System.out.println("11111---"+firstGrade+"---");				
				System.out.println(firstGrade.equals(""));
				
				if(txtFirstgrade.isEditable()) {
					teacher.addGrade(Integer.parseInt(firstGrade), student.getRegistrationID(), code, "first");
				}else {
					teacher.addGrade(Integer.parseInt(seccondGrade), student.getRegistrationID(), code, "seccond" );
				}
				openCheckGrades(currSession, student.getRegistrationID());
			}
		});
		
		JSeparator separator = new JSeparator();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(49)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGap(156)
							.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblModule)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblFirstGrade)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtFirstgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblResitGrade)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtSeccondgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(58, Short.MAX_VALUE))
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
						.addComponent(txtFirstgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblResitGrade)
						.addComponent(txtSeccondgrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(24))
		);
		contentPane.setLayout(gl_contentPane);
	}
	protected void openCheckGrades(Session s, int r) {		
		CheckGradesGUI frame = new CheckGradesGUI(s,r);
		frame.setVisible(true);				
		dispose();	
		
	}
}
