package gui;

/* A page to check the student's status(credits,final grade) each year and get the mean grade
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import users.Student;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckStudentStatus extends JFrame {

	private JPanel contentPane;
	private JTextField txtYear;
	private JTextField txtYear_1;
	private JTextField txtYear_2;
	private JTextField txtYear_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JLabel lblNewLabel_3;
	private JLabel lblDegreeClass;
	private JTextField textField_13;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private static Student st;
	private static String periodOfStudy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckStudentStatus frame = new CheckStudentStatus();
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
	public CheckStudentStatus() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(62, 35, 86, 14);
		contentPane.add(lblYear);
		
		JLabel lblNewLabel = new JLabel("Credit");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(204, 35, 86, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Grade");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(349, 35, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		txtYear = new JTextField();
		txtYear.setHorizontalAlignment(SwingConstants.CENTER);
		txtYear.setEditable(false);
		txtYear.setText("Year 1");
		txtYear.setBounds(62, 88, 86, 20);
		contentPane.add(txtYear);
		txtYear.setColumns(10);
		
		txtYear_1 = new JTextField();
		txtYear_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtYear_1.setEditable(false);
		txtYear_1.setText("Year 2");
		txtYear_1.setBounds(62, 143, 86, 20);
		contentPane.add(txtYear_1);
		txtYear_1.setColumns(10);
		
		txtYear_2 = new JTextField();
		txtYear_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtYear_2.setEditable(false);
		txtYear_2.setText("Year 3");
		txtYear_2.setBounds(62, 202, 86, 20);
		contentPane.add(txtYear_2);
		txtYear_2.setColumns(10);
		
		txtYear_3 = new JTextField();
		txtYear_3.setHorizontalAlignment(SwingConstants.CENTER);
		txtYear_3.setEditable(false);
		txtYear_3.setText("Year 4");
		txtYear_3.setBounds(62, 262, 86, 20);
		contentPane.add(txtYear_3);
		txtYear_3.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("weighted mean grade");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(165, 336, 150, 33);
		contentPane.add(lblNewLabel_2);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBounds(165, 380, 140, 33);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setBounds(204, 88, 86, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setEditable(false);
		textField_6.setBounds(204, 143, 86, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setBounds(204, 202, 86, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setEditable(false);
		textField_8.setBounds(204, 262, 86, 20);
		contentPane.add(textField_8);
		textField_8.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setEditable(false);
		textField_9.setBounds(349, 88, 86, 20);
		contentPane.add(textField_9);
		textField_9.setColumns(10);
		
		textField_10 = new JTextField();
		textField_10.setEditable(false);
		textField_10.setBounds(349, 143, 86, 20);
		contentPane.add(textField_10);
		textField_10.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setEditable(false);
		textField_11.setBounds(349, 202, 86, 20);
		contentPane.add(textField_11);
		textField_11.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setEditable(false);
		textField_12.setBounds(349, 262, 86, 20);
		contentPane.add(textField_12);
		textField_12.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Degree");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(488, 35, 86, 14);
		contentPane.add(lblNewLabel_3);
		
		lblDegreeClass = new JLabel("Degree Class");
		lblDegreeClass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDegreeClass.setBounds(531, 336, 100, 33);
		contentPane.add(lblDegreeClass);
		
		textField_13 = new JTextField();
		textField_13.setEditable(false);
		textField_13.setColumns(10);
		textField_13.setBounds(508, 380, 140, 33);
		contentPane.add(textField_13);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(488, 88, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(488, 143, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(488, 202, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(488, 262, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			    CheckStudentDetail newFrame = new CheckStudentDetail(st,periodOfStudy);
				newFrame.setVisible(true);
			}
		});
		btnView.setBounds(653, 87, 89, 23);
		contentPane.add(btnView);
		
		JButton btnView_1 = new JButton("View");
		btnView_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			    CheckStudentDetail newFrame = new CheckStudentDetail(st,periodOfStudy);
				newFrame.setVisible(true);
			}
		});
		btnView_1.setBounds(653, 142, 89, 23);
		contentPane.add(btnView_1);
		
		JButton button = new JButton("View");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			    CheckStudentDetail newFrame = new CheckStudentDetail(st,periodOfStudy);
				newFrame.setVisible(true);
			}
		});
		button.setBounds(653, 201, 89, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("View");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			    CheckStudentDetail newFrame = new CheckStudentDetail(st,periodOfStudy);
				newFrame.setVisible(true);
			}
		});
		button_1.setBounds(653, 261, 89, 23);
		contentPane.add(button_1);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			    CheckStudent newFrame = new CheckStudent();
				newFrame.setVisible(true);
			}
		});
		btnBack.setBounds(365, 433, 89, 23);
		contentPane.add(btnBack);
	}
}
