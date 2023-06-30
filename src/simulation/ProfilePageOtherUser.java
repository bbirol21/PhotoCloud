package simulation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import filter.Photo;
import users.User;

public class ProfilePageOtherUser implements ActionListener{
	
	private static JFrame frame;
	private static JPanel panel;
	private static JButton search, discover, logout, addPhoto, addFilter, changepp;
	private static JScrollPane scPane;
	private static JLabel infoLabel, userLabel, nameLabel, snameLabel, mailLabel, typeLabel, postLabel, ageLabel, pp;
	private static User user, otherUser;
	private static File file;
    
    public ProfilePageOtherUser(User user, User otherUser) {
		this.user = user; 
		this.otherUser = otherUser;
		
		frame = new JFrame();
		panel = new JPanel();
		
		frame.setSize(350, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel);
		panel.setLayout(null);
		
		
		logout = new JButton("Log out");
		logout.setBounds(10, 20, 80, 25);
		logout.addActionListener(this);
		panel.add(logout);
		
		search = new JButton("Search");
		search.setBounds(120, 20, 80, 25);
		search.addActionListener(this);
		panel.add(search);
		
		discover = new JButton("Discover");
		discover.setBounds(230, 20, 80, 25);
		discover.addActionListener(this);
		panel.add(discover);
		
		infoLabel = new JLabel("User Profile: ");
		Font boldFont = new Font(infoLabel.getFont().getFontName(), Font.BOLD, infoLabel.getFont().getSize());
		infoLabel.setFont(boldFont);
		infoLabel.setBounds(10, 50, 350, 15);
		
		userLabel = new JLabel("Username: " + otherUser.getNickname());
		userLabel.setBounds(120, 80, 350, 15);
		
		nameLabel = new JLabel("Name: " + otherUser.getRealName() +  " " + otherUser.getSurName() );
		nameLabel.setBounds(120, 100, 350, 15);
//		
//		mailLabel = new JLabel("E-Mail: " + user.getEmail());
//		mailLabel.setBounds(120, 120, 350, 15);
//		
//		ageLabel = new JLabel("Age: " + user.getAge());
//		ageLabel.setBounds(120, 140, 350, 15);
//		
//		typeLabel = new JLabel("User Type: " + user.getMessage());
//		typeLabel.setBounds(120, 160, 350, 15);
		
		
		
		pp = new JLabel(new ImageIcon(Photo.getResizedImage(Photo.createImageMatrix(otherUser.getPp()),100,100).getBufferedImage()));
		pp.setBounds(10, 80, 100, 100);
		
		postLabel = new JLabel("User Posts: ");
		postLabel.setFont(boldFont);
		postLabel.setBounds(10, 200, 350, 15);
		
		
		panel.add(infoLabel);
		panel.add(userLabel);
		panel.add(postLabel);
		panel.add(pp);
		panel.add(nameLabel);
		
		int i = 0;
		for (Photo p : otherUser.getSharedPhotos()) {
			JLabel imageLabel = new JLabel (new ImageIcon(p.getImageMatrix().getBufferedImage()));
			JLabel userLabel1 = new JLabel(p.getUser().getNickname());
			JLabel captionLabel = new JLabel(p.getUser().getNickname() + ": " + p.getCaption());
			JLabel date = new JLabel(p.getCreationTime());
			
			userLabel1.setBounds(10, 230+i, 350, 15); // Set the position and size of the label
			userLabel1.setFont(boldFont);
			
			imageLabel.setBounds(50, 250+i, 250, 250);
			
			captionLabel.setBounds(10, 530+i, 350, 15);
			
			date.setBounds(10, 550+i, 350, 15);
			
			panel.add(userLabel1);
			panel.add(imageLabel);
			panel.add(captionLabel);
			panel.add(date);
			
			i += 380;
		}
		int ylength = i + 400;
		
		panel.setPreferredSize(new Dimension (350, ylength));
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        frame.add(scrollPane);
		frame.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()== discover) {
			new DiscoverPage(user);
			frame.dispose();
		}
		
		if(e.getSource()== logout) {
			new LoginPage();
			frame.dispose();
		}
		
		if(e.getSource()== search) {
			new SearchPage(user);
			frame.dispose();
		}
		
	}

}
