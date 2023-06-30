package simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import exceptions.InvalidMailException;
import exceptions.InvalidPasswordException;
import logging.BaseLogger;
import users.Free;
import users.Hobbyist;
import users.Professional;
import users.User;

public class SignupPage implements ActionListener{
	
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel userLabel, passLabel, nameLabel, snameLabel, emailLabel, plan, message, age;
	private static JTextField userText, passwordText, nameText, snameText, emailText, ageText;
	private static JButton signup, back;
	private static JRadioButton free, hobbyist, prof;

	public SignupPage() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(350, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		userLabel = new JLabel("Username: ");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);
		passLabel = new JLabel("Password: ");
		passLabel.setBounds(10, 50, 80, 20);
		panel.add(passLabel);
		passwordText = new JTextField(20);
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);
		
		nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(10, 80, 80, 20);
		panel.add(nameLabel);
		nameText = new JTextField(20);
		nameText.setBounds(100, 80, 165, 25);
		panel.add(nameText);
		
		snameLabel = new JLabel("Surname: ");
		snameLabel.setBounds(10, 110, 80, 20);
		panel.add(snameLabel);
		snameText = new JTextField(20);
		snameText.setBounds(100, 110, 165, 25);
		panel.add(snameText);
		
		emailLabel = new JLabel("Email: ");
		emailLabel.setBounds(10, 140, 80, 20);
		panel.add(emailLabel);
		emailText = new JTextField(20);
		emailText.setBounds(100, 140, 165, 25);
		panel.add(emailText);
		
		age = new JLabel("Age: ");
		age.setBounds(10, 170, 80, 20);
		panel.add(age);
		ageText = new JTextField(20);
		ageText.setBounds(100, 170, 165, 25);
		panel.add(ageText);
		
		plan = new JLabel("Choose your plan:");
		plan.setBounds(10, 200, 300, 25);
		panel.add(plan);
		
		free = new JRadioButton("Free");    
        free.setBounds(10,220,300,30);      
        hobbyist = new JRadioButton("Hobbyist (5.00TL)");    
        hobbyist.setBounds(10,240,300,30);
        prof = new JRadioButton("Professional (20.00TL)");    
        prof.setBounds(10,260,300,30);
        ButtonGroup programs = new ButtonGroup();    
        programs.add(free);
        programs.add(hobbyist);    
        programs.add(prof);
        
        panel.add(free);
        panel.add(hobbyist);
        panel.add(prof);
        
        back = new JButton("Back");
		back.setBounds(10, 290, 80, 25);
		back.addActionListener(this);
		panel.add(back);
		
		signup = new JButton("Sign up");
		signup.setBounds(190, 290, 80, 25);
		signup.addActionListener(this);
		panel.add(signup);
		
        message = new JLabel("");
		message.setBounds(10, 320, 300, 25);
		panel.add(message);
        
		frame.setVisible(true);
	}
	
	public static boolean doesNickExist(ArrayList<User> allUsers, String nick) {
    	for(User u : allUsers) {
    		if(u.getNickname().equals(nick)) {
        		return true;
        	}		
    	}
    	return false;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()== signup) {
				
			try {
				
				String username = userText.getText();
				String password = passwordText.getText();
				String realName = nameText.getText();
				String sname = snameText.getText();
				String email = emailText.getText();
				int age = Integer.valueOf(ageText.getText());
				
				
				boolean containsAtSymbol = email.contains("@");
				
				if(!containsAtSymbol) {
					throw new InvalidMailException("email doesnt contain @ symbol");
				}
				
				if(password.length()<8) {
					throw new InvalidPasswordException("password should be more than 8 characters.");
				}
				
				if(username.equals("")||password.equals("")||realName.equals("")||sname.equals("")||email.equals("")) {
		        	throw new NullPointerException();
		        }
				
				if(doesNickExist(User.allUsers, username)) {
					message.setText("This username is taken.");
				}
				
				else {
					
					if(free.isSelected()) {
		        		Free us = new Free(username, password, realName, sname, email, age, "pp.png");
		        		User.allUsers.add(us);
				        JOptionPane.showMessageDialog(frame, "Your 'Free' account is created","Signed Up",JOptionPane.PLAIN_MESSAGE);
				        BaseLogger.info().log("Free account for " + username);
				        new LoginPage();
				        frame.dispose();
					}
					else if(hobbyist.isSelected()) {
		        		Hobbyist us = new Hobbyist(username, password, realName, sname, email, age, "pp.png");
		        		User.allUsers.add(us);
				        JOptionPane.showMessageDialog(frame, "Your 'Hobbyist' account is created","Signed Up",JOptionPane.PLAIN_MESSAGE);
				        BaseLogger.info().log("Hobbyist account for " + username);
				        new LoginPage();
				        frame.dispose();
					}
					else if(prof.isSelected()) {
		        		Professional us = new Professional(username, password, realName, sname, email, age, "pp.png");
		        		User.allUsers.add(us);
				        JOptionPane.showMessageDialog(frame, "Your 'Professional' account is created","Signed Up",JOptionPane.PLAIN_MESSAGE);
				        BaseLogger.info().log("Professional account for " + username);
				        new LoginPage();
				        frame.dispose();
					}
					else {
						message.setText("Please select an account type :D");
						BaseLogger.error().log("Account type wasn't selected.");
					}
				}
				
			}
			catch (NullPointerException e1) {
        		message.setText("Please fill all info");
        	}
			
			catch (NumberFormatException e1) {
				message.setText("Age is not valid");
        	}
			
			catch (InvalidMailException e1) {
				message.setText(e1.toString());
			}
			
			catch (InvalidPasswordException e1) {
				message.setText(e1.toString());
			}
        		
		}
        
		if(e.getSource()== back) {
			BaseLogger.info().log("Back button is clicked");
			new LoginPage();
		}
	}
}
