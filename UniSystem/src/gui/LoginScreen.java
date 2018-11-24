package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.UserAuthorization;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
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
	public LoginScreen() {
		
		UserAuthorization usrLogin = new UserAuthorization();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 341);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 0, 214, 303);
		contentPane.add(panel);
		
		JLabel lblAlert = new JLabel("Invalid username or password");
		lblAlert.setForeground(Color.RED);
		lblAlert.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAlert.setBounds(307, 256, 179, 14);
		lblAlert.setVisible(false);
		contentPane.add(lblAlert);
		
		Button button = new Button("Login");
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// login request
				String username = textField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				username = username.replaceAll(" ", "");
				password = password.replaceAll(" ", "");
				
				if(!username.isEmpty() && !password.isEmpty())
				{
				  int logged = usrLogin.getAuthorization(username, password);
				  System.out.print(logged);
				  if(logged!=0)
				  {
					  //logged in
					  if(logged == 1)
						  openAdminScreen();
					  
					  if(logged == 2)
						  openStudentScreen();
					  
					  System.out.println("login succesfull");
				  }else {
					  //not logged in
					  System.out.println("login failed");
					  lblAlert.setText("Invalid username or password");
					  lblAlert.setVisible(true);
				  }
				}else {
					//username or password empty
					System.out.println("username or password empty");
					lblAlert.setText("Please fill username and password");
					lblAlert.setVisible(true);
				}
			}
		});
		
		button.setFont(new Font("Dialog", Font.PLAIN, 14));
		button.setBackground(SystemColor.activeCaption);
		button.setBounds(307, 194, 172, 31);
		contentPane.add(button);
		
		textField = new JTextField();
		textField.setBounds(278, 84, 224, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(278, 59, 65, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(278, 117, 65, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(278, 142, 224, 20);
		contentPane.add(passwordField);
		
		
	}

	
	protected void openStudentScreen() {
		// TODO Auto-generated method stub
		StudentGUI frame = new StudentGUI();
		frame.setVisible(true);
		dispose();
	}

	protected void openAdminScreen() {
		// TODO Auto-generated method stub
		AdminGUI frame = new AdminGUI();
		frame.setVisible(true);
		dispose();
	}
}
