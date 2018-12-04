package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import users.Student;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckStudentDetail extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JLabel lblRepeatgrade;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;
	private JTextField textField_27;
	private JTextField textField_28;
	private JTextField textField_29;
	private JTextField textField_30;
	private JTextField textField_31;
	private JLabel lblCredit_1;
	private JTextField textField_32;
	private JTextField textField_33;
	private JTextField textField_34;
	private JTextField textField_35;
	private JTextField textField_36;
	private JTextField textField_37;
	private JTextField textField_38;
	private JTextField textField_39;
	private JButton btnEdit;
	private JButton btnBack;
	private JTextField textField_41;
	private JTextField textField_42;
	private JTextField textField_43;
	private JTextField textField_44;
	private JTextField textField_45;
	private JTextField textField_46;
	private JTextField textField_47;
	private JTextField textField_48;
	private JLabel lblAverageGrade;
	private JTextField textField_40;
	private JLabel label;
	private static Student st;
	private static String periodOfStudy;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckStudentDetail frame = new CheckStudentDetail(st,periodOfStudy);
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
	public CheckStudentDetail(Student sl, String periodOfStudy) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblModule = new JLabel("Module");
		lblModule.setHorizontalAlignment(SwingConstants.CENTER);
		lblModule.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblModule.setBounds(10, 13, 86, 14);
		contentPane.add(lblModule);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 51, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(10, 283, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(10, 328, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(10, 377, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBounds(10, 97, 86, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setBounds(10, 142, 86, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setEditable(false);
		textField_6.setBounds(10, 189, 86, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setBounds(10, 235, 86, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblCredit = new JLabel("Credit");
		lblCredit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCredit.setBounds(106, 13, 86, 14);
		contentPane.add(lblCredit);
		
		textField_8 = new JTextField();
		textField_8.setEditable(false);
		textField_8.setColumns(10);
		textField_8.setBounds(106, 51, 86, 20);
		contentPane.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setEditable(false);
		textField_9.setColumns(10);
		textField_9.setBounds(106, 97, 86, 20);
		contentPane.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setEditable(false);
		textField_10.setColumns(10);
		textField_10.setBounds(106, 142, 86, 20);
		contentPane.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setEditable(false);
		textField_11.setColumns(10);
		textField_11.setBounds(106, 189, 86, 20);
		contentPane.add(textField_11);
		
		textField_12 = new JTextField();
		textField_12.setEditable(false);
		textField_12.setColumns(10);
		textField_12.setBounds(106, 235, 86, 20);
		contentPane.add(textField_12);
		
		textField_13 = new JTextField();
		textField_13.setEditable(false);
		textField_13.setColumns(10);
		textField_13.setBounds(106, 283, 86, 20);
		contentPane.add(textField_13);
		
		textField_14 = new JTextField();
		textField_14.setEditable(false);
		textField_14.setColumns(10);
		textField_14.setBounds(106, 328, 86, 20);
		contentPane.add(textField_14);
		
		textField_15 = new JTextField();
		textField_15.setEditable(false);
		textField_15.setColumns(10);
		textField_15.setBounds(106, 377, 86, 20);
		contentPane.add(textField_15);
		
		JLabel lblResitgrade = new JLabel("Initial Grade");
		lblResitgrade.setHorizontalAlignment(SwingConstants.CENTER);
		lblResitgrade.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResitgrade.setBounds(202, 13, 86, 14);
		contentPane.add(lblResitgrade);
		
		textField_16 = new JTextField();
		textField_16.setEditable(false);
		textField_16.setColumns(10);
		textField_16.setBounds(202, 51, 86, 20);
		contentPane.add(textField_16);
		
		textField_17 = new JTextField();
		textField_17.setEditable(false);
		textField_17.setColumns(10);
		textField_17.setBounds(202, 97, 86, 20);
		contentPane.add(textField_17);
		
		textField_18 = new JTextField();
		textField_18.setEditable(false);
		textField_18.setColumns(10);
		textField_18.setBounds(202, 142, 86, 20);
		contentPane.add(textField_18);
		
		textField_19 = new JTextField();
		textField_19.setEditable(false);
		textField_19.setColumns(10);
		textField_19.setBounds(202, 189, 86, 20);
		contentPane.add(textField_19);
		
		textField_20 = new JTextField();
		textField_20.setEditable(false);
		textField_20.setColumns(10);
		textField_20.setBounds(202, 235, 86, 20);
		contentPane.add(textField_20);
		
		textField_21 = new JTextField();
		textField_21.setEditable(false);
		textField_21.setColumns(10);
		textField_21.setBounds(202, 283, 86, 20);
		contentPane.add(textField_21);
		
		textField_22 = new JTextField();
		textField_22.setEditable(false);
		textField_22.setColumns(10);
		textField_22.setBounds(202, 328, 86, 20);
		contentPane.add(textField_22);
		
		textField_23 = new JTextField();
		textField_23.setEditable(false);
		textField_23.setColumns(10);
		textField_23.setBounds(202, 377, 86, 20);
		contentPane.add(textField_23);
		
		lblRepeatgrade = new JLabel("Resit Grade");
		lblRepeatgrade.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepeatgrade.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRepeatgrade.setBounds(298, 15, 86, 14);
		contentPane.add(lblRepeatgrade);
		
		textField_24 = new JTextField();
		textField_24.setEditable(false);
		textField_24.setColumns(10);
		textField_24.setBounds(298, 51, 86, 20);
		contentPane.add(textField_24);
		
		textField_25 = new JTextField();
		textField_25.setEditable(false);
		textField_25.setColumns(10);
		textField_25.setBounds(298, 97, 86, 20);
		contentPane.add(textField_25);
		
		textField_26 = new JTextField();
		textField_26.setEditable(false);
		textField_26.setColumns(10);
		textField_26.setBounds(298, 142, 86, 20);
		contentPane.add(textField_26);
		
		textField_27 = new JTextField();
		textField_27.setEditable(false);
		textField_27.setColumns(10);
		textField_27.setBounds(298, 189, 86, 20);
		contentPane.add(textField_27);
		
		textField_28 = new JTextField();
		textField_28.setEditable(false);
		textField_28.setColumns(10);
		textField_28.setBounds(298, 235, 86, 20);
		contentPane.add(textField_28);
		
		textField_29 = new JTextField();
		textField_29.setEditable(false);
		textField_29.setColumns(10);
		textField_29.setBounds(298, 283, 86, 20);
		contentPane.add(textField_29);
		
		textField_30 = new JTextField();
		textField_30.setEditable(false);
		textField_30.setColumns(10);
		textField_30.setBounds(298, 328, 86, 20);
		contentPane.add(textField_30);
		
		textField_31 = new JTextField();
		textField_31.setEditable(false);
		textField_31.setColumns(10);
		textField_31.setBounds(298, 377, 86, 20);
		contentPane.add(textField_31);
		
		lblCredit_1 = new JLabel("Repeat Grade");
		lblCredit_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredit_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCredit_1.setBounds(391, 15, 97, 14);
		contentPane.add(lblCredit_1);
		
		textField_32 = new JTextField();
		textField_32.setEditable(false);
		textField_32.setColumns(10);
		textField_32.setBounds(394, 51, 86, 20);
		contentPane.add(textField_32);
		
		textField_33 = new JTextField();
		textField_33.setEditable(false);
		textField_33.setColumns(10);
		textField_33.setBounds(394, 97, 86, 20);
		contentPane.add(textField_33);
		
		textField_34 = new JTextField();
		textField_34.setEditable(false);
		textField_34.setColumns(10);
		textField_34.setBounds(394, 142, 86, 20);
		contentPane.add(textField_34);
		
		textField_35 = new JTextField();
		textField_35.setEditable(false);
		textField_35.setColumns(10);
		textField_35.setBounds(394, 189, 86, 20);
		contentPane.add(textField_35);
		
		textField_36 = new JTextField();
		textField_36.setEditable(false);
		textField_36.setColumns(10);
		textField_36.setBounds(394, 235, 86, 20);
		contentPane.add(textField_36);
		
		textField_37 = new JTextField();
		textField_37.setEditable(false);
		textField_37.setColumns(10);
		textField_37.setBounds(394, 283, 86, 20);
		contentPane.add(textField_37);
		
		textField_38 = new JTextField();
		textField_38.setEditable(false);
		textField_38.setColumns(10);
		textField_38.setBounds(394, 328, 86, 20);
		contentPane.add(textField_38);
		
		textField_39 = new JTextField();
		textField_39.setEditable(false);
		textField_39.setColumns(10);
		textField_39.setBounds(394, 377, 86, 20);
		contentPane.add(textField_39);
		
		btnEdit = new JButton("Edit");
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				EditStudentDetail newFrame = new EditStudentDetail();
				newFrame.setVisible(true);
			}
		});
		btnEdit.setBounds(391, 433, 89, 23);
		contentPane.add(btnEdit);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				CheckStudentStatus newFrame = new CheckStudentStatus();
				newFrame.setVisible(true);
			}
		});
		btnBack.setBounds(490, 433, 89, 23);
		contentPane.add(btnBack);
		
		textField_41 = new JTextField();
		textField_41.setEditable(false);
		textField_41.setColumns(10);
		textField_41.setBounds(490, 51, 86, 20);
		contentPane.add(textField_41);
		
		textField_42 = new JTextField();
		textField_42.setEditable(false);
		textField_42.setColumns(10);
		textField_42.setBounds(490, 97, 86, 20);
		contentPane.add(textField_42);
		
		textField_43 = new JTextField();
		textField_43.setEditable(false);
		textField_43.setColumns(10);
		textField_43.setBounds(490, 142, 86, 20);
		contentPane.add(textField_43);
		
		textField_44 = new JTextField();
		textField_44.setEditable(false);
		textField_44.setColumns(10);
		textField_44.setBounds(490, 189, 86, 20);
		contentPane.add(textField_44);
		
		textField_45 = new JTextField();
		textField_45.setEditable(false);
		textField_45.setColumns(10);
		textField_45.setBounds(490, 235, 86, 20);
		contentPane.add(textField_45);
		
		textField_46 = new JTextField();
		textField_46.setEditable(false);
		textField_46.setColumns(10);
		textField_46.setBounds(490, 283, 86, 20);
		contentPane.add(textField_46);
		
		textField_47 = new JTextField();
		textField_47.setEditable(false);
		textField_47.setColumns(10);
		textField_47.setBounds(490, 328, 86, 20);
		contentPane.add(textField_47);
		
		textField_48 = new JTextField();
		textField_48.setEditable(false);
		textField_48.setColumns(10);
		textField_48.setBounds(490, 377, 86, 20);
		contentPane.add(textField_48);
		
		lblAverageGrade = new JLabel("Average Grade");
		lblAverageGrade.setHorizontalAlignment(SwingConstants.CENTER);
		lblAverageGrade.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAverageGrade.setBounds(10, 433, 182, 23);
		contentPane.add(lblAverageGrade);
		
		textField_40 = new JTextField();
		textField_40.setEditable(false);
		textField_40.setColumns(10);
		textField_40.setBounds(202, 434, 86, 20);
		contentPane.add(textField_40);
		
		label = new JLabel("Final Grade");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label.setBounds(490, 15, 97, 14);
		contentPane.add(label);
	}
	
	
}
