package users;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import exceptions.NoCommentException;
import filter.Photo;

public class Comment  {
    private User sender;
    private User receiver;
    private String message;
    private LocalDateTime timeSent;
    private Photo p;

    public Comment(User sender, Photo photo, String message) {
        this.sender = sender;
        this.receiver = photo.getUser();
        this.message = message;
        this.p = photo;
        this.timeSent = LocalDateTime.now();
        photo.addComment(this);
        
       
        	
    }

    // Getters and setters for the properties

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    // Format the time to hours and minutes only
    public String getFormattedTimeSent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timeSent.format(formatter);
    }

	@Override
	public String toString() {
		return sender.getNickname() + ": " + message;
	}
    
    
}