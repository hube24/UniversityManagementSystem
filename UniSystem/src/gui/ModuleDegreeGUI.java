package gui;

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

public class ModuleDegreeGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code of Module", "Name", "Credits", "Code of Degree", "Level", "Core Module"
			}
		));
		DatabaseSelector dbSelector = new DatabaseSelector();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		scrollPane.setViewportView(table);
		
		JButton btnDeleteModule = new JButton("Delete Module");
		btnDeleteModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				if(i>=0) {			

					boolean isEmpty = dbSelector.deleteModule(table.getValueAt(i, 0).toString());
					if(isEmpty) {
						model.removeRow(i);
						JOptionPane.showMessageDialog(null, "Module has been successfuly deleted.");
					}else {
						JOptionPane.showMessageDialog(null, "Unable to delete module. First remove all students from it.");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Unable to delete. Select module.");
				}
			}
		});
		btnDeleteModule.setBounds(557, 400, 155, 40);
		contentPane.add(btnDeleteModule);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDegrees(currSession);
			}
		});
		btnCancel.setBounds(84, 400, 155, 40);
		contentPane.add(btnCancel);
		
		List <String[]> modulesList = dbSelector.getModulesFromDegrees(index);
		for( String[] row : modulesList) {
			model.addRow(new String[] {row[0], row[1], row[2], row[4], row[5], row[6]});
		}
	}
	protected void openDegrees(Session s) {
		DegreesGUI frame = new DegreesGUI(s);
		frame.setVisible(true);
		dispose();
	}
}
