package users;

public class Free extends User{

	public Free(String nickname, String password, String realName, String surName, String email, int age, String pp) {
		super(nickname, password, realName, surName, email, age, pp);
		// TODO Auto-generated constructor stub
	}

	public void blur() {
		System.out.println("Image is blurred.");
	}
	
	public void sharpen() {
		System.out.println("Image is sharpened.");
	}
}
