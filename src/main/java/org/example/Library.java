package org.example;

import lombok.Data;

import java.util.LinkedList;

@Data
public class Library {
    private LinkedList<Book> bookList;
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