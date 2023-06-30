package simulation;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import filter.Photo;
import users.Free;
import users.Hobbyist;
import users.Professional;
import users.User;

public class ProfilePage implements ActionListener {
	
	private static JFrame frame;
	private static JPanel panel;
	private static JButton search, discover, logout, addPhoto, addFilter, changepp, edit, modify;
	private static JScrollPane scPane;
	private static JLabel infoLabel, userLabel, nameLabel, snameLabel, mailLabel, typeLabel, postLabel, ageLabel, pp;
	private static User user;
	private static File file;
	
	public ProfilePage(User user) {
		this.user = user; 
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
		
		infoLabel = new JLabel("My Profile: ");
		Font boldFont = new Font(infoLabel.getFont().getFontName(), Font.BOLD, infoLabel.getFont().getSize());
		infoLabel.setFont(boldFont);
		infoLabel.setBounds(10, 50, 350, 15);
		
		userLabel = new JLabel("Username: " + user.getNickname());
		userLabel.setBounds(120, 80, 350, 15);
		
		nameLabel = new JLabel("Name: " + user.getRealName() +  " " + user.getSurName() );
		nameLabel.setBounds(120, 100, 350, 15);
		
		mailLabel = new JLabel("E-Mail: " + user.getEmail());
		mailLabel.setBounds(120, 120, 350, 15);
		
		ageLabel = new JLabel("Age: " + user.getAge());
		ageLabel.setBounds(120, 140, 350, 15);
		
		typeLabel = new JLabel("User Type: " + user.getMessage());
		typeLabel.setBounds(120, 160, 350, 15);
		
		pp = new JLabel(new ImageIcon(Photo.getResizedImage(Photo.createImageMatrix(user.getPp()),100,100).getBufferedImage()));
		pp.setBounds(10, 80, 100, 100);
		
		changepp = new JButton("Change Pp");
		changepp.setBounds(10, 200, 150, 25);
		changepp.addActionListener(this);
		
		addPhoto = new JButton("Add Photo!");
		addPhoto.setBounds(10, 230, 150, 25);
		addPhoto.addActionListener(this);
		
		modify = new JButton("Modify Profile");
		modify.setBounds(10, 260, 150, 25);
		modify.addActionListener(this);
		
		postLabel = new JLabel("My Posts: ");
		postLabel.setFont(boldFont);
		postLabel.setBounds(10, 330, 350, 15);
		
		
		panel.add(infoLabel);
		panel.add(userLabel);
		panel.add(mailLabel);
		panel.add(ageLabel);
		panel.add(typeLabel);
		panel.add(postLabel);
		panel.add(addPhoto);
		panel.add(changepp);
		panel.add(pp);
		panel.add(nameLabel);
		panel.add(modify);
		
		int i = 0;
		for (Photo p : user.getUserPhotos()) {
			JLabel imageLabel = new JLabel (new ImageIcon(p.getImageMatrix().getBufferedImage()));
			JLabel userLabel1 = new JLabel(p.getUser().getNickname());
			JLabel captionLabel = new JLabel(p.getUser().getNickname() + ": " + p.getCaption());
			JLabel date = new JLabel(p.getCreationTime());
			
			userLabel1.setBounds(10, 360+i, 350, 15); // Set the position and size of the label
			userLabel1.setFont(boldFont);
			
			imageLabel.setBounds(50, 380+i, 250, 250);
			
			captionLabel.setBounds(10, 660+i, 350, 15);
			
			date.setBounds(10, 680+i, 350, 15);
			
			edit = new JButton("Edit");
			edit.setBounds(120, 635+i, 100, 25);
			
			edit.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
            	
					if (e.getActionCommand().equals(user.getNickname())) {
						new ProfilePage(user);
						frame.dispose();
					}
					else {
						new EditPage(user, p);
						frame.dispose();
					}
				}
			});
			
			panel.add(userLabel1);
			panel.add(imageLabel);
			panel.add(captionLabel);
			panel.add(date);
			panel.add(edit);
			
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
		
		if(e.getSource()== addPhoto) {
	       new PhotoSharing(user);     
	       frame.dispose();
		}
		
		if(e.getSource()== changepp) {
			JFileChooser filechooser = new JFileChooser();
		    int response = filechooser.showOpenDialog(null);
		 	if(response == JFileChooser.APPROVE_OPTION) {
		 		file = new File(filechooser.getSelectedFile().getAbsolutePath());
		 		if(file.getPath().endsWith("jpg") || file.getPath().endsWith("png") || file.getPath().endsWith("jpeg")){
		 			
		 			//defaultp = new JLabel(new ImageIcon(Photo.getResizedImage(file.getPath(),200,200).getBufferedImage()));
		 			pp.setIcon(new ImageIcon(Photo.getResizedImage(Photo.createImageMatrix(file.getPath()),100,100).getBufferedImage()));
		 			user.setPp(file.getPath());
		 			//frame.setVisible(true);
		 			
		 		}
		 	}
		}
		
		if(e.getSource()== modify) {
			new ModifyPage(user);
		}
		
		
		
	}
	
	
}
