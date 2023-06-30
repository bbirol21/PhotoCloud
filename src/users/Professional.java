package users;

public class Professional extends Hobbyist{

	public Professional(String nickname, String password, String realName, String surName, String email, int age, String pp) {
		super(nickname, password, realName, surName, email, age, pp);
		// TODO Auto-generated constructor stub
	}

	public void grayscale() {
		System.out.println("Grayscale filter was applied.");
	}
	
	public void edgeDetection() {
		System.out.println("Edge detection filter was applied.");
	}
}
