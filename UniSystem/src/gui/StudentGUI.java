package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.Session;
import users.Student;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import java.awt.Color;

public class StudentGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentGUI frame = new StudentGUI(null);
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
	public StudentGUI(Session s) {
		Session currSession = s;
		Student student = new Student(s.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 548);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTitle = new JLabel(student.getTitle());
		lblTitle.setFont(new Font("Nirmala UI", Font.PLAIN, 30));
		
		JLabel lblSurnameForename = new JLabel(student.getForename() +" "+ student.getSurname());
		lblSurnameForename.setFont(new Font("Nirmala UI", Font.PLAIN, 30));
		
		JLabel lblReg = new JLabel("Registration Number:");
		lblReg.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JLabel lblEm = new JLabel("Email:");
		lblEm.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JLabel lblDeg = new JLabel("Degree:");
		lblDeg.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JLabel lblLvl = new JLabel("Current Level:");
		lblLvl.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JLabel lblPerT = new JLabel("Personal Tutor:");
		lblPerT.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JLabel lblDep = new JLabel("Department:");
		lblDep.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JLabel lblRegistrationNumber = new JLabel(Integer.toString(student.getRegistrationID()));  
		lblRegistrationNumber.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblEmail = new JLabel(student.getEmail());
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblDepartment = new JLabel("student.getDegree().getLeadDepartment()");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblDegree = new JLabel("student.getDegree().getCode()+ - "+student.getDegree().getName());
		lblDegree.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblCurrentLevel = new JLabel(student.getCurrentLevel());
		lblCurrentLevel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblPersonalTutor = new JLabel(student.getPersonalTutor());
		lblPersonalTutor.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.DARK_GRAY);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(separator, Alignment.LEADING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblReg)
								.addComponent(lblEm)
								.addComponent(lblDep)
								.addComponent(lblDeg)
								.addComponent(lblLvl)
								.addComponent(lblPerT))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPersonalTutor)
								.addComponent(lblCurrentLevel)
								.addComponent(lblDegree)
								.addComponent(lblDepartment)
								.addComponent(lblEmail)
								.addComponent(lblRegistrationNumber)))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblTitle)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSurnameForename)))
					.addContainerGap(169, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblSurnameForename))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblReg)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEm)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDep)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDeg)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblLvl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPerT))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblRegistrationNumber)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEmail)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDepartment)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDegree)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCurrentLevel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPersonalTutor)))
					.addGap(224))
		);
		contentPane.setLayout(gl_contentPane);
	}
}