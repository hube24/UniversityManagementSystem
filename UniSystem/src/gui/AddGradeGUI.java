package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.Administrator;
import database.DatabaseSelector;
import database.Session;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import database.Administrator;
import database.Session;
import javax.swing.JComboBox;

public class AddGradeGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					AddGradeGUI frame = new AddGradeGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Create the frame.
	 */
	public AddGradeGUI(Session s) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblInitialGrade = new JLabel("Initial Grade:");
		lblInitialGrade.setBounds(77, 129, 89, 14);
		contentPane.add(lblInitialGrade);
		
		textField_1 = new JTextField();
		textField_1.setBounds(244, 126, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				ChangeGradeGUI newFrame = new ChangeGradeGUI();
				newFrame.setVisible(true);
			}
		});
		btnCancel.setBounds(77, 209, 89, 23);
		contentPane.add(btnCancel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(244, 73, 166, 20);
		contentPane.add(comboBox);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Administrator teacher = new Administrator();
				
				String dptCode = ((String)comboBox.getSelectedItem()).substring(0, 3);
				String grade = textField_1.getText();
				
		    	if(teacher.addGrade(grade))
		    		infoBox("Initial Grade added succesfully.","Success");
		    		openChangeGrade(null);
			}
		});
		btnSubmit.setBounds(241, 209, 89, 23);
		contentPane.add(btnSubmit);
		
		JLabel lblNewLabel = new JLabel("Course Code:");
		lblNewLabel.setBounds(77, 76, 76, 14);
		contentPane.add(lblNewLabel);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> modulesList = dbSelector.GetModulesList();

		for( String[] row : modulesList ){
			comboBox.addItem(row[0] + " - " + row[1]);
		}
		
	}
	protected void openChangeGrade(Session s) {		
		ChangeGradeGUI frame = new ChangeGradeGUI();
		frame.setVisible(true);
		dispose();		
	}
}
