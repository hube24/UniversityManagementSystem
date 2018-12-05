package gui;

/* A page to check the student's status(credits,final grade) each year and get the mean grade
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import users.Student;
import users.Teacher;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;

import javax.swing.JScrollPane;

public class CheckGradesGUI extends JFrame {
	
	private Student student;
	private JPanel contentPane;
	private static Student st;
	private static String periodOfStudy;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {			
				public void run() {
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					       if ("Nimbus".equals(info.getName())) {
					           UIManager.setLookAndFeel(info.getClassName());
					           break;
					        }
					    }
					} catch (Exception e) {
					}
				try {
					CheckGradesGUI frame = new CheckGradesGUI(null, -1);
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
	public CheckGradesGUI(Session s, int regNum) {
		Session currSession = s;
		student = new Student(regNum);
		student.completeFromDB();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 896, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		JLabel lblRegistrationNumber = new JLabel("Registration Number: "+ student.getRegistrationID());
		lblRegistrationNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRegistrationNumber.setBounds(10, 11, 410, 41);
		contentPane.add(lblRegistrationNumber);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 105, 860, 275);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code of the Module", "Name of the Module", "Period of Study", "Initial Grade", "Resit Grade", "Final Grade", "Add/Update Grade"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(107);
		table.getColumnModel().getColumn(1).setPreferredWidth(109);
		table.getColumnModel().getColumn(2).setPreferredWidth(96);
		table.getColumnModel().getColumn(6).setPreferredWidth(127);
		table.setRowHeight(35);
		
		Action open = new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {	
				
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        String code = (String)table.getModel().getValueAt(modelRow, 0);        		        
		    }
		};
		
		ButtonColumn buttonColumn = new ButtonColumn(table, open, 6);
		
		JLabel lblDegree = new JLabel("");
		lblDegree.setText(student.getDegree().getCode() + " - " + student.getDegree().getName());
		lblDegree.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDegree.setBounds(10, 40, 307, 35);
		contentPane.add(lblDegree);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		
		
		List <String[]> moduleList = dbSelector.getRegisteredModules(student);
		System.out.println(moduleList);
		for( String[] row : moduleList) {
			if(row[6]==null) {
				model.addRow(new String[] {row[0], row[1], null, row[5], row[6],row[5], "Add/Update Grade"}); 
			}else if(Integer.valueOf(row[6])>=40){			
				model.addRow(new String[] {row[0], row[1], null, row[5], row[6],"40", "Add/Update Grade"}); 
			}else {
				model.addRow(new String[] {row[0], row[1], null, row[5], row[6],"Failed", "Add/Update Grade"}); 
			}
			 
		}
		
	}
}
