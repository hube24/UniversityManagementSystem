package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.DatabaseSelector;
import java.util.ArrayList;
import java.util.List;

public class UsersGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsersGUI frame = new UsersGUI();
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
	public UsersGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//Change it for list of students from db.
		String[] students = new String[3];
		students[0] = "Student1";
		students[1] = "Student2";
		students[2] = "Student3";
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(176, 195, 85, 25);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAddUser();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnAddUser);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Username", "Level of access"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		}
				);
		table.setBounds(27, 36, 382, 146);
		contentPane.add(table);
		
		//getting list of users.
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> usersList = dbSelector.GetUsersList();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for( String[] row : usersList) {
			model.addRow(new String[] {row[0], row[2]});
		}
	}
	

	
	protected void openAddUser() {		
		AddUserGUI frame = new AddUserGUI();
		frame.setVisible(true);
		dispose();		
	}
}