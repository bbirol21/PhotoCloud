package users;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import filter.Photo;


public abstract class User {

	private String nickname;
	private String email;
	private String password;
	private String realName;
	private String surName;
	private String pp;
	private int age;
	
	public static ArrayList<User> allUsers = new ArrayList<User>();
	
	private Set<Photo> userPhotos = new TreeSet<Photo>();
	
	private Set<Photo> sharedPhotos = new TreeSet<Photo>();
	
	//private Set<Photo> sharedPhotos = new HashSet<Photo>();
	
	
	public User(String nickname, String password, String realName, String surName, String email, int age, String pp) {
		this.pp = pp;
		this.nickname = nickname;
		this.password = password;
		this.realName = realName;
		this.surName = surName;
		this.email = email;
		this.age = age;
		allUsers.add(this);
	}
	
	/**
	 * for adding the photo into the user photos
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		userPhotos.add(photo);
	}
	
	/**
	 * for sharing the added photo
	 * @param photo
	 */
	public void shareAddedPhoto(Photo photo) {
		sharedPhotos.add(photo);
		Photo.allSharedPhotos.add(photo);
	}
	/**
	 * for unsharing the added photo
	 * @param photo
	 */
	public void unshareAddedPhoto(Photo photo) {
		sharedPhotos.remove(photo);
		Photo.allSharedPhotos.remove(photo);
	}
	/**
	 * for deleting the photo requires to delete it from all of the lists
	 * @param photo
	 */
	public void deletePhoto(Photo photo) {
		userPhotos.remove(photo);
		
		if(photo.isShared()) {
			Photo.allSharedPhotos.remove(photo);
			sharedPhotos.remove(photo);
		}
	}
	
	
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public static ArrayList<User> getAllUsers() {
		return allUsers;
	}

	public static void setAllUsers(ArrayList<User> allUsers) {
		User.allUsers = allUsers;
	}

	@Override
	public String toString() {
		return "User email=" + email + ", password=" + password + ", realName=" + realName + ", surName=" + surName;
	}

	public Set<Photo> getUserPhotos() {
		// TODO Auto-generated method stub
		return userPhotos;
	}

	public String getPp() {
		return pp;
	}

	public int getAge() {
		return age;
	}

	public String getMessage() {
		return this.getClass().getSimpleName();
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	public Set<Photo> getSharedPhotos() {
		return sharedPhotos;
	}

	public void setAge(int age) {
		// TODO Auto-generated method stub
		this.age = age;
	}
	
	
	
	
}

