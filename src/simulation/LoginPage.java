package simulation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logging.BaseLogger;
import users.User;

public class LoginPage implements ActionListener {

	//Why do we have to make it static
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel userLabel;
	private static JTextField userText;
	private static JLabel passLabel, success, appLabel;
	private static JTextField passwordText;
	private static JButton login;
	private static JButton signup;
	private static BufferedWriter log;
	
	public LoginPage() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(350, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		
		appLabel = new JLabel("PhotoCloud");
		appLabel.setBounds(10, 20, 300, 90);
		Font boldFont = new Font(appLabel.getFont().getFontName(), Font.BOLD, 24);
		appLabel.setFont(boldFont);
		panel.add(appLabel);
		
		
		userLabel = new JLabel("Username: ");
		userLabel.setBounds(10, 120, 80, 25);
		panel.add(userLabel);
		userText = new JTextField(20);
		userText.setBounds(100, 120, 165, 25);
		panel.add(userText);
		passLabel = new JLabel("Password: ");
		passLabel.setBounds(10, 150, 80, 20);
		panel.add(passLabel);
		passwordText = new JTextField(20);
		passwordText.setBounds(100, 150, 165, 25);
		panel.add(passwordText);
		
		login = new JButton("Log in");
		login.setBounds(10, 180, 80, 25);
		login.addActionListener(this);
		panel.add(login);
		success = new JLabel("");
		success.setBounds(10, 210, 300, 25);
		panel.add(success);
		
		signup = new JButton("Sign up");
		signup.setBounds(190, 180, 80, 25);
		signup.addActionListener(this);
		panel.add(signup);
		
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == login) {
			
			// for setting the boolean to true or false so that the person couldnt be found at the beginning
			boolean found = false;
			String name = null;
			User loggedUser = null;
			for(User us: User.allUsers) {
				if(userText.getText().equals(us.getNickname()) && passwordText.getText().equals(us.getPassword())) {
					found = true;
					name = us.getRealName();
					loggedUser = us;
					break;
				}
			}
			if (found) {
				success.setText("Hello " + name);
				BaseLogger.info().log(loggedUser.getNickname() + " logged in");
				new DiscoverPage(loggedUser);
				frame.dispose();
			}
			else {
				success.setText("Invalid username or password...");
				BaseLogger.error().log("Invalid password try.");
			}
		}
		
		if (e.getSource() == signup) {
			BaseLogger.info().log("Sign up button is clicked.");
			new SignupPage();
			frame.dispose();
		}
		
	}

	
	
}
