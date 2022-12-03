package org.example;

import lombok.Getter;
import lombok.Setter;
import java.util.LinkedList;

public class Library {
    @Getter @Setter
    private LinkedList<Book> bookList;
    @Getter @Setter
    private LinkedList<User> users;
    @Getter @Setter
    private LinkedList<Admin> admins;
    public Library()
    {
        this.bookList = null;
        this.users = null;
        this.admins.addLast(new Admin("root", "root", 0));
    }
    public static void main(String[] args) {
        Library lib = new Library();
        LoginPage loginpage = new LoginPage(600, 500);
        while (!loginpage.isLoggedIn()){
            loginpage.setVisible(true);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Exception!!");
            //tu powinien być log jakiś ale idk jak to zrobić lol
        }
        loginpage.setVisible(false);
        WelcomePage welcomepage = new WelcomePage(600, 500);
        welcomepage.setVisible(true);
    }
}