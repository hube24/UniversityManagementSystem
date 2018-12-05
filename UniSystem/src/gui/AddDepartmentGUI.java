package gui;

/* A page to add a new department with department name and code
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.Color;

import database.Session;
import users.Administrator;

public class AddDepartmentGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddDepartmentGUI frame = new AddDepartmentGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//create an infoBox which provides infoMessage and titleBar
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	 
	/**
	 * Create the frame.
	 */
	public AddDepartmentGUI(Session s) {
		Session currSession = s;
		
		
		setBackground(Color.WHITE);
		setResizable(false);
		setTitle("Add new department");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 204);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDepartmentCode = new JLabel("Department Code:");
		lblDepartmentCode.setBounds(27, 44, 134, 20);
		contentPane.add(lblDepartmentCode);
		
		textField = new JTextField();
		textField.setBounds(142, 44, 76, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblDepartmentName = new JLabel("Department name:");
		lblDepartmentName.setBounds(27, 75, 134, 14);
		contentPane.add(lblDepartmentName);
		
		textField_1 = new JTextField();
		textField_1.setBounds(142, 75, 167, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		//create the Submit Button
		Button submit_button = new Button("Submit");
		submit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Administrator admin = new Administrator();
				
				//check if the Department exist or not
				String code = textField.getText();
				String name = textField_1.getText();
				code = code.replace(" ", "");
				
				if(!code.equals(null) && !name.equals(null))
				 if(admin.addDepartment(code, name))
				 {
					 infoBox("Department added succesfully.","Success");
					 openDepartments(currSession);
				 }
				
			}
		});
		submit_button.setBounds(237, 119, 96, 22);
		contentPane.add(submit_button);
		
		//create the Cancel Button
		Button cancel_button = new Button("Cancel");
		cancel_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openDepartments(currSession);
			}
		});
		cancel_button.setBounds(71, 119, 90, 22);
		contentPane.add(cancel_button);
	}
	//back to the former DepartmentsGUI page
	protected void openDepartments(Session s) {		
		DepartmentsGUI frame = new DepartmentsGUI(s);
		frame.setVisible(true);
		dispose();		
	}
}
