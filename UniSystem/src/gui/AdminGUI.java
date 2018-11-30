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

public class AdminGUI extends JFrame {

	
	Session currSession;
	private JPanel contentPane;
	private JButton btnUsers;


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
	
	public AdminGUI(Session s) {
		currSession = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnUsers = new JButton("Users");
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openUser(currSession);		
			}
		});
		
		JButton btnDegree = new JButton("Degrees");
		btnDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDegrees(currSession);
			}
		});
		
		JButton btnDepartment = new JButton("Departments");
		btnDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDepartments(currSession);
			}
		});
		
		JButton btnNewModlue = new JButton("Modules");
		btnNewModlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openModules(currSession);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnDepartment, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addComponent(btnNewModlue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnUsers, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addComponent(btnDegree, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDepartment, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewModlue, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUsers, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDegree, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
		);
		contentPane.setLayout(gl_contentPane);
	}
	protected void openUser(Session s) {		
		UsersGUI frame = new UsersGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	protected void openDepartments(Session s) {		
		DepartmentsGUI frame = new DepartmentsGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	protected void openDegrees(Session s) {		
		DegreesGUI frame = new DegreesGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	protected void openModules(Session s) {		
		ModulesGUI frame = new ModulesGUI(s);
		frame.setVisible(true);
		dispose();		
	}
}
