package simulation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import filter.Photo;
import users.Comment;
import users.User;

public class CommentsPage implements ActionListener{
	
	
	private static JFrame frame;
	private static JPanel panel;
	private static JButton search, discover, profile, choosePhoto, save;
	private static JScrollPane scPane;
	private static JLabel infoLabel, defaultp, caption, message, imageLabel;
	private static JTextField captionText;
	private static JRadioButton makePublic, makePrivate;
	private static User user;
	private static File file;
	
	private Photo p;
	/**
	 * 
	 * @param user
	 * @param p
	 */
	public CommentsPage(User user, Photo p) {
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
		
		int j = 0;
        
		imageLabel = new JLabel (new ImageIcon(p.getImageMatrix().getBufferedImage()));
		imageLabel.setBounds(50, 95, 250, 250);
		panel.add(imageLabel);
		
        //iterating through the comments created for the desired photo
        for (int i=0; i<p.getComments().size(); i+=2 ) {
        	JLabel commentLabel = new JLabel(p.getComments().get(i).toString());
        	commentLabel.setBounds(10, 380+j, 350, 25);
        	panel.add(commentLabel);
        	j += 20;
        }
        //if there are no comments there is a message
        if(p.getComments().size() == 0) {
        	JLabel noCommentsLabel = new JLabel("No comments");
        	noCommentsLabel.setBounds(10, 380, 350, 25);
        	panel.add(noCommentsLabel);
        }
        
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
	}

}
