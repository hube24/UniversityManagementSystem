package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.DatabaseSelector;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DegreesGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DegreesGUI frame = new DegreesGUI();
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
	public DegreesGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAdmin();
			}
		});
		btnBack.setBounds(22, 200, 110, 25);
		contentPane.add(btnBack);
		
		JButton btnAddDegree = new JButton("Add Degree");
		btnAddDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddDegree();
			}
		});
		btnAddDegree.setBounds(298, 200, 110, 25);
		contentPane.add(btnAddDegree);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 408, 174);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {				
			},
			new String[] {
				"Code", "Name", "Number of Levels"
			}
		) {
			Class[] columnTypes = new Class [] {
				String.class, String.class, String.class
			};
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(2).setPreferredWidth(113);
		scrollPane.setViewportView(table);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//getting list of degrees.
		DatabaseSelector dbSelector = new DatabaseSelector();
		List <String[]> degreesList = dbSelector.GetDegreesList();
		for( String[] row : degreesList) {
			model.addRow(new String[] {row[0], row[1], row[2]});
		}

	}
	protected void openAddDegree() {		
		AddDegreeGUI frame = new AddDegreeGUI();
		frame.setVisible(true);
		dispose();		
	}
	
	protected void openAdmin() {
		AdminGUI frame = new AdminGUI();
		frame.setVisible(true);
		dispose();
	}
}
