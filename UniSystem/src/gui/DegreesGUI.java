package gui;

/* A page view degrees with code, degree name and level
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;
import database.DatabaseSelector;
import database.Session;
import users.Student;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class DegreesGUI extends JFrame {
	static JProgressBar progressBar;
	private JPanel contentPane;
	private JTable table;
	private String index;
	static int MY_MAXIMUM;
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
					DegreesGUI frame = new DegreesGUI(null);
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
	public DegreesGUI(Session s) {
		Session currSession = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 877, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAdmin(currSession);
			}
		});
		btnBack.setBounds(40, 472, 195, 61);
		contentPane.add(btnBack);
		
		JButton btnAddDegree = new JButton("Add Degree");
		btnAddDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddDegree(currSession);
			}
		});
		btnAddDegree.setBounds(629, 472, 195, 61);
		contentPane.add(btnAddDegree);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 835, 418);
		contentPane.add(scrollPane);
					
		table = new JTable();
		table.setFont(new Font("Nirmala UI", Font.PLAIN, 13));
		table.setModel(new DefaultTableModel(
			new Object[][] {				
			},
			new String[] {
				"Code", "Name", "Number of Levels", "See Modules of this Degree."
			}
		) {
			Class[] columnTypes = new Class [] {
				String.class, String.class, String.class, Object.class
			};
			boolean[] columnEditables = new boolean[] {
				false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(2).setPreferredWidth(113);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(35);
		scrollPane.setViewportView(table);
		
		
		
		Action open = new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {	
				
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        String code = (String)table.getModel().getValueAt(modelRow, 0);
		        
		        openModuleDegree(currSession, code);
		    }

		};
		ButtonColumn buttonColumn = new ButtonColumn(table, open, 3);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		JButton btnDeleteDegree = new JButton("Delete Degree");
		btnDeleteDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = table.getSelectedRow();
				if(i>=0) {			

					boolean isEmpty = dbSelector.deleteDegree(table.getValueAt(i, 0).toString());
					if(isEmpty) {
						model.removeRow(i);
						JOptionPane.showMessageDialog(null, "Degree has been successfuly deleted.");
					}else {
						JOptionPane.showMessageDialog(null, "Unable to delete degree. First delete all modules from it.");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Unable to delete. Select degree.");
				}	
			}
		});
		btnDeleteDegree.setBounds(326, 472, 195, 61);
		contentPane.add(btnDeleteDegree);		
		
		//getting list of degrees.
		
		List <String[]> degreesList = dbSelector.GetDegreesList();
		for( String[] row : degreesList) {
			model.addRow(new String[] {row[0], row[1], row[2], "See Modules"});
		}

	}
	protected void openAddDegree(Session s) {		
		AddDegreeGUI frame = new AddDegreeGUI(s);
		frame.setVisible(true);
		dispose();		
	}
	
	protected void openAdmin(Session s) {
		AdminGUI frame = new AdminGUI(s);
		frame.setVisible(true);
		dispose();
	}
	
	protected void openModuleDegree(Session s, String i) {		
		ModuleDegreeGUI frame = new ModuleDegreeGUI(s, i);
		frame.setVisible(true);
		index = i;
		dispose();		
	}
}
