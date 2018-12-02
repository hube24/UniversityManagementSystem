package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DatabaseSelector;
import database.Session;
import users.Student;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JProgressBar;

public class RegistrarGUI extends JFrame {

	private JPanel contentPane;
	static private JPanel panel;
	static private JTable table;

	static JProgressBar progressBar;
	
	static int MY_MAXIMUM;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
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
			RegistrarGUI frame = new RegistrarGUI(null);
			frame.setVisible(true);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		loadStudents();
		
		
	}

	
	static void loadStudents()
	{
		//filling table of students
		
		table.setVisible(false);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		DatabaseSelector dbSelector = new DatabaseSelector();
		int count = 0;
		
		List <String[]> studentssList = dbSelector.GetStudentsList();
		for( String[] row : studentssList) {			
			Integer regNum = Integer.valueOf(row[0]);
			String degree = row[1];
			String name = row[3] + " " + row[4] + " " + row[5];			
			Student student = new Student(regNum);
			String level = student.getCurrentLevel();
			
			
			String periodOfStudy = student.getCurrentPeriodOfStudy();
			System.out.print(periodOfStudy);
			
			int sumCredits = 0;
			
			if(!periodOfStudy.isEmpty()){
			 String periodLabel = periodOfStudy.substring(0,1);
			 sumCredits = student.getRegisteredCredits(periodLabel);
			}
			
			
			model.addRow(new Object[] {regNum, degree, name, level, sumCredits});
			count+=1;
			progressBar.setValue(count);
			if(progressBar.getValue()>= MY_MAXIMUM)
			{
				panel.setVisible(false);
				table.setVisible(true);
			}
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public RegistrarGUI(Session s) {
		Session currSession = s;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(14, 12, 645, 370);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		
		JLabel lblLoadingListOf = new JLabel("Loading list of students...");
		lblLoadingListOf.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		lblLoadingListOf.setBounds(150, 150, 279, 28);
		panel.add(lblLoadingListOf);
		
		JLabel lblObtainingNumbersOf = new JLabel("Obtaining numbers of credits...");
		lblObtainingNumbersOf.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		lblObtainingNumbersOf.setBounds(242, 180, 296, 21);
		panel.add(lblObtainingNumbersOf);
		
		JLabel lblWelcomeRegistrar = new JLabel("Welcome Registrar");
		lblWelcomeRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWelcomeRegistrar.setBounds(27, 26, 246, 23);
		contentPane.add(lblWelcomeRegistrar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(27, 52, 596, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 69, 596, 254);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Registration num.", "Degree", "Name", "Level", "Registered credits "
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(105);
		table.getColumnModel().getColumn(2).setPreferredWidth(111);
		table.getColumnModel().getColumn(3).setPreferredWidth(170);
		table.getColumnModel().getColumn(4).setPreferredWidth(104);
		
		table.getColumnModel().getColumn(4).setCellRenderer(new ColourTableCellRenderer());
		
		JLabel creditsNum = new JLabel();
		creditsNum.setText("0");
		table.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		
		
		scrollPane.setViewportView(table);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		MY_MAXIMUM = dbSelector.getStudentCount();
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(130, 212, 418, 28);
		progressBar.setMaximum(MY_MAXIMUM);
		panel.add(progressBar);

	}
}


class ColourTableCellRenderer extends DefaultTableCellRenderer {
	  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

	    //Cells are by default rendered as a JLabel.
	    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

	    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
	    
	    //getting level of student
	    String lvl = (String)table.getModel().getValueAt(row, 3);
	    
	    //getting desired amount of credits for paritcular degree
	    int rightCredits = (lvl != "4")?120:180;
	    
	    //changing colour of student
	    if( (int)value != rightCredits  ) 
	    	l.setBackground( Color.RED ); 
	    else 
	    	l.setBackground( Color.GREEN );
	  //Return the JLabel which renders the cell.
	  return l;

	}
}