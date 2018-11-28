package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.Session;

import javax.swing.JLabel;

public class AdminGUI extends JFrame {

	
	Session currSession;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public AdminGUI(Session s) {
		currSession = s;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblHelloAdmin = new JLabel("Hello Admin ");
		contentPane.add(lblHelloAdmin, BorderLayout.NORTH);
	}

}
