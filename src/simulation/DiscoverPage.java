package simulation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import exceptions.NoCommentException;
import filter.Photo;
import users.Comment;
import users.User;

public class DiscoverPage implements ActionListener{

	//Photo Grid Layout
	//Photo Interaction
	//User Profile Access
	
	private static JFrame frame;
	private static JPanel panel;
	private static JButton search, profile, logout;
	private static JScrollPane scPane;
	private static User user;
	private int i;

	
	/**
	 * It is important to have an user parameter in order to go directly to the profile page of the main user
	 * @param user
	 */
	public DiscoverPage(User user) {
		
		this.user = user;
		frame = new JFrame();
		panel = new JPanel();
		
		frame.setSize(350, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel);
		panel.setLayout(null);
		
		logout = new JButton("Log Out");
		logout.setBounds(10, 20, 80, 25);
		logout.addActionListener(this);
		panel.add(logout);
		
		search = new JButton("Search");
		search.setBounds(120, 20, 80, 25);
		search.addActionListener(this);
		panel.add(search);
		
		profile = new JButton("Profile");
		profile.setBounds(230, 20, 80, 25);
		profile.addActionListener(this);
		panel.add(profile);
		
		i = 0;
		
		// iterates over all shared photos inside the program
		for (Photo p : Photo.allSharedPhotos) {
			JLabel imageLabel = new JLabel (new ImageIcon(p.getImageMatrix().getBufferedImage()));
			
			JButton userButton = new JButton(p.getUser().getNickname());
			JButton deleteButton = new JButton("delete");
			
			JLabel captionLabel = new JLabel(p.getUser().getNickname() + ": " + p.getCaption());
			JLabel date = new JLabel(p.getCreationTime());
			JLabel pp = new JLabel(new ImageIcon(Photo.getResizedImage(Photo.createImageMatrix(p.getUser().getPp()),40,40).getBufferedImage()));
			JButton commentsButton = new JButton("Comments:");
			JTextField commentText = new JTextField();
			JButton sendButton = new JButton("Send");
			
			pp.setBounds(10, 50+i, 40, 40);
			
			userButton.setBounds(60, 60+i, 200, 25); // Set the position and size of the label
			Font boldFont = new Font(userButton.getFont().getFontName(), Font.BOLD, userButton.getFont().getSize());
			userButton.setFont(boldFont);
			
			
			imageLabel.setBounds(50, 95+i, 250, 250);
			captionLabel.setBounds(10, 350+i, 350, 15);
			deleteButton.setBounds(220, 350+i, 80, 25);
			date.setBounds(10, 370+i, 360, 15);
			commentText.setBounds(10, 390+i, 230, 25);
			sendButton.setBounds(230, 390+i, 80, 25);
			commentsButton.setBounds(10, 420+i, 100, 25);
			
			//to direct through the desired user's profile page
			userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	if (e.getActionCommand().equals(user.getNickname())) {
                		new ProfilePage(user);
                		frame.dispose();
                	}
                	else {
                		new ProfilePageOtherUser(user, p.getUser());
                		frame.dispose();
                	}
                    
                }
            });
			
			// method specialized for the administrator to delete photos
			deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	Photo.allSharedPhotos.remove(p);
                	p.getUser().getSharedPhotos().remove(p);
                	p.getUser().getUserPhotos().remove(p);
                	frame.dispose();
                	new DiscoverPage(user);
                    
                }
            });
			
			// method for sending comments
			sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					 
					try {
						if (commentText.getText().equals("")) {
				        	throw new NoCommentException("no comment");
				        }
						
						String message = commentText.getText();
						Comment comment = new Comment(user, p, message);
						p.addComment(comment);
				        commentText.setText("");
				        
					}
					catch(NoCommentException e1) {
						JOptionPane.showMessageDialog(frame, "Please make a comment","No comment",JOptionPane.PLAIN_MESSAGE);
					}

			        
			    }
			});
			

			// method for displaying the comments
			commentsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					new CommentsPage(user, p);
					frame.dispose();
			    }
			});
			
			if(user.getClass().getSimpleName().equals("Administrator")) {
				panel.add(deleteButton);
			}
			
			panel.add(userButton);
			panel.add(imageLabel);
			panel.add(captionLabel);
			panel.add(date);
			panel.add(pp);
			panel.add(sendButton);
			panel.add(commentText);
			panel.add(commentsButton);

			i += 450;
			
		}
		
		int ylength = i + 100;
		
		// scroll pane implementation according to the number of photos added
		panel.setPreferredSize(new Dimension (350, ylength));

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        frame.add(scrollPane);
		frame.setVisible(true);
		
		
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()== logout) {
			new LoginPage();
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
