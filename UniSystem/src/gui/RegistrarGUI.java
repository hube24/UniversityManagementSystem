package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.Session;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;

public class RegistrarGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarGUI frame = new RegistrarGUI(null);
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
	public RegistrarGUI(Session s) {
		Session currSession = s;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcomeRegistrar = new JLabel("Welcome Registrar");
		lblWelcomeRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWelcomeRegistrar.setBounds(27, 26, 246, 23);
		contentPane.add(lblWelcomeRegistrar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(27, 52, 596, 2);
		contentPane.add(separator);
	}
}
