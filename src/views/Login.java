package views;

import javax.swing.JFrame;

import components.ColorPanel;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import controllers.UserIF;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField username;
	private JTextField password;
	private UserIF controller;

	/**
	 * Create the application.
	 */
	public Login(UserIF uc) {
		initialize();
		this.controller = uc;
	}

	public void launch() {
		this.frame.setVisible(true);
	}
	
	public void dispose() {
		this.frame.dispose();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLookAndFeel("Nimbus");

		// Create GUI Widgets
		ColorPanel pane = new ColorPanel();
		frame.getContentPane().add(pane);
		pane.setLayout(null);

		//Username
		username = new JTextField();
		username.setBounds(142, 107, 200, 31);
		pane.add(username);
		username.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(77, 107, 71, 31);
		pane.add(lblUsername);

		lblUsername.requestFocusInWindow();

		//Password
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(142, 146, 200, 31);
		pane.add(password);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(77, 146, 71, 28);
		pane.add(lblPassword);

		
		//Sign In
		JButton btnLogIn = new JButton("Sign In");

		btnLogIn.setBounds(253, 188, 89, 23);
		pane.add(btnLogIn);

		
		//Below are all the action listners that connect to the Controller
		
		//Sign In Click
		btnLogIn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				controller.signIn(username.getText(), password.getText());
			}
		});

		//Enter while typing password
		password.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.signIn(username.getText(), password.getText());

			}

		});
	}

	/**
	 * Set the look and feel of the application
	 * @param look String of the desired look and feel
	 */
	private void setLookAndFeel(String look) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (look.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(look + " not found");
		}
	}

}
