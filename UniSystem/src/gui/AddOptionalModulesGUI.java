package gui;

/* A page to add or drop optional modules for students
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DatabaseSelector;
import database.Session;
import university.Degree;
import university.Module;
import users.Registrar;
import users.Student;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class AddOptionalModulesGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private RegistrarGUI parentFrame;
	private int modelRow;
	/**
	 * Launch the application.
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
					
					
					Student stu = new Student("",100008, "","","","",new Degree("", "COMU03", 3), "", null);
					
					
					AddOptionalModulesGUI frame = new AddOptionalModulesGUI(null,stu, null,0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	int sumOfCredits = 0;	
    int rightCredits = 120;

	
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	
	void updateCreditCount(JLabel label, int add)
	{
		sumOfCredits += add;
		if(sumOfCredits != rightCredits) label.setForeground(Color.RED); else label.setForeground(Color.GREEN);
		label.setText( String.valueOf((sumOfCredits)) );
	}
	
	
	boolean moduleIsCore(String code, List<String[]> corelist)
	{
		for(String[] row : corelist)
		{
			if(row[0].equals(code))return true;
		}
		return false;
	}	
	/**
	 * Create the frame.
	 * @param modelRow 
	 */
	public AddOptionalModulesGUI(Session s, Student student, RegistrarGUI pf, int r ) {
		parentFrame = pf;
		modelRow = r;
		Session currSession = s;
		
		//String level = student.getCurrentLevel();
		
		String level = student.getCurrentLevel();
	    int rightCredits = (level != "4")?120:180;
	    
		

		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddOptionalModules = new JLabel("Add/Drop optional modules : ");
		lblAddOptionalModules.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		lblAddOptionalModules.setBounds(36, 41, 216, 26);
		contentPane.add(lblAddOptionalModules);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 117, 349, 245);
		contentPane.add(scrollPane);
		
		JLabel creditsLabel = new JLabel("0");
		creditsLabel.setFont(new Font("Nirmala UI", Font.BOLD, 18));
		creditsLabel.setBounds(730, 390, 46, 17);
		contentPane.add(creditsLabel);
		
		table = new JTable();
		table.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Module", "Credits", "Select"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Integer.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(207);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> moduleList = dbSelector.getOptionalModules(student.getDegree(), level);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for( String[] row : moduleList ){
			model.addRow(new Object[]{row[0] + " - " + row[1], Integer.valueOf(row[2]), Boolean.FALSE});
		}
		
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(443, 117, 349, 245);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Module", "credits", "Select"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Integer.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table_1.getColumnModel().getColumn(0).setPreferredWidth(267);
		

		List <String[]> registeredModuleList = dbSelector.getRegisteredModules(student);
		DefaultTableModel model2 = (DefaultTableModel) table_1.getModel();
		for( String[] row : registeredModuleList ){
			model2.addRow(new Object[]{row[0] + " - " + row[1], Integer.valueOf(row[2]), Boolean.FALSE});
			updateCreditCount(creditsLabel, Integer.valueOf(row[2]));
		}
		
		List <String[]> coreModuleList = dbSelector.getCoreModulesList(student.getDegree(), level);
		
		scrollPane_1.setViewportView(table_1);
		
		JButton btnAddModules = new JButton("Add modules ->");
		btnAddModules.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		btnAddModules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// transfer modules from table 1 to table 2
				int rowNumber = table.getModel().getRowCount();
				
				List<Integer> rowsToDelete = new ArrayList<Integer>();
				
				for (int i = 0; i < rowNumber; i++) {
					//if module selected
			         if((boolean)table.getModel().getValueAt(i, 2)){
			        	 
			        	 String name = (String)table.getModel().getValueAt(i, 0);
			        	 int credits = (int)table.getModel().getValueAt(i, 1);
			        	 
			        	 ((DefaultTableModel)table_1.getModel()).addRow(new Object[]{name, credits, Boolean.FALSE});
			        	 ((DefaultTableModel)table.getModel()).removeRow(i);
			        	 i--;
			        	 rowNumber--;
			        	 updateCreditCount(creditsLabel, credits);
			        	 
			         }
			         
			     }
				
			}
		});
		btnAddModules.setBounds(340, 373, 146, 34);
		contentPane.add(btnAddModules);
		
		JButton btnNewButton = new JButton("<- Remove modules");
		btnNewButton.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// transfer modules from table 2 to table 1
				// transfer modules from table 1 to table 2
				boolean showalert = false;
				int rowNumber = table_1.getModel().getRowCount();
				for (int i = 0; i < rowNumber; i++) {
					//if module selected
			         if( (boolean)table_1.getModel().getValueAt(i, 2) ){
			        	 
			        	 String name = (String)table_1.getModel().getValueAt(i, 0);
			        	 int credits = (int)table_1.getModel().getValueAt(i, 1);
			        	 
			        	 if(! moduleIsCore(name.substring(0,7), coreModuleList) ){
				        	 ((DefaultTableModel)table.getModel()).addRow(new Object[]{name, credits, Boolean.FALSE});
				        	 ((DefaultTableModel)table_1.getModel()).removeRow(i);
				        	 
				        	 i--;
				        	 rowNumber--;
				        	 updateCreditCount(creditsLabel, -credits);
			        	 }else{
			        		 showalert = true;
			        		 ((DefaultTableModel)table_1.getModel()).setValueAt(Boolean.FALSE, i, 2);
			        	 }
			         }			         
			     }
				if(showalert) infoBox("Core modules cannot be removed.", "warning");
			}
		});
		btnNewButton.setBounds(340, 72, 146, 34);
		contentPane.add(btnNewButton);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sumOfCredits > rightCredits){
					infoBox("Can't submit the changes, sum of credits is too high. Please remove some modules.", "warning");
					return;
				}
				if(sumOfCredits < rightCredits){
					System.out.print(sumOfCredits);
					infoBox("Can't submit the changes, sum of credits is too low. Please add some modules.", "warning");
					return;
				}
				
				List<Module> dropModules = new ArrayList();
				List<Module> addModules = new ArrayList();
				
				int rowNumber = table.getModel().getRowCount();
				for (int i = 0; i < rowNumber; i++) {
			        String code = ((String)table.getModel().getValueAt(i, 0)).substring(0,7);
			        Module m = new Module(code, "", 0);
			        dropModules.add(m);
				}
				
				rowNumber = table_1.getModel().getRowCount();
				for (int i = 0; i < rowNumber; i++) {
			        String code = ((String)table_1.getModel().getValueAt(i, 0)).substring(0,7);
			        if(!moduleIsCore(code, coreModuleList)){
				        Module m = new Module(code, "", 0);
				        addModules.add(m);
			        }
				}
				
				Registrar registrar = new Registrar();
				if(registrar.addDropOptionalModules(addModules, dropModules, student)){
					infoBox("Modules updated successfully!", "Done.");
				}	
				
				parentFrame.updateTableRecord(student.getRegistrationID(), sumOfCredits, modelRow);
				dispose();
			}
		});
		btnSubmit.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		btnSubmit.setBounds(565, 449, 124, 23);
		contentPane.add(btnSubmit);
		
		JLabel lblSumOfCredits = new JLabel("Sum of credits: ");
		lblSumOfCredits.setFont(new Font("Nirmala UI", Font.BOLD, 12));
		lblSumOfCredits.setBounds(634, 394, 130, 14);
		contentPane.add(lblSumOfCredits);		

		
		JSeparator separator = new JSeparator();
		separator.setBounds(36, 418, 759, 2);
		contentPane.add(separator);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		btnNewButton_1.setBounds(123, 449, 124, 23);
		contentPane.add(btnNewButton_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(36, 65, 756, 2);
		contentPane.add(separator_1);
	}
}
