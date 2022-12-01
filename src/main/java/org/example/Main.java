package org.example;

public class Main {
    public static void main(String[] args) {
        LoginPage loginpage = new LoginPage();
        while (!loginpage.isLoggedIn()){
            loginpage.setVisible(true);
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            //tu powinien być log jakiś ale idk jak to zrobić lol
        }
        loginpage.setVisible(false);
        new MyFrame(600, 500);
    }
}