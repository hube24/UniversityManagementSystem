package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ModulesGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModulesGUI frame = new ModulesGUI();
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
	public ModulesGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 408, 162);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code", "Name", "Credits"
			}
		) {
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
		scrollPane.setViewportView(table);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAdmin();
			}
		});
		btnBack.setBounds(22, 188, 114, 25);
		contentPane.add(btnBack);
		
		JButton btnAddModule = new JButton("Add Module");
		btnAddModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddModule();
			}
		});
		btnAddModule.setBounds(295, 188, 114, 25);
		contentPane.add(btnAddModule);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//getting list of modules.
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> modulesList = dbSelector.GetModulesList();
		for( String[] row : modulesList) {
			model.addRow(new String[] {row[0], row[1], row[2]});
		}
	}
	
	protected void openAddModule() {		
		AddModuleGUI frame = new AddModuleGUI();
		frame.setVisible(true);
		dispose();		
	}
	
	protected void openAdmin() {
		AdminGUI frame = new AdminGUI();
		frame.setVisible(true);
		dispose();
	}
}
