package gui;

/* The login page of the system, after login, different users access to different pages
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.Session;

import database.UserAuthorization;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
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
	
		try {

		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		       if ("Nimbus".equals(info.getName())) {
		           UIManager.setLookAndFeel(info.getClassName());
		           break;
		        }
		    }
		} catch (Exception e) {

		}
		
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
		panel.setBackground(new Color(0, 102, 204));
		panel.setBounds(0, 0, 214, 303);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LIBRUS 2.0");
		lblNewLabel.setFont(new Font("Nirmala UI", Font.BOLD, 22));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 66, 134, 52);
		panel.add(lblNewLabel);
		
		JLabel lblLogIn = new JLabel("LOG IN TO");
		lblLogIn.setForeground(Color.WHITE);
		lblLogIn.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		lblLogIn.setBounds(10, 44, 134, 52);
		panel.add(lblLogIn);
		
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
				
				
				if(!username.isEmpty() && !password.isEmpty())
				{
				 String access = usrLogin.getAuthorization(username, password);
				  System.out.print(access);
				  
				  
				  
				  if(!access.equals(null))
				  {
					  
					  Session currSession = new Session(username, password, access);
					  
					  //logged in
					  switch(access){
						  case "admin":
							  openAdminScreen(currSession);
						  break;
						  
						  case "student":
							  openStudentScreen(currSession);
						  break;
					  }
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
		textField.setBounds(278, 84, 224, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(278, 59, 65, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(278, 126, 65, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(278, 151, 224, 31);
		contentPane.add(passwordField);
		
		
	}

	
	protected void openStudentScreen( Session s ) {
		// TODO Auto-generated method stub
		StudentGUI frame = new StudentGUI(s);
		frame.setVisible(true);
		dispose();
	}

	protected void openAdminScreen(  Session s ) {
		// TODO Auto-generated method stub
		AdminGUI frame = new AdminGUI(s);
		frame.setVisible(true);
		dispose();
	}
}
