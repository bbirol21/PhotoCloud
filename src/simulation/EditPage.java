package simulation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import filter.FilterPreference;
import filter.ImageChanger;
import filter.ImageMatrix;
import filter.Photo;
import users.User;

public class EditPage implements ActionListener {
	
	private User user;
	private static JFrame frame;
	private static JPanel panel;
	private static JButton search, discover, profile, blur, sharpen, brightness, contrast, grayscale, 
							edge, delete, publicPhoto, privatePhoto;
	private static JLabel infoLabel, infolabel2, photo;
	private Photo p;

	/**
	 * For editing the photos either deleting, making public, private or applying filters
	 * @param user
	 * @param p
	 */
	public EditPage(User user , Photo p) {
		
		this.user = user;
		this.p = p;
		frame = new JFrame();
		panel = new JPanel();
		
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
		
		
		ImageMatrix matrix = p.getImageMatrix();
		photo = new JLabel(new ImageIcon(Photo.getResizedImage(matrix, 300, 300).getBufferedImage()));
		photo.setBounds(20, 50, 300, 300);
		panel.add(photo);
		
		
		infoLabel = new JLabel("Apply Filter! ");
		Font boldFont = new Font(infoLabel.getFont().getFontName(), Font.BOLD, infoLabel.getFont().getSize());
		infoLabel.setFont(boldFont);
		infoLabel.setBounds(10, 370, 350, 15);
		
		blur = new JButton("Blur");
		blur.setBounds(5, 440, 120, 25);
		blur.addActionListener(this);
		
		sharpen = new JButton("Sharpen");
		sharpen.setBounds(5, 400, 120, 25);
		sharpen.addActionListener(this);

		brightness = new JButton("Brightness");
		brightness.setBounds(115, 440, 120, 25);
		brightness.addActionListener(this);

		contrast = new JButton("Contrast");
		contrast.setBounds(115, 400, 120, 25);
		contrast.addActionListener(this);
		
		grayscale = new JButton("Grayscale");
		grayscale.setBounds(225, 400, 120, 25);
		grayscale.addActionListener(this);
		
		edge = new JButton("Edge Detection");
		edge.setBounds(225, 440, 120, 25);
		edge.addActionListener(this);
		
		panel.add(infoLabel);
		
		
		// buttons are added to the panel according to the user type
		
		if(user.getClass().getSimpleName().equals("Free")) {
			panel.add(blur);
			panel.add(sharpen);
		}
		
		if(user.getClass().getSimpleName().equals("Hobbyist")) {
			panel.add(blur);
			panel.add(sharpen);
			panel.add(brightness);
			panel.add(contrast);
		}
		
		if(user.getClass().getSimpleName().equals("Professional")) {
			panel.add(blur);
			panel.add(sharpen);
			panel.add(brightness);
			panel.add(contrast);
			panel.add(grayscale);
			panel.add(edge);
		}
		
		if(user.getClass().getSimpleName().equals("Administrator")) {
			panel.add(blur);
			panel.add(sharpen);
			panel.add(brightness);
			panel.add(contrast);
			panel.add(grayscale);
			panel.add(edge);
		}
		
		
		
		infolabel2 = new JLabel("Photo Settings: ");
		infolabel2.setFont(boldFont);
		infolabel2.setBounds(10, 490, 350, 15);
		
		delete = new JButton("Delete Photo");
		delete.setBounds(80, 520, 200, 25);
		delete.addActionListener(this);

		publicPhoto = new JButton("Make it public");
		publicPhoto.setBounds(80, 550, 200, 25);
		publicPhoto.addActionListener(this);
		
		privatePhoto = new JButton("My eyes only :)");
		privatePhoto.setBounds(80, 580, 200, 25);
		privatePhoto.addActionListener(this);
		
		panel.add(infolabel2);
		panel.add(delete);
		panel.add(publicPhoto);
		panel.add(privatePhoto);
		
		
		panel.setPreferredSize(new Dimension (350, 650));
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        frame.add(scrollPane);
		frame.setVisible(true);
		
		
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// as it was in the other pages implementation creating a path to discover profile or search page.
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
		// for filter choices
		if(e.getSource()== blur) {
			new ImageChanger(user, p, FilterPreference.BLUR);
			frame.dispose();
		}
		
		if(e.getSource()== sharpen) {
			new ImageChanger(user, p, FilterPreference.SHARPEN);
			frame.dispose();
		}
		
		if(e.getSource()== brightness) {
			new ImageChanger(user, p, FilterPreference.BRIGHTNESS);
			frame.dispose();
		}
		
		if(e.getSource()== contrast) {
			new ImageChanger(user, p, FilterPreference.CONTRAST);
			frame.dispose();
		}
		
		if(e.getSource()== grayscale) {
			new ImageChanger(user, p, FilterPreference.GRAYSCALE);
			frame.dispose();
		}
		
		if(e.getSource()== edge) {
			new ImageChanger(user, p, FilterPreference.EDGE_DETECTION);
			frame.dispose();
		}
		
		// for deleting the photo delete method is in user class
		if(e.getSource()== delete) {
			user.deletePhoto(p);
			JOptionPane.showMessageDialog(frame, "Your photo is deleted","Deleted",JOptionPane.PLAIN_MESSAGE);
			new ProfilePage(user);
			frame.dispose();
		}
		
		// for making the photo public or private
		if(e.getSource()==publicPhoto) {
			
			//if the photo is already shared giving a warning message
			if(p.isShared()) {
				JOptionPane.showMessageDialog(frame, "Your photo was aldready public before","Public Photo",JOptionPane.PLAIN_MESSAGE);
			}
			else {
				p.setShared(true);
				user.shareAddedPhoto(p);
				JOptionPane.showMessageDialog(frame, "Your photo is now public !!!","Public Photo",JOptionPane.PLAIN_MESSAGE);
			}
			new ProfilePage(user);
			frame.dispose();
		}
		
		if(e.getSource()==privatePhoto) {
			
			if(p.isShared()) {
				p.setShared(false);
				user.unshareAddedPhoto(p);
				JOptionPane.showMessageDialog(frame, "Your photo is now private","Private Photo",JOptionPane.PLAIN_MESSAGE);
			}
			
			else {
				JOptionPane.showMessageDialog(frame, "Your photo was already private","Made Private",JOptionPane.PLAIN_MESSAGE);
			}
			new ProfilePage(user);
			frame.dispose();
		}
		
	}
}
