package org.example.LibraryContextPackage;

import org.example.Main;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;

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
    private static final Logger logger = LogManager.getLogger(org.example.LibraryContextPackage.LibraryContext.class);
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


    public static void LibContextInit()
    {
        try {
            autoAdmin = new Admin("root", "root", "root", "root","root@admin.lib.com", 0);
            autoAdmin.addObject(autoAdmin);
            currentAdmin = autoAdmin;


            Admin.getAdmins().add(currentAdmin);
            Admin.getAdmins().add(new Admin("nkulakow", "bmt1bGFrb3c=", "Nel", "Kułakowska", 101, "01169201@pw.edu.pl", 0, 1));
            Admin.getAdmins().add(new Admin("mwawrzy1", "bXdhd3J6eTE=", "Marcin", "Wawrzyniak", 102, "mail@pw.edu.pl", 0, 2));
            Admin.getAdmins().add(new Admin("mkielbus", "bWtpZWxidXM=", "Mateusz", "Kielbus", 103, "mail.@pw.edu.pl", 0, 3));
            Admin.getAdmins().add(new Admin("jhapunik", "amhhcHVuaWs=", "Janek", "Hapunik", 104, "mail@pw.edu.pl", 0, 4));
            // admin. get admins from db; admin.get users from db
        }
        catch (NullOrEmptyStringException | InvalidIdException | InvalidBookNumberException e){
            logger.error("Error in LibContextInit: " + e.getMessage());
            Main.exit();
        }
    }
    public static void returnBook(Book book) {
        ArrayDeque<CommonUser> users = takenBooks.get(book.getBookId());
        users.remove();
        if (users.isEmpty()) {
            takenBooks.remove(book.getBookId());
        }
        currentUser.returnBook(book);
    }

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

    static public boolean checkLoggingAdmins(String login, char[] password) {
        for (Admin ad : Admin.getAdmins()) {
            if (login.equals(ad.getLogin()) && encodedPassword.equals(ad.getPassword())) {
                currentAdmin = ad;
                logger.info("Logged in as an Admin.");
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
                logger.info("Logged in as a User.");
                return true;
            }
        }
        return false;
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
