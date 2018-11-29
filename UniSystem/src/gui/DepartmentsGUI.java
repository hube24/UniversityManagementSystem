package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class DepartmentsGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public String index;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentsGUI frame = new DepartmentsGUI(null);
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
	public DepartmentsGUI(Session s) {
		Session currSession = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 864, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 822, 338);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Nirmala UI", Font.PLAIN, 13));
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
				openAdmin(currSession);
			}
		});
		btnBack.setBounds(39, 392, 169, 46);
		contentPane.add(btnBack);
		
		JButton btnAddDepartment = new JButton("Add Department");
		btnAddDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAddDepartment(currSession);
			}
		});
		btnAddDepartment.setBounds(623, 392, 175, 46);
		contentPane.add(btnAddDepartment);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		JButton btnDeleteDepartment = new JButton("Delete Department");
		btnDeleteDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				if(i>=0) {			

					boolean isEmpty = dbSelector.deleteDepartment(table.getValueAt(i, 0).toString());
					if(isEmpty) {
						model.removeRow(i);
						JOptionPane.showMessageDialog(null, "Department has been successfuly deleted.");
					}else {
						JOptionPane.showMessageDialog(null, "Unable to delete department. First delete all degrees from it.");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Unable to department. Select department.");
				}		
			}
		});
		btnDeleteDepartment.setBounds(327, 392, 169, 46);
		contentPane.add(btnDeleteDepartment);
		
		
		
		//getting list of departments.		
		List <String[]> departmentsList = dbSelector.GetDepartmentList();
		for( String[] row : departmentsList) {
			model.addRow(new String[] {row[0], row[1]});
		}
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	        public void valueChanged(ListSelectionEvent e) {
	        	
	        	index = table.getValueAt(table.getSelectedRow(), 0).toString();	        	
	        	openDepartmentDegree(currSession, index);
	        }			
	    });
		
	}
	
	protected void openAddDepartment(Session s) {		
		AddDepartmentGUI frame = new AddDepartmentGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	
	protected void openDepartmentDegree(Session s, String i) {		
		DepartmentDegreeGUI frame = new DepartmentDegreeGUI(s, i);
		frame.setVisible(true);
		index = i;
		dispose();			
	}
	
	protected void openAdmin(Session s) {
		AdminGUI frame = new AdminGUI(s);
		frame.setVisible(true);
		dispose();
	}
}
