package org.example.LibraryContextPackage;

import org.example.Main;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;
import org.example.Database.LibraryDatabase;
import java.time.Duration;
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


    public static void LibContextInit() {
        try {
            autoAdmin = new Admin("root", "Null", "root", "root","root", 0);
            autoAdmin.addObject(autoAdmin);
            currentAdmin = autoAdmin;
            autoAdmin.addObject(currentAdmin);

            var newAdmins = LibraryDatabase.getAdmins();
            for (var admin : newAdmins){
                autoAdmin.addObject(admin);
            }

            var newUsers = LibraryDatabase.getCommonUsers();
            for(var user : newUsers){
                autoAdmin.addObject(user);
            }

        }
        catch (NullOrEmptyStringException | InvalidIdException  e){
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

    static public boolean checkLoggingAdmins(String login, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
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

        for(CommonUser usr: Admin.getUsers())
        {
            if(login.equals(usr.getLogin()) && password.equals(usr.getPassword()))
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
