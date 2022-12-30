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
    @Getter
    private static HashSet<User> users;
    @Getter
    private static HashSet<Admin> admins;
    public static void LibContextInit() throws NullOrEmptyStringException, InvalidIdException
    {
        LibraryContext.books = new HashSet<>();
        LibraryContext.users = new HashSet<>();
        LibraryContext.admins = new HashSet<>();
        currentAdmin = new Admin("root", "root", "root", "root", 0, "root@admin.lib.com", 0, 0);
        Admin.getAdmins().add(currentAdmin);
        LibraryContext.admins.add(new Admin("nkulakow", "bmt1bGFrb3c=", 0));
        LibraryContext.admins.add(new Admin("mwawrzy1", "bXdhd3J6eTE=", 1));
        LibraryContext.admins.add(new Admin("mkielbus", "bWtpZWxidXM=", 2));
        LibraryContext.admins.add(new Admin("jhapunik", "amhhcHVuaWs=", 3));
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
