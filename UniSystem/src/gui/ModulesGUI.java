package gui;

/* A page to view modules including code, name credits
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ModulesGUI extends JFrame {

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
					ModulesGUI frame = new ModulesGUI(null);
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
	public ModulesGUI(Session s) {
		setTitle("Module Page");
		Session currSession = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 792, 379);
		contentPane.add(scrollPane);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		//create a Table with strings defined
		table = new JTable();
		table.setFont(new Font("Nirmala UI", Font.PLAIN, 13));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code", "Name", "Credits"
			}
		) {	//disable the editability of contents in the table
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class
				};
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setRowHeight(35);
		scrollPane.setViewportView(table);
		
		//create a Back Button and arrange its properties
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAdmin(currSession);
			}
		});
		btnBack.setBounds(40, 430, 181, 49);
		contentPane.add(btnBack);
		
		//create a Add Module Button and arrange its properties
		JButton btnAddModule = new JButton("Add Module");
		btnAddModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddModule(currSession);
			}
		});
		btnAddModule.setBounds(604, 430, 187, 49);
		contentPane.add(btnAddModule);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
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
		btnDeleteModule.setBounds(325, 430, 181, 49);
		contentPane.add(btnDeleteModule);;
		
		//getting list of modules.		
		List <String[]> modulesList = dbSelector.GetModulesList();
		for( String[] row : modulesList) {
			model.addRow(new String[] {row[0], row[1], row[2]});
		}			
	}
	
	//return the AddModuleGUI page
	protected void openAddModule(Session s) {		
		AddModuleGUI frame = new AddModuleGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	
	//return the AdminGUI page
	protected void openAdmin(Session s) {
		AdminGUI frame = new AdminGUI(s);
		frame.setVisible(true);
		dispose();
	}	
}
