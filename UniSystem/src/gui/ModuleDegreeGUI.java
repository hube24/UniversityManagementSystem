package gui;

/* A page to view module degree including code, name, credits, code of degree, level, core module
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
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModuleDegreeGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	//create an infoBox which provides infoMessage and titleBar
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
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				       if ("Nimbus".equals(info.getName())) {
				           UIManager.setLookAndFeel(info.getClassName());
				           break;
				        }
				    }
				} catch (Exception e) {

				}
				try {
					ModuleDegreeGUI frame = new ModuleDegreeGUI(null, null);
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
	public ModuleDegreeGUI(Session s, String index) {
		setTitle("Module Degree");
		Session currSession = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 785, 364);
		contentPane.add(scrollPane);
		
		//create a table with strings defined
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code of Module", "Name", "Credits", "Code of Degree", "Level", "Core Module"
			}
			) {
			//disable the editability of the contents in the table
			Class[] columnTypes = new Class[] {
					String.class, String.class, Object.class
				};
			boolean[] columnEditables = new boolean[] {
				false, false, false,false,false,false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		//arrange the properties of the table
		table.setRowHeight(35);
		DatabaseSelector dbSelector = new DatabaseSelector();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		scrollPane.setViewportView(table);
		
		//create a Delete Module Button
		JButton btnDeleteModule = new JButton("Delete Module");
		btnDeleteModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				//identify whether the module has been successfully deleted or not
				if(i>=0) {			

					boolean isEmpty = dbSelector.deleteModule(table.getValueAt(i, 0).toString());
					if(isEmpty) {
						model.removeRow(i);
						JOptionPane.showMessageDialog(null, "Module has been successfully deleted.");
					}else {
						JOptionPane.showMessageDialog(null, "Unable to delete module. First remove all students from it.");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Unable to delete. Select module.");
				}
			}
		});
		//arrange the position of the button
		btnDeleteModule.setBounds(557, 400, 155, 40);
		contentPane.add(btnDeleteModule);
		
		
		//create a Cancel Button and arrange its properties
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDegrees(currSession);
			}
		});
		btnCancel.setBounds(84, 400, 155, 40);
		contentPane.add(btnCancel);
		
		//get Modules lists
		List <String[]> modulesList = dbSelector.getModulesFromDegrees(index);
		for( String[] row : modulesList) {
			model.addRow(new String[] {row[0], row[1], row[2], row[4], row[5], row[6]});
		}
	}
	
	//return the DegreesGUI page
	protected void openDegrees(Session s) {
		DegreesGUI frame = new DegreesGUI(s);
		frame.setVisible(true);
		dispose();
	}
}
