package gui;

/* The main page of Administrator with 4 sections(User,Department,Module,Degree)
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import database.Session;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Button;

public class AdminGUI extends JFrame {

	
	Session currSession;
	private JPanel contentPane;
	private JButton btnUsers;
	//create an infoBox which provides infoMessage and titleBar
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Create the frame.
	 */

	public static void main(String[] args) {
		
		try {
		    //recommended way to set Nimbus LaF because old versions of Java 6
		    //don't have it included
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		       if ("Nimbus".equals(info.getName())) {
		           UIManager.setLookAndFeel(info.getClassName());
		           break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(null);
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	//arrange the names and positions of the elements
	public AdminGUI(Session s) {
		setTitle("Admin");
		currSession = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//create a Users Button and arrange its position
		btnUsers = new JButton("Users");
		btnUsers.setBorder(null);
		btnUsers.setBorderPainted(false);
		btnUsers.setBackground(Color.WHITE);
		btnUsers.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		btnUsers.setBounds(175, 116, 148, 31);
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openUser(currSession);		
			}
		});
		
		//create a Degrees Button and arrange its position
		JButton btnDegree = new JButton("Degrees");
		btnDegree.setBorder(null);
		btnDegree.setBorderPainted(false);
		btnDegree.setBackground(Color.WHITE);
		btnDegree.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		btnDegree.setBounds(346, 158, 148, 31);
		btnDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDegrees(currSession);
			}
		});
		
		//create a Departments Button and arrange its position
		JButton btnDepartment = new JButton("Departments");
		btnDepartment.setBorder(null);
		btnDepartment.setBorderPainted(false);
		btnDepartment.setBackground(Color.WHITE);
		btnDepartment.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		btnDepartment.setBounds(346, 116, 148, 31);
		btnDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDepartments(currSession);
			}
		});
		
		//create a Modules Button and arrange its position
		JButton btnNewModlue = new JButton("Modules");
		btnNewModlue.setBorder(null);
		btnNewModlue.setBorderPainted(false);
		btnNewModlue.setBackground(Color.WHITE);
		btnNewModlue.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		btnNewModlue.setBounds(175, 158, 148, 31);
		btnNewModlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openModules(currSession);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnDepartment);
		contentPane.add(btnNewModlue);
		contentPane.add(btnUsers);
		contentPane.add(btnDegree);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 148, 262);
		contentPane.add(panel);
		
		//create a welcome text
		JLabel lblWelcomeAdministrator = new JLabel("Welcome Administrator");
		lblWelcomeAdministrator.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		lblWelcomeAdministrator.setBounds(175, 62, 188, 31);
		contentPane.add(lblWelcomeAdministrator);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(158, 91, 336, 2);
		contentPane.add(separator);
		
		//create a Logout Button and a warning window
		Button button = new Button("Logout");
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
		//set the properties of the button
		button.setFont(new Font("Nirmala UI", Font.PLAIN, 10));
		button.setBackground(UIManager.getColor("scrollbar"));
		button.setBounds(421, 10, 86, 21);
		contentPane.add(button);
	}
	//define the page which returns to the UsersGUI page
	protected void openUser(Session s) {		
		UsersGUI frame = new UsersGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	//define the page which returns to the DepartmentsGUI page
	protected void openDepartments(Session s) {		
		DepartmentsGUI frame = new DepartmentsGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	//define the page which returns to the DegreesGUI page
	protected void openDegrees(Session s) {		
		DegreesGUI frame = new DegreesGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	//define the page which returns to the ModulesGUI page
	protected void openModules(Session s) {		
		ModulesGUI frame = new ModulesGUI(s);
		frame.setVisible(true);
		dispose();		
	}
}
