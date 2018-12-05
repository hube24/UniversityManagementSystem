package gui;

/* A page to add a user with username, password, level of access(Administrator, Registrar, Teacher, Student)
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.Session;
import users.Administrator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddUserGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUserGUI frame = new AddUserGUI(null);
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
	
	//verify whether the  password is a valid  one or not
	public boolean isValidPassword(String password)
	{
		if(password.length() < 8) return false;
		if (!password.matches("[\\w\\Q!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\\E]+")) return false;
		
		return true;
	}
	
	
	/**
	 * Create the frame.
	 */
	public AddUserGUI(Session s) {
		Session currSession = s;
		setTitle("Add new user");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(81, 26, 100, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(81, 62, 100, 14);
		contentPane.add(lblPassword);
		
		JLabel lblLevelOfAccess = new JLabel("Level of access:");
		lblLevelOfAccess.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLevelOfAccess.setBounds(81, 134, 100, 14);
		contentPane.add(lblLevelOfAccess);
		
		textField = new JTextField();
		textField.setBounds(191, 23, 113, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(191, 62, 113, 20);
		contentPane.add(passwordField);
		
		JLabel lblRepeatPassword = new JLabel("Repeat password:");
		lblRepeatPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepeatPassword.setBounds(66, 98, 115, 14);
		contentPane.add(lblRepeatPassword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(190, 95, 114, 20);
		contentPane.add(passwordField_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Registrar", "Teacher", "Student"}));
		comboBox.setBounds(191, 131, 113, 20);
		contentPane.add(comboBox);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Administrator admin = new Administrator();
				
				//get all values of fields
				String username = textField.getText();
				String password = String.valueOf(passwordField.getPassword());
				String password2 = String.valueOf(passwordField_1.getPassword());
				String access = (String)comboBox.getSelectedItem();
				
				username = username.replace(" ", "");
				access = access.toLowerCase();
				
				//set the requirements that need to complete the table
				if(username.isEmpty() || password.isEmpty() || password2.isEmpty() || access.isEmpty())
				{
					infoBox("Please, fill all the fields. ", "Warning");
					return;
				}
				
				if(!isValidPassword(password))
				{
					infoBox("-Password must be at least 8 characters long /n -Password can't contain illegal characters like: /;()#$ etc.", "Warning");
					return;
				}
				
				if(!password.equals(password2))
				{
					System.out.print(password + " " + password2);
					infoBox("passwords don't match.", "Warning");
					return;
				}
				
				
				if(admin.addUser(username, password, access))
				 {
					 infoBox("User added succesfully.","Success");
					 openUser(currSession);	
				 }
			}
		});
		submit.setBounds(242, 183, 89, 23);
		contentPane.add(submit);
		
		//create a Cancel Button
		JButton Cancel = new JButton("Cancel");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openUser(currSession);
			}
		});
		Cancel.setBounds(66, 183, 89, 23);
		contentPane.add(Cancel);
	}
	
	//return to the UserGUI page
	protected void openUser(Session s) {		
		UsersGUI frame = new UsersGUI(s);
		frame.setVisible(true);
		dispose();		
	}
}
