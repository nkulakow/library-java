package org.example.LibraryContextPackage;

import lombok.Getter;
import java.util.Arrays;


public class LibraryContext {
    @Getter
    private static Admin currentAdmin = null;
    @Getter
    private static User currentUser = null;

    public static void LibContextInit() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {
        currentAdmin = new Admin("root", "root", "root", "root", 0, "root@admin.lib.com", 0, 0);
    }

    static public int checkLogging(String login, char[] password) {
        for (Admin ad : Admin.getAdmins()) {
            char[] ad_password = ad.getPassword().toCharArray();
            if (login.equals(ad.getLogin()) && Arrays.equals(password, ad_password)) {
                currentAdmin = ad;
                return 1;
            }
        }
        for (User usr : Admin.getUsers()) {
            char[] usr_password = usr.getPassword().toCharArray();
            if (login.equals(usr.getLogin()) && Arrays.equals(password, usr_password)) {
                currentUser = usr;
                return 0;
            }
        }
        return -1;
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
