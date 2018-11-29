package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class StudentGUI {

 private JFrame frame;
 private JTextField textField;
 private JTextField textField_1;
 private JTextField textField_2;
 private JTextField textField_3;
 private JTextField textField_4;
 private JTextField textField_5;
 private JTextField textField_6;
 private JTextField textField_7;
 private JTextField textField_8;

 /**
  * Launch the application.
  */
 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     StudentGUI window = new StudentGUI();
     window.frame.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 /**
  * Create the application.
  */
 public StudentGUI() {
  initialize();
 }

 /**
  * Initialize the contents of the frame.
  */
 private void initialize() {
  frame = new JFrame();
  frame.setBounds(100, 100, 450, 300);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.getContentPane().setLayout(null);
  
  JLabel lblHelloStudent = new JLabel("Hello Student");
  lblHelloStudent.setBounds(178, 6, 85, 16);
  frame.getContentPane().add(lblHelloStudent);
  
  JLabel lblRegisterNumber = new JLabel("Register Number");
  lblRegisterNumber.setBounds(6, 65, 105, 16);
  frame.getContentPane().add(lblRegisterNumber);
  
  JLabel lblNewLabel = new JLabel("Title");
  lblNewLabel.setBounds(33, 98, 61, 16);
  frame.getContentPane().add(lblNewLabel);
  
  JLabel lblSurname = new JLabel("Surname");
  lblSurname.setBounds(20, 126, 61, 16);
  frame.getContentPane().add(lblSurname);
  
  JLabel lblForename = new JLabel("Forename");
  lblForename.setBounds(20, 165, 61, 16);
  frame.getContentPane().add(lblForename);
  
  JLabel lblEmail = new JLabel("Email");
  lblEmail.setBounds(33, 203, 61, 16);
  frame.getContentPane().add(lblEmail);
  
  JLabel lblLevel = new JLabel("Username");
  lblLevel.setBounds(242, 65, 70, 16);
  frame.getContentPane().add(lblLevel);
  
  textField = new JTextField();
  textField.setBounds(113, 60, 105, 26);
  frame.getContentPane().add(textField);
  textField.setColumns(10);
  
  textField_1 = new JTextField();
  textField_1.setColumns(10);
  textField_1.setBounds(113, 126, 105, 26);
  frame.getContentPane().add(textField_1);
  
  textField_2 = new JTextField();
  textField_2.setColumns(10);
  textField_2.setBounds(113, 160, 105, 26);
  frame.getContentPane().add(textField_2);
  
  textField_3 = new JTextField();
  textField_3.setColumns(10);
  textField_3.setBounds(113, 198, 105, 21);
  frame.getContentPane().add(textField_3);
  
  textField_4 = new JTextField();
  textField_4.setColumns(10);
  textField_4.setBounds(314, 65, 105, 26);
  frame.getContentPane().add(textField_4);
  
  textField_5 = new JTextField();
  textField_5.setColumns(10);
  textField_5.setBounds(314, 103, 105, 26);
  frame.getContentPane().add(textField_5);
  
  textField_6 = new JTextField();
  textField_6.setColumns(10);
  textField_6.setBounds(314, 141, 105, 26);
  frame.getContentPane().add(textField_6);
  
  textField_7 = new JTextField();
  textField_7.setColumns(10);
  textField_7.setBounds(314, 179, 105, 26);
  frame.getContentPane().add(textField_7);
  
  textField_8 = new JTextField();
  textField_8.setColumns(10);
  textField_8.setBounds(113, 93, 105, 26);
  frame.getContentPane().add(textField_8);
  
  JLabel lblDegree = new JLabel("Degree");
  lblDegree.setBounds(250, 106, 52, 21);
  frame.getContentPane().add(lblDegree);
  
  JLabel lblTutor = new JLabel("Tutor");
  lblTutor.setBounds(260, 146, 36, 16);
  frame.getContentPane().add(lblTutor);
  
  JLabel label_2 = new JLabel("level");
  label_2.setBounds(260, 184, 36, 16);
  frame.getContentPane().add(label_2);
  
  JButton btnStatus = new JButton("Status");
  btnStatus.setBounds(165, 243, 117, 29);
  frame.getContentPane().add(btnStatus);
 }

}