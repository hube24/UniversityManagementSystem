package gui;

/* A page to view the list of grades including Initial Grade, Resit Grade, Repeat Grade, Final Grade of each module
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.DatabaseSelector;
import database.Session;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class ChangeGradeGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeGradeGUI frame = new ChangeGradeGUI(null);
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
	public ChangeGradeGUI(Session s) {
		Session currSession = s;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 605, 259);
		contentPane.add(scrollPane);
		
		JTable table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {				
			},
			new String[] {
				"Module", "Credit", "Initial Grade", "Resit Grade", "Repeat Grade", "Final Grade", "Final Credit"
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
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				AddGradeGUI newFrame = new AddGradeGUI(null);
				newFrame.setVisible(true);
			}
		});
		btnNewButton.setBounds(263, 378, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				UpdateGradeGUI newFrame = new UpdateGradeGUI(currSession);
				newFrame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(526, 378, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				TeacherGUI newFrame = new TeacherGUI(currSession);
				newFrame.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(10, 378, 89, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_5 = new JButton("Status");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton_5.setBounds(263, 312, 89, 23);
		contentPane.add(btnNewButton_5);
	}
}