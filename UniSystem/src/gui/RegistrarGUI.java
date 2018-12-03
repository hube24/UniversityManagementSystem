package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingWorker;
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

	static JProgressBar progressBar;
	private JPanel contentPane;
	static private JPanel panel;
	static private JTable table;
	static int MY_MAXIMUM;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args)
	{
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
		
		Progress loadStudentTask = new Progress(table, progressBar, panel, MY_MAXIMUM);
		loadStudentTask.execute();
	}
	
	public static void run(){
		
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
		
		Progress loadStudentTask = new Progress(table, progressBar, panel, MY_MAXIMUM);
		loadStudentTask.execute();
	}
	
	/**
	 * Create the frame.
	 */
	public RegistrarGUI(Session s) {
		Session currSession = s;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(14, 12, 960, 370);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		
		JLabel lblLoadingListOf = new JLabel("Loading list of students...");
		lblLoadingListOf.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		lblLoadingListOf.setBounds(320, 129, 279, 28);
		panel.add(lblLoadingListOf);
		
		JLabel lblObtainingNumbersOf = new JLabel("Obtaining numbers of credits...");
		lblObtainingNumbersOf.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblObtainingNumbersOf.setBounds(420, 168, 296, 21);
		panel.add(lblObtainingNumbersOf);
		
		JLabel lblWelcomeRegistrar = new JLabel("Welcome Registrar");
		lblWelcomeRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWelcomeRegistrar.setBounds(27, 26, 246, 23);
		contentPane.add(lblWelcomeRegistrar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(27, 52, 596, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 69, 933, 254);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Registration num.", "Degree", "Name", "Level", "Registered credits ", "Add/Drop Modules"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, Integer.class, Object.class
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
		
		table.setRowHeight(35);
		
		JLabel creditsNum = new JLabel();
		creditsNum.setText("0");
		table.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		
		scrollPane.setViewportView(table);
		
		RegistrarGUI myself = this;
		
		Action open = new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        int regNum = (int)table.getModel().getValueAt(modelRow, 0);
		        
		        System.out.println(regNum);
		        
		        Student student = new Student(regNum);
		        student.completeFromDB();
		        AddOptionalModulesGUI frame = new AddOptionalModulesGUI(currSession, student, myself, modelRow);
		        frame.setVisible(true);
		    }

		};
		 
		ButtonColumn buttonColumn = new ButtonColumn(table, open, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		DatabaseSelector dbSelector = new DatabaseSelector();
		MY_MAXIMUM = dbSelector.getStudentCount();
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(272, 215, 418, 28);
		progressBar.setMaximum(MY_MAXIMUM);
		panel.add(progressBar);

	}

	public void updateTableRecord(int registrationID, int sumOfCredits, int row) {
		// TODO Auto-generated method stub
		table.getModel().setValueAt(sumOfCredits, row, 4);
	}
	

	
}


class Progress extends SwingWorker<Void, Void>
{
	
	JTable table;
	JProgressBar progressBar;
	JPanel panel;
	int MY_MAXIMUM;
	
	public Progress(JTable t, JProgressBar b, JPanel p, int max) {
		table = t;
		progressBar = b;
		panel = p;
		MY_MAXIMUM = max;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
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
					
					
					model.addRow(new Object[] {regNum, degree, name, level, sumCredits, "Add/Drop Modules"});
					count+=1;
					progressBar.setValue(count);
					if(progressBar.getValue()>= MY_MAXIMUM)
					{
						panel.setVisible(false);
						table.setVisible(true);
					}
				}
		return null;
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