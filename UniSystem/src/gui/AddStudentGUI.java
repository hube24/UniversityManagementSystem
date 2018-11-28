package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DatabaseSelector;
import university.Degree;
import users.Registrar;
import users.Student;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class AddStudentGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddStudentGUI frame = new AddStudentGUI();
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
	public AddStudentGUI() {
		Registrar registrar = new Registrar();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblForname = new JLabel("Forname(s) :");
		lblForname.setBounds(38, 75, 91, 14);
		contentPane.add(lblForname);
		
		textField = new JTextField();
		textField.setBounds(166, 72, 254, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(38, 43, 91, 14);
		contentPane.add(lblTitle);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Ms", "Mr"}));
		comboBox.setBounds(166, 40, 71, 20);
		contentPane.add(comboBox);
		
		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setBounds(38, 103, 91, 14);
		contentPane.add(lblSurname);
		
		textField_1 = new JTextField();
		textField_1.setBounds(166, 100, 254, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDegree = new JLabel("Degree:");
		lblDegree.setBounds(38, 139, 91, 14);
		contentPane.add(lblDegree);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(166, 136, 254, 20);
		contentPane.add(comboBox_1);
		
		//getting list of degrees 
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> degreesList = dbSelector.GetDegreesList();

		for( String[] row : degreesList ){
			comboBox_1.addItem(row[0] + " - " + row[1]);
		}
		
		
		JLabel lblAssignStudentWith = new JLabel("Connect student with user account: ");
		lblAssignStudentWith.setBounds(38, 221, 176, 20);
		contentPane.add(lblAssignStudentWith);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(267, 221, 153, 20);
		contentPane.add(comboBox_2);
		
		List <String[]> usersList = dbSelector.getUserWithAccessList("student");

		for( String[] row : usersList ){
			comboBox_2.addItem(row[0]);
		}
		
		JLabel lblNewLabel = new JLabel("Personal tutor:");
		lblNewLabel.setBounds(38, 176, 91, 14);
		contentPane.add(lblNewLabel);
		
		textField_2 = new JTextField();
		textField_2.setBounds(166, 173, 254, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(38, 201, 382, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(38, 164, 382, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(38, 128, 382, 5);
		contentPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(36, 249, 384, 6);
		contentPane.add(separator_3);
		
		JLabel lblInitialPeriodOf = new JLabel("Initial period of Study:");
		lblInitialPeriodOf.setBounds(37, 266, 177, 14);
		contentPane.add(lblInitialPeriodOf);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(182, 260, 238, 20);
		contentPane.add(comboBox_3);
		
		//getting list of periods of study 
		List <String[]> periodsList = dbSelector.GetPeriodsOfStudyList();

		for( String[] row : periodsList ){
			comboBox_3.addItem(row[0] + "  " + row[1]  + " to " + row[2]);
		}
				
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String title  = (String)comboBox.getSelectedItem(); 
				String forname = textField.getText();
				String surname = textField_1.getText();
				String degreeCode = ((String)comboBox_1.getSelectedItem()).substring(0, 6);
				String personalTutor = textField_2.getText();
				String username = (String)comboBox_2.getSelectedItem();
				String initialPeriod = ((String)comboBox_3.getSelectedItem()).substring(0, 1);
				
				forname = forname.replace(" ", "");
				surname = surname.replace(" ", "");
				personalTutor = personalTutor.replace(" ", "");
				
				if(forname.equals("") || surname.equals("") || personalTutor.equals(""))
				{
					infoBox("Please fill all fields.", "Warning");
					return;
				}
				
				
				Degree degree = new Degree(degreeCode);
				degree.completeFromDB();
				
				Student student = new Student(username, 0, title, surname, forname , "", degree, personalTutor, null );
				if(registrar.addStudent(student, initialPeriod))
				{
					infoBox("Student added successfully", "Done.");
				}
				
			}
		});
		btnSubmit.setBounds(283, 315, 99, 23);
		contentPane.add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(65, 315, 89, 23);
		contentPane.add(btnCancel);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(38, 291, 382, 2);
		contentPane.add(separator_4);
	}

}
