package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TeacherGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherGUI frame = new TeacherGUI();
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
	public TeacherGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcomeTeacher = new JLabel("Welcome, Teacher");
		lblWelcomeTeacher.setFont(new Font("Yu Gothic", Font.BOLD, 18));
		lblWelcomeTeacher.setBounds(134, 11, 171, 56);
		contentPane.add(lblWelcomeTeacher);
		
		JLabel lblEnterStudentidFor = new JLabel("Enter StudentID below for his/her info");
		lblEnterStudentidFor.setBounds(115, 105, 227, 20);
		contentPane.add(lblEnterStudentidFor);
		
		textField = new JTextField();
		textField.setBounds(134, 136, 158, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setVisible(false);
				ChangeGradeGUI newFrame = new ChangeGradeGUI();
				newFrame.setVisible(true);
			}
		});
		btnContinue.setBounds(164, 197, 89, 23);
		contentPane.add(btnContinue);
	}

}
