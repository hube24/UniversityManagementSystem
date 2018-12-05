package gui;

/* A page to view department degree with code of degree, name of degree, code of department, lead department
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DepartmentDegreeGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentDegreeGUI frame = new DepartmentDegreeGUI(null, null);
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
	public DepartmentDegreeGUI(Session s, String index) {
		setTitle("Department Degree");
		Session currSession = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 838, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 796, 262);
		contentPane.add(scrollPane);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		//create a table
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {				
			},
			new String[] {
				"code of degree", "name", "code of department", "Lead Department"
			}
		) {	//disable the editability of the contents in the table
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class
				};
			boolean[] columnEditables = new boolean[] {
					false, false, false,false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
		});
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//create a Delete Degree Button
		JButton btnDeleteDegree = new JButton("Delete Degree");
		btnDeleteDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = table.getSelectedRow();
				//identify whether the degree is successfully deleted or not
				if(i>=0) {			

					boolean isEmpty = dbSelector.deleteDegree(table.getValueAt(i, 0).toString());
					if(isEmpty) {
						model.removeRow(i);
						JOptionPane.showMessageDialog(null, "Degree has been successfuly deleted.");
					}else {
						JOptionPane.showMessageDialog(null, "Unable to delete degree. First delete all modules from it.");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Unable to degree. Select degree.");
				}
			}
		});
		
		btnDeleteDegree.setBounds(505, 316, 180, 25);
		contentPane.add(btnDeleteDegree);
		
		//create a Cancel Button and arrange its position
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDepartmens(currSession);
			}
		});
		btnCancel.setBounds(77, 316, 180, 25);
		contentPane.add(btnCancel);
		
		//get Degrees from the database
		List <String[]> degreesList = dbSelector.getDegreesFromDepartment(index);
		for( String[] row : degreesList) {
			model.addRow(new String[] {row[0], row[1], row[3], row[5]});
		}
	}
	//return the DepartmentsGUI page
	protected void openDepartmens(Session s) {
		DepartmentsGUI frame = new DepartmentsGUI(s);
		frame.setVisible(true);
		dispose();
	}
}
