package gui;

/* A page to change the grade including Initial Grade, Resit Grade, Repeat Grade, Final Grade of each module
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DatabaseSelector;
import database.Session;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class UpdateGradeGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateGradeGUI frame = new UpdateGradeGUI(null);
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
	public UpdateGradeGUI(Session s) {
		Session currSession = s;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInitialGrade = new JLabel("Initial Grade");
		lblInitialGrade.setBounds(53, 69, 91, 14);
		contentPane.add(lblInitialGrade);
		
		JLabel lblResitGrade = new JLabel("Resit Grade");
		lblResitGrade.setBounds(53, 107, 91, 14);
		contentPane.add(lblResitGrade);
		
		JLabel lblRepeatGrade = new JLabel("Repeat Grade");
		lblRepeatGrade.setBounds(53, 143, 91, 14);
		contentPane.add(lblRepeatGrade);
		
		JLabel lblNewLabel = new JLabel("Final Grade");
		lblNewLabel.setBounds(53, 182, 91, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(236, 66, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(236, 104, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(236, 143, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(236, 179, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setVisible(false);
				ChangeGradeGUI newFrame = new ChangeGradeGUI(currSession);
				newFrame.setVisible(true);
			}
		});
		btnNewButton.setBounds(53, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnNewButton_1.setBounds(233, 227, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblModuleCode = new JLabel("Module Code");
		lblModuleCode.setBounds(53, 30, 91, 14);
		contentPane.add(lblModuleCode);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(236, 27, 161, 20);
		contentPane.add(comboBox);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> modulesList = dbSelector.GetModulesList();

		for( String[] row : modulesList ){
			comboBox.addItem(row[0] + " - " + row[1]);
		}
	}
}
