package filter;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import users.Comment;
import users.User;

public class Photo implements Comparable<Photo>{
    
	public static Set<Photo> allSharedPhotos = new TreeSet<>();
    

    private User user;
    private String caption;
    private String filePath;
    private LocalDateTime creationTime;
    private boolean isShared;
    private ImageMatrix imagematrix;
    private ArrayList<Comment> comments;
    

    /**
     * 
     * Each photo object has the following parameter information
     * @param user
     * @param caption
     * @param filePath
     * @param creationTime
     * @param isShared
     * 
     */
    public Photo(User user, String caption, String filePath, LocalDateTime creationTime, boolean isShared) {
        this.user = user;
        this.caption = caption;
        this.filePath = filePath;
        this.creationTime = creationTime;
        this.isShared = isShared;
        this.setShared(isShared);
        this.imagematrix = getResizedImage(createImageMatrix(filePath), 250,250);
        comments = new ArrayList<Comment>();
        user.addPhoto(this);
        if (isShared) {
        	allSharedPhotos.add(this);
        	user.getSharedPhotos().add(this);
        }
        
        
    }

    /**
     * 
     * For creating an imagematrix to work with since applying filter requires imagematrix information
     * @param filePath
     * @return
     */
    public static ImageMatrix createImageMatrix(String filePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            return new ImageMatrix(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Set<Photo> getAllSharedPhotos() {
        return allSharedPhotos;
    }



    /**
     * 
     * For getting Resized Image
     * @param img
     * @param width
     * @param height
     * @return
     */
    public static ImageMatrix getResizedImage(ImageMatrix img, int width, int height) {
    	BufferedImage bufferedImage = img.getBufferedImage();
 	    Image scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
 	    BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
 	    Graphics2D g2d = resizedImage.createGraphics();
 	    g2d.drawImage(scaledImage, 0, 0, null);
 	    g2d.dispose();
 	    return new ImageMatrix(resizedImage);
    }

    public User getUser() {
        return user;
    }

    public String getCaption() {
        return caption;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getCreationTime() {
        return creationTime.toString();
    }

	public ImageMatrix getImageMatrix() {
		return imagematrix;
	}

	public void setImage(ImageMatrix imagematrix) {
		this.imagematrix = imagematrix;
	}
	
	/**
	 * For sorting the photo list according to the creation date
	 */
	@Override
	public int compareTo(Photo o) {
		// TODO Auto-generated method stub
		return o.getCreationTime().compareTo(this.getCreationTime());
	}

	public boolean isShared() {
		return isShared;
	}

	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	public void addComment(Comment comment) {
        comments.add(comment);
    }
}
