package simulation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import users.User;

public class SearchPage implements ActionListener {

	private JFrame frame;
    private JPanel panel, resultsPanel;
    private JButton discover, search, profile, sb;
    private JTextField userText;
    private User user;
    
    public SearchPage(User user)  {
    	
    	this.user = user; 
		frame = new JFrame();
		panel = new JPanel();
		resultsPanel = new JPanel();
		
		frame.setSize(350, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel);
		panel.setLayout(null);
		
		
		discover = new JButton("Discover");
		discover.setBounds(10, 20, 80, 25);
		discover.addActionListener(this);
		panel.add(discover);
		
		search = new JButton("Search");
		search.setBounds(120, 20, 80, 25);
		search.addActionListener(this);
		panel.add(search);
		
		profile = new JButton("Profile");
		profile.setBounds(230, 20, 80, 25);
		profile.addActionListener(this);
		panel.add(profile);
		
		userText = new JTextField(20);
		userText.setBounds(20, 50, 275, 25);
		panel.add(userText);
    	
		sb = new JButton("Find!");
		sb.setBounds(85, 75, 150, 25);
		
		
		panel.add(sb);
		panel.setPreferredSize(new Dimension (350, 600));
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        frame.add(scrollPane);
		
        
        ActionListener searchListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = userText.getText().toLowerCase();
                // Clear previous search results
                int i = 0;

                // Iterate through the user list and check for matches
                for (User otherUser : User.allUsers) {
                	
                	boolean alreadyExists = false;
                    for (Component component : panel.getComponents()) {
                        if (component instanceof JButton && ((JButton) component).getText().equals(otherUser.getNickname())) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (alreadyExists) {
                        continue; // Skip creating the button if it already exists
                    }
                	
                    String username = otherUser.getNickname().toLowerCase();
                    if (username.startsWith(searchTerm)) {
                        JButton userButton = new JButton(otherUser.getNickname());
                        userButton.setBounds(20, 120+i, 150, 25);
                        i += 30;
                        
                        userButton.addActionListener(new ActionListener() {
                        	
                        	
                            @Override
                            public void actionPerformed(ActionEvent e) {
                            	
                            	if (e.getActionCommand().equals(user.getNickname())) {
                            		new ProfilePage(user);
                            		frame.dispose();
                            	}
                            	else {
                            		new ProfilePageOtherUser(user, otherUser);
                            		frame.dispose();
                            	}
                                
                            }
                        });
                        panel.add(userButton);
                        
                    }
                    
                }

                // Refresh the panel to display new search results
                panel.revalidate();
                panel.repaint();
            }
        };
        sb.addActionListener(searchListener);
		
		
		
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
