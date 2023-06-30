package simulation;
import users.*;
import java.time.format.DateTimeFormatter;  

import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import filter.Photo;


/*
/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own
without any help from anyone else. The effort in the project thus belongs
completely to me. I did not search for a solution, or I did not consult any
program written by others or did not copy any program from other sources. I
read and followed the guidelines provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Bartu Birol, 0079227>
*************************************************************************/

public class Main {
	
	
	public static void main(String[] args) {
		
		
		Administrator admin = new Administrator("123", "123", "123", "123", "123", 123, "pp.png");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		
		Professional prof1 = new Professional("haluk_erenler", "123", "Haluk", "Erenler", "haluk@gmail", 21, "pp.png");
		Professional prof2 = new Professional("guneest", "123", "Güneş", "Tüfekçi", "guneest@gmail.com", 18, "pp.png");
		Professional prof3 = new Professional("bartu_birol", "123", "Bartu", "Birol", "birolbartu@gmail.com", 20, "bartu_foto.jpeg");
		Professional prof4 = new Professional("nfa26", "123", "Nurkan Fatih", "Altunel", "altnur21@robcol.k12.tr", 21, "pp.png");
		
		
		Hobbyist hob1 = new Hobbyist("eren_badur", "123", "Eren", "Badur", "badere21@robcol.k12.tr", 21, "pp.png");
		Hobbyist hob2 = new Hobbyist("sebnembirol", "123", "Sebnem" , "Birol", "birolsebnem@gmail.com", 51, "pp.png");
		Hobbyist hob3 = new Hobbyist("kemalguzel16", "123", "Kemal" , "Güzel", "kguzel", 21, "pp.png");
		Hobbyist hob4 = new Hobbyist("ezgi", "123", "ezgi", "mih", "aasdf", 21, "pp.png");
		
		Free f1 = new Free("ipek", "123", "İpek", "Yavuz", "iyavuz@gmail.com", 20, "ipek1.png");
		Free f2 = new Free("simay", "123", "Simay", "Öğün", "sogun@ku", 21, "simay1.png");
		Free f3 = new Free("basakbirolx", "123", "Başak" , "Birol", "hiIambasakbirol", 15, "pp.png");
	
		Photo p1 = new Photo(prof1, "cok zekiyim", "haluk.png", LocalDateTime.now(), true);
		Photo p2 = new Photo(prof3, "<3", "gunes.jpeg", LocalDateTime.now(), true);
		Photo p3 = new Photo(prof3, "Resmi bir foto", "bartu_foto.jpeg", LocalDateTime.parse("2022/05/03 12:40",dtf), true);
		Photo p4 = new Photo(prof4, "Güzel zamanlar", "nfa.jpg", LocalDateTime.now(), true);
		Photo p5 = new Photo(hob1, "İste ben", "b2.png", LocalDateTime.now(), true);
		Photo p6 = new Photo(hob2, "Neşeli", "sebo.png", LocalDateTime.now(), true);
		Photo p7 = new Photo(hob3, "Kemily in Paris", "kg.png", LocalDateTime.now(), true);
		Photo p8 = new Photo(hob4, "sanat eseri benim", "ezgi.png", LocalDateTime.parse("1984/05/03 12:40",dtf), true);
		Photo p9 = new Photo(f1, "havali...", "ipek3.png", LocalDateTime.parse("2021/05/03 12:40",dtf), true);
		Photo p10 = new Photo(f2, "guzel manzara", "simay1.png", LocalDateTime.parse("2021/08/12 12:40",dtf), true);
		Photo p11 = new Photo(f3, "basaaaaaaak", "basak.png",LocalDateTime.parse("1996/05/03 12:40",dtf), true );
		Photo p12 = new Photo(prof3, "rossiiiiii", "rossi1.png",LocalDateTime.now(), true);
		Photo p13 = new Photo(prof2, "funny photo", "gt.jpeg",LocalDateTime.parse("2020/05/03 09:40",dtf), true);
		
		LoginPage login = new LoginPage();

	
	}	
		
}
	
	
