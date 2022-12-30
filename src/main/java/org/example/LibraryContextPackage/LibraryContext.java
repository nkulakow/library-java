package org.example.LibraryContextPackage;

import lombok.Getter;
import java.util.HashSet;
import java.util.Base64;
import java.util.Arrays;


public class LibraryContext {
    @Getter
    private static Admin currentAdmin = null;
    @Getter
    private static User currentUser = null;

    public static void LibContextInit() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException
    {
        currentAdmin = new Admin("root", "Null", "root", "root", 0, "root@admin.lib.com", 0, 0);
        Admin.getAdmins().add(currentAdmin);
        Admin.getAdmins().add(new Admin("nkulakow", "bmt1bGFrb3c=", "Nel", "Kułakowska", 101, "01169201@pw.edu.pl", 0, 1));
        Admin.getAdmins().add(new Admin("mwawrzy1", "bXdhd3J6eTE=", "Marcin", "Wawrzyniak", 102, "mail@pw.edu.pl", 0, 2));
        Admin.getAdmins().add(new Admin("mkielbus", "bWtpZWxidXM=", "Mateusz", "Kielbus", 103, "mail.@pw.edu.pl", 0, 3));
        Admin.getAdmins().add(new Admin("jhapunik", "amhhcHVuaWs=", "Janek", "Hapunik", 104, "mail@pw.edu.pl", 0 ,4));
    }

    static public boolean checkLoggingAdmins(String login, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        for (Admin ad : Admin.getAdmins()) {
            if (login.equals(ad.getLogin()) && encodedPassword.equals(ad.getPassword())) {
                currentAdmin = ad;
                return true;
            }
        }
        return false;
    }
    static public boolean checkLoggingUsers(String login, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        for(User usr: Admin.getUsers())
        {
            if(login.equals(usr.getLogin()) && encodedPassword.equals(usr.getPassword()))
            {
                currentUser = usr;
                return true;
            }
        }
        return false;
    }

    static public void addBook(Book book) {
        currentAdmin.addBook(book);
    }

    static public void addUser(User user) {
        currentAdmin.addUser(user);
    }

    static public void addAdmin(Admin admin) {
        currentAdmin.addAdmin(admin);
    }

    static public void removeBook(Book book) {
        currentAdmin.removeBook(book);
    }

    static public void removeUser(User user) {
        currentAdmin.removeUser(user);
    }

    static public void removeAdmin(Admin admin) {
        currentAdmin.removeAdmin(admin);
    }

    static public void searchForBook(String name) {
        Book book = currentAdmin.searchForBook(name);
    }

    static public void searchForBook(int id) {
        Book book = currentAdmin.searchForBook(id);
    }

    static public void searchForUser(String login) {
        User user = currentAdmin.searchForUser(login);
    }

    static public void searchForAdmin(int id) {
        Admin admin = currentAdmin.searchForAdmin(id);
    }

    static public void searchForAdmin(String login) {
        Admin admin = currentAdmin.searchForAdmin(login);
    }
}
