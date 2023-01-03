package org.example.LibraryContextPackage;

import lombok.Getter;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LibraryContext {
    @Getter
    private static Admin currentAdmin = null;
    @Getter
    private static CommonUser currentUser = null;
    @Getter
    private static Admin autoAdmin = null;

    private static float penalty = 0.5f;

    private static Hashtable<Integer, ArrayDeque<CommonUser>> takenBooks = new Hashtable<>();

    public static void orderBook(Book book, long months)
    {
        if(takenBooks.containsKey(book.getBookId()))
        {
            takenBooks.get(book.getBookId()).add(currentUser);
        }
        else
        {
            currentUser.orderBook(book, months);
            takenBooks.put(currentUser.getUserId(), new ArrayDeque<>());
        }

    }

    public static void returnBook(Book book)
    {
        ArrayDeque<CommonUser> users = takenBooks.get(book.getBookId());
        users.remove();
        if(users.isEmpty())
        {
            takenBooks.remove(book.getBookId());
        }
        currentUser.returnBook(book);

    }

    public static Hashtable<Integer, Float> showPenalties()
    {
        float penalty = 0;
        Hashtable<Integer, Float> penalties = new Hashtable<>();
        HashSet<Book> books = currentUser.showBooks(autoAdmin);
        for(Book book:books)
        {
            Duration anydiff = Duration.between(book.getReturnDate(), ZonedDateTime.now());
            long diff = ChronoUnit.DAYS.between(book.getReturnDate(), ZonedDateTime.now());
            if(anydiff.isPositive())
            {
                penalty = diff*LibraryContext.penalty;
            }
            penalties.put(book.getBookId(), penalty);
        }
        return penalties;
    }

    private static HashSet<Book> showBooks()
    {
        if(currentUser != null)
        {
            return currentUser.showBooks(autoAdmin);
        }
        return  null;
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
