package simulation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import filter.Photo;
import logging.BaseLogger;
import users.User;

public class PhotoSharing implements ActionListener {
	
	private static JFrame frame;
	private static JPanel panel;
	private static JButton search, discover, profile, choosePhoto, save;
	private static JScrollPane scPane;
	private static JLabel infoLabel, defaultp, caption, message;
	private static JTextField captionText;
	private static JRadioButton makePublic, makePrivate;
	private static User user;
	private static File file;
	
	//Users can comment to other photos...
	
	public PhotoSharing(User user) {
		this.user = user; 
		frame = new JFrame();
		panel = new JPanel();
		file = null;
		
		frame.setSize(350, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel);
		panel.setLayout(null);
		
		profile = new JButton("Profile");
		profile.setBounds(10, 20, 80, 25);
		profile.addActionListener(this);
		panel.add(profile);
		
		search = new JButton("Search");
		search.setBounds(120, 20, 80, 25);
		search.addActionListener(this);
		panel.add(search);
		
		discover = new JButton("Discover");
		discover.setBounds(230, 20, 80, 25);
		discover.addActionListener(this);
		panel.add(discover);
		
		defaultp = new JLabel(new ImageIcon(Photo.getResizedImage(Photo.createImageMatrix("choose.png"),250,250).getBufferedImage()));
		defaultp.setBounds(50, 80, 250, 250);
		panel.add(defaultp);
		
		choosePhoto = new JButton("Choose Photo!");
		choosePhoto.setBounds(10, 350, 120, 25);
		choosePhoto.addActionListener(this);
		panel.add(choosePhoto);
		
		caption = new JLabel("Caption: ");
		caption.setBounds(20, 380, 80, 20);
		panel.add(caption);
		
		captionText = new JTextField(20);
		captionText.setBounds(100, 380, 165, 25);
		panel.add(captionText);
		
		makePublic = new JRadioButton("Public Photo");    
        makePublic.setBounds(10,410,300,30);      
        makePrivate = new JRadioButton("Private Photo");    
        makePrivate.setBounds(10,430,300,30);
        ButtonGroup programs = new ButtonGroup();    
        programs.add(makePublic);
        programs.add(makePrivate);    
		panel.add(makePublic);
		panel.add(makePrivate);
        
		save = new JButton("Save Changes");
		save.setBounds(10, 460, 120, 25);
		save.addActionListener(this);
		panel.add(save);
		
		message = new JLabel("");
		message.setBounds(20, 490, 300, 20);
		panel.add(message);
		
		frame.setVisible(true);
		
		
		
	}
	
	
 	
 	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== discover) {
			new DiscoverPage(user);
			frame.dispose();
		}
		
		if(e.getSource()== profile) {
			new ProfilePage(user);
			frame.dispose();
		}
		
		if(e.getSource()== search) {
			new SearchPage(user);
			frame.dispose();
		}
		
		if(e.getSource()== choosePhoto) {
			JFileChooser filechooser = new JFileChooser();
		    int response = filechooser.showOpenDialog(null);
		 	if(response == JFileChooser.APPROVE_OPTION) {
		 		file = new File(filechooser.getSelectedFile().getAbsolutePath());
		 		if(file.getPath().endsWith("jpg") || file.getPath().endsWith("png") || file.getPath().endsWith("jpeg")){
		 			
		 			defaultp.setIcon(new ImageIcon(Photo.getResizedImage(Photo.createImageMatrix(file.getPath()),250,250).getBufferedImage()));
		 			
		 			frame.setVisible(true);
		 			
		 		}
		 	}
		 	
		}
		
		if(e.getSource()== save) {
			
			try {
				if(makePublic.isSelected()) {
					Photo p = new Photo(user, captionText.getText(), file.getPath(), LocalDateTime.now(), true);
					BaseLogger.info().log(user.getNickname() + " made his/her photo public");
					new ProfilePage(user);
					frame.dispose();
				}
				else if(makePrivate.isSelected()) {
					Photo p = new Photo(user, captionText.getText(), file.getPath(), LocalDateTime.now(), false);
					BaseLogger.info().log(user.getNickname() + " made his/her photo private");
					new ProfilePage(user);
					frame.dispose();
				}
				else {
					message.setText("Please select access type.");
					BaseLogger.error().log(user.getNickname() + " didn't enter access type.");
				}
			} 
			catch(NullPointerException e1) {
				message.setText("Please upload photo");
				BaseLogger.error().log(user.getNickname() + " didn't upload photo.");
			}	
			
		}
		
	}
}
