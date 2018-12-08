package gui;

/* A page to add a new degree with degree name, level, lead department and departments
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;

import database.DatabaseSelector;
import database.Session;
import users.Administrator;

import javax.swing.JCheckBox;
import java.awt.event.ActionListener;

public class AddDegreeGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddDegreeGUI frame = new AddDegreeGUI(null);
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

	/**
	 * Create the frame.
	 */
	public AddDegreeGUI(Session s) {
		Session currSession = s;
		setTitle("Add New Degree");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDegreeName = new JLabel("Degree name :");
		lblDegreeName.setBounds(31, 45, 106, 14);
		contentPane.add(lblDegreeName);
		
		textField = new JTextField();
		textField.setBounds(158, 42, 180, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblLeadDepartment = new JLabel("Lead Department:");
		lblLeadDepartment.setBounds(31, 81, 106, 14);
		contentPane.add(lblLeadDepartment);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(157, 78, 181, 17);
		contentPane.add(comboBox);
		
		JLabel lblEntry = new JLabel(" Entry: ");
		lblEntry.setBounds(31, 122, 106, 14);
		contentPane.add(lblEntry);
		
		JRadioButton rdbtnUndergraduate = new JRadioButton("Undergraduate");
		rdbtnUndergraduate.setSelected(true);
		rdbtnUndergraduate.setBounds(123, 118, 125, 23);
		contentPane.add(rdbtnUndergraduate);
		
		JRadioButton rdbtnPostgraduate = new JRadioButton("Postgraduate");
		rdbtnPostgraduate.setBounds(266, 118, 136, 23);
		contentPane.add(rdbtnPostgraduate);
		
		ButtonGroup uPButtonGroup = new ButtonGroup();
		uPButtonGroup.add(rdbtnPostgraduate);
		uPButtonGroup.add(rdbtnUndergraduate);
		
		JLabel lblYearOfIndustry = new JLabel("Year in industry:");
		lblYearOfIndustry.setBounds(31, 166, 106, 14);
		contentPane.add(lblYearOfIndustry);
		
		JRadioButton rdbtnWithAYear = new JRadioButton("with a Year in Industry");
		rdbtnWithAYear.setBounds(148, 162, 172, 23);
		contentPane.add(rdbtnWithAYear);
		
		JLabel lblNumberOfLevels = new JLabel("Number of Levels:");
		lblNumberOfLevels.setBounds(31, 204, 106, 14);
		contentPane.add(lblNumberOfLevels);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"1", "3", "4", "5"}));
		comboBox_1.setBounds(158, 201, 46, 20);
		contentPane.add(comboBox_1);
		
		//create a cancel button to get back to former page
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDegrees(currSession);
			}
		});
		
		//design the positions of the elements
		btnCancel.setBounds(62, 386, 89, 23);
		contentPane.add(btnCancel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(31, 191, 307, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 153, 307, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(31, 106, 307, 2);
		contentPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(31, 70, 307, 2);
		contentPane.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(31, 229, 307, 2);
		contentPane.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(31, 362, 307, 2);
		contentPane.add(separator_5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(148, 242, 190, 102);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		//getting list of departments 
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <JCheckBox> departmentsCheckBoxes = new ArrayList<>();
		List <String[]> departmentsList = dbSelector.GetDepartmentList();

		for( String[] row : departmentsList ){
			JCheckBox chckbx = new JCheckBox(row[0] + " - " + row[1]);
			departmentsCheckBoxes.add(chckbx);
			panel.add(chckbx);
			
			comboBox.addItem(row[0] + " - " + row[1]);
		}
		

		JLabel lblDeparments = new JLabel("Deparments:");
		lblDeparments.setBounds(31, 242, 106, 14);
		contentPane.add(lblDeparments);
		
		//Creating a submit button
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Getting all values from fields
				
				String name = textField.getText();
				
				String leadDepartmentCode = (String)comboBox.getSelectedItem();
				leadDepartmentCode = leadDepartmentCode.substring(0, 3).toUpperCase();
				
				char underPost;
				if(rdbtnUndergraduate.isSelected()) underPost = 'U'; else underPost = 'P';
				
				if(!name.toLowerCase().endsWith("with a year in industry") && rdbtnWithAYear.isSelected())
						name += " with a Year in Industry";
				
				//create a variable representing degrees
				String code = leadDepartmentCode + underPost;
				
				int numOfLevels = Integer.valueOf((String)comboBox_1.getSelectedItem());
				
				//check if the Degree already exist or not
				List<String> checkedDepartments = new ArrayList();
				boolean leadChecked = false;
				for(JCheckBox dpt : departmentsCheckBoxes)
				{
					if(dpt.isSelected()){ 
						String dptCode = dpt.getText().substring(0, 3);
						if(dptCode == leadDepartmentCode) leadChecked = true;
						checkedDepartments.add( dptCode );
					}
				}
				if(!leadChecked)checkedDepartments.add( leadDepartmentCode );
				
				
				String [] checkedDepartmentsArray = checkedDepartments.toArray(new String[checkedDepartments.size()]);
				
				
			    if(!name.isEmpty())
			    {
			    	Administrator admin = new Administrator();
			    	if(admin.addDegree(code, name, leadDepartmentCode, numOfLevels, checkedDepartmentsArray))
			    		infoBox("Degree added succesfully.","Success");
			    		openDegrees(currSession);
			 
			    }else{
			    	infoBox("Please fill all the fields","Warning!");
			    }
			}
		});
		btnNewButton.setBounds(220, 386, 89, 23);
		contentPane.add(btnNewButton);
		
	}
	//return to the Degree page
	protected void openDegrees(Session s) {		
		DegreesGUI frame = new DegreesGUI(s);
		frame.setVisible(true);
		dispose();		
	}
}
