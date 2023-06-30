package users;

public class Hobbyist extends Free{

	public Hobbyist(String nickname, String password, String realName, String surName, String email, int age, String pp) {
		super(nickname, password, realName, surName, email, age, pp);
		// TODO Auto-generated constructor stub
	}

	public void brightness() {
		System.out.println("Brighntess was changed.");
	}
	
	public void contrast() {
		System.out.println("Contrast was changed.");
	}
}




   