package org.example.LibraryContextPackage;

import lombok.Getter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

public class LibraryContext {
    @Getter
    private static Admin currentAdmin = null;
    @Getter
    private static CommonUser currentUser = null;
    @Getter
    private static Admin autoAdmin = null;

    private static Hashtable<Integer, Vector<CommonUser>> takenBooks = new Hashtable<>();

    private static void orderBook(Book book)
    {
        if(takenBooks.containsKey(book.getBookId()))
        {
            takenBooks.get(book.getBookId()).add(currentUser);
        }
        else
        {
            currentUser.orderBook(book);
        }

    }

    public static void LibContextInit() throws NullOrEmptyStringException, InvalidIdException {
        autoAdmin = new Admin("root", "root", "root", "root","root@admin.lib.com", 0);
        autoAdmin.addObject(autoAdmin);
        currentAdmin = autoAdmin;
    }

    static public int checkLogging(String login, char[] password) {
        for (Admin ad : Admin.getAdmins()) {
            char[] ad_password = ad.getPassword().toCharArray();
            if (login.equals(ad.getLogin()) && Arrays.equals(password, ad_password)) {
                currentAdmin = ad;
                return 1;
            }
        }
        for (CommonUser usr : Admin.getUsers()) {
            char[] usr_password = usr.getPassword().toCharArray();
            if (login.equals(usr.getLogin()) && Arrays.equals(password, usr_password)) {
                currentUser = usr;
                return 0;
            }
        }
        return -1;
    }

    static public void addObject(LibraryContextActions libObject)
    {
        currentAdmin.addObject(libObject);
    }

    static public void removeObject(LibraryContextActions libObject)
    {
        currentAdmin.removeObject(libObject);
    }

    static HashSet<LibraryContextActions> searchForObject(Isearch searchObject, String searchPattern)
    {
        HashSet<LibraryContextActions> results;
        if(currentUser != null)
        {
            results = autoAdmin.searchForObject(searchObject, searchPattern);
        }
        else
        {
            results = currentAdmin.searchForObject(searchObject, searchPattern);
        }
        return results;
    }

}
