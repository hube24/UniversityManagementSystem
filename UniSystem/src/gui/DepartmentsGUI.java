package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class DepartmentsGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentsGUI frame = new DepartmentsGUI();
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
	public DepartmentsGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 408, 173);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {				
			},
			new String[] {
				"Code", "Name"
			}
		) {
			Class[] columnTypes = new Class[] {
					String.class, String.class
				};
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAdmin();
			}
		});
		btnBack.setBounds(22, 199, 133, 25);
		contentPane.add(btnBack);
		
		JButton btnAddDepartment = new JButton("Add Department");
		btnAddDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAddDepartment();
			}
		});
		btnAddDepartment.setBounds(275, 199, 133, 25);
		contentPane.add(btnAddDepartment);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//getting list of departments.
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> departmentsList = dbSelector.GetDepartmentList();
		for( String[] row : departmentsList) {
			model.addRow(new String[] {row[0], row[1]});
		}
	}
	protected void openAddDepartment() {		
		AddDepartmentGUI frame = new AddDepartmentGUI();
		frame.setVisible(true);
		dispose();		
	}
	
	protected void openAdmin() {
		AdminGUI frame = new AdminGUI();
		frame.setVisible(true);
		dispose();
	}
}
