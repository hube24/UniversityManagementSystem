package gui;

/* A page to add a Module with name, 4 digit serial number, credits and main teaching department
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import database.Administrator;
import database.DatabaseSelector;
import database.Session;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddModuleGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddModuleGUI frame = new AddModuleGUI(null);
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
	public AddModuleGUI(Session s) {
		Session currSession = s;
		setTitle("Add Module");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(24, 27, 156, 14);
		contentPane.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(190, 21, 187, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCode = new JLabel("4 digit serial number:");
		lblCode.setBounds(24, 105, 156, 14);
		contentPane.add(lblCode);
		
		textField_1 = new JTextField();
		textField_1.setBounds(190, 99, 70, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Main teaching department:");
		lblNewLabel.setBounds(24, 65, 156, 14);
		contentPane.add(lblNewLabel);
		
		
		//departments combobox 
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(190, 59, 187, 20);
		contentPane.add(comboBox);
		
		//getting list of departments 
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> departmentsList = dbSelector.GetDepartmentList();

		for( String[] row : departmentsList ){
			comboBox.addItem(row[0] + " - " + row[1]);
		}
		
		
		
		
		JLabel lblNumberOfCredits = new JLabel("Number of credits");
		lblNumberOfCredits.setBounds(24, 141, 156, 14);
		contentPane.add(lblNumberOfCredits);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(20, 15, 60, 5));
		spinner.setBounds(190, 135, 70, 20);
		contentPane.add(spinner);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 217, 501, 136);
		contentPane.add(scrollPane);
		
	
		
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Add Module to:", "Degree", "Level", "Is Obligatory", "Term"
			}
		) {
			Class[] columnTypes = new Class[] {
				Boolean.class, String.class, Object.class, Boolean.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(1).setPreferredWidth(135);
		table.getColumnModel().getColumn(2).setPreferredWidth(62);
		table.getColumnModel().getColumn(3).setPreferredWidth(76);
		table.getColumnModel().getColumn(4).setPreferredWidth(79);
		
		
		JComboBox lvlComboBox = new JComboBox();
		lvlComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3","4", "P"}));
		table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(lvlComboBox));
		
		JComboBox termComboBox = new JComboBox();
		termComboBox.setModel(new DefaultComboBoxModel(new String[] {"autumn", "spring", "summer","across the academic year"}));
		table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(termComboBox));
		
		//getting list of degrees 
		List <String[]> degreeList = dbSelector.GetDegreesList();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for( String[] row : degreeList ){
			model.addRow(new Object[]{false, row[0] + " - " + row[1], "1" , false, "autumn"});
		}
		
		scrollPane.setViewportView(table);
		
		
		JLabel lblDegrees = new JLabel("Degrees:");
		lblDegrees.setBounds(24, 192, 132, 14);
		contentPane.add(lblDegrees);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = textField.getText();
				String dptCode = ((String)comboBox.getSelectedItem()).substring(0, 3);
				String serialNum = textField_1.getText();
				int numberOfCredits = (int)spinner.getValue();
				
				if(name.replace(" ", "").equals("") || serialNum.replace(" ", "").equals("") )
				{
					infoBox("Please fill all the fields.","Warning");
					return;
				}
				
				if(!serialNum.matches("[0-9]+") || serialNum.length()!=4)
				{
					infoBox("Serial number can contain only 4 digits","Warning");
					return;
				}
				
				String code = dptCode + serialNum;
				
				//degrees to link : 
				List<Object[]> degrees = new ArrayList();
				
				for(int i=0;i<table.getRowCount();i++)
				{
					boolean rowSelected = (boolean) table.getModel().getValueAt(i, 0);
				    if(rowSelected){
				    	
				        String degreeCode = ((String)table.getModel().getValueAt(i, 1)).substring(0, 6);
				        String level = (String)table.getModel().getValueAt(i, 2);
				        boolean isCoreModule = (boolean)table.getModel().getValueAt(i, 3);
				        String term = (String)table.getModel().getValueAt(i, 4);
				        
				        degrees.add(new Object[] {degreeCode, level, isCoreModule, term });
				        //System.out.println(degreeCode + " " + level + " " + String.valueOf(isCoreModule)  + " " + term);
				    }
				}
				
				Administrator admin = new Administrator();
		    	if(admin.addModule(code, name, numberOfCredits, degrees))
		    		infoBox("Module added succesfully.","Success");
		    		openModules(currSession);
			}
		});
		btnSubmit.setBounds(311, 376, 89, 23);
		contentPane.add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openModules(currSession);
			}
		});
		btnCancel.setBounds(77, 376, 89, 23);
		contentPane.add(btnCancel);
	}
	protected void openModules(Session s) {		
		ModulesGUI frame = new ModulesGUI(s);
		frame.setVisible(true);
		dispose();		
	}
}
