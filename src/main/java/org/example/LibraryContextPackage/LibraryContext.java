package org.example.LibraryContextPackage;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import org.example.Database.LibraryDatabase;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.example.GUI.LibraryGUI;

public class LibraryContext {
    @Getter
    private static Admin currentAdmin = null;
    @Getter
    private static CommonUser currentUser = null;
    @Getter
    private static Admin autoAdmin = null;

    private static float penalty = 0.5f;
    @Getter
    private static Hashtable<Integer, ArrayDeque<CommonUser>> takenBooks = new Hashtable<>();
    @Getter
    private static Hashtable<Integer, ArrayDeque<Long>> takenBooksOrderedTime = new Hashtable<>();
    private static final Logger logger = LogManager.getLogger(org.example.LibraryContextPackage.LibraryContext.class);
    public static void orderBook(Book book, long months)
    {
        if(takenBooks.containsKey(book.getBookId()))
        {
            takenBooks.get(book.getBookId()).add(currentUser);
            takenBooksOrderedTime.get(book.getBookId()).add(months);
        }
        else
        {
            currentUser.orderBook(book, months);
            takenBooks.put(book.getBookId(), new ArrayDeque<>());
            takenBooksOrderedTime.put(book.getBookId(), new ArrayDeque<>());
        }

    }

    public static void LibContextInitForTests(boolean AsUser) throws InvalidBookNumberException {
        try {
            LibraryDatabase.initLoginInfoForTests();


            autoAdmin = null;
            currentAdmin = null;
            currentUser = null;

            autoAdmin = new Admin("root", "Null", "root", "root","root", 0);
            Admin.clearAll();
            autoAdmin.addObject(autoAdmin);
            currentAdmin = autoAdmin;
            autoAdmin.addObject(currentAdmin);
            if(AsUser){
                currentUser = new CommonUser("user", "Null", "user", "Null", 1, "mail", 0);
                autoAdmin.addObject(currentUser);
            }


        }
        catch (NullOrEmptyStringException | InvalidIdException e){
            logger.error("Error in LibContextInit: " + e.getMessage());
            LibraryGUI.sendMessageToLoginPage("Cannot initialize login process. Please exit and refer to all_logs.log file.");
        }
    }

    public static void LibContextInit() {
        try {
            LibraryDatabase.initLoginInfo();

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

            var newBooks = LibraryDatabase.getBooks();
            for(var book : newBooks){
                autoAdmin.addObject(book);
            }
            logger.info("Executed LibraryContextInit.");
        }
        catch (NullOrEmptyStringException | InvalidIdException | SQLException | InvalidBookNumberException |IOException e){
            logger.error("Error in LibContextInit: " + e.getMessage());
            LibraryGUI.sendMessageToLoginPage("Cannot initialize login process. Please exit and refer to all_logs.log file.");
        }

    }
    public static void returnBook(Book book) {
        ArrayDeque<CommonUser> users = takenBooks.get(book.getBookId());
        users.remove();
        if (users.isEmpty()) {
            takenBooks.remove(book.getBookId());
            takenBooksOrderedTime.remove(book.getBookId());
            currentUser.returnBook(book);
        }
        else {
            currentUser.returnBook(book);
            try
            {
                book.setUserId(takenBooks.get(book.getBookId()).remove().getUserId());
            }
            catch (InvalidIdException e)
            {
                logger.warn("Exception in return Book: " + e.getMessage());
            }
            book.setReturnDate(ZonedDateTime.now().plusMonths(takenBooksOrderedTime.get(book.getBookId()).remove()));

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
                if(diff == 0)
                {
                    penalty = 1*LibraryContext.penalty;
                }
                else
                {
                    penalty = diff*LibraryContext.penalty;
                }
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
                currentAdmin = autoAdmin;
                logger.info("Logged in as a User.");
                return true;
            }
        }
        return false;
    }

    static public boolean addObject(LibraryContextActions libObject)
    {
        if (currentAdmin.addObject(libObject)) {
            if (libObject.getClass().equals(CommonUser.class)) {
                try {
                    LibraryDatabase.addUser((CommonUser) libObject);
                    logger.info("Added user to DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove user in DB");
                    return false;
                }
            }
            if (libObject.getClass().equals(Book.class)) {
                try {
                    LibraryDatabase.addBook((Book) libObject);
                    logger.info("Added user to DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove user in DB");
                    return false;
                }
            }
        }
        return true;
    }

    static public boolean removeObject(LibraryContextActions libObject)
    {
        if (currentAdmin.removeObject(libObject)) {
            if (libObject.getClass().equals(CommonUser.class)) {
                try {
                    LibraryDatabase.removeUser((CommonUser) libObject);
                    logger.info("Removed user from DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove user in DB");
                    return false;
                }
            }
            if (libObject.getClass().equals(Book.class)) {
                try {
                    LibraryDatabase.removeBook((Book) libObject);
                    logger.info("Removed book from DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove book in DB");
                    return false;
                }
            }
        }
        return true;
    }

    static public HashSet<LibraryContextActions> searchForObject(Isearch searchObject, String searchPattern)
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

    static public boolean modifyFewUserAttributes(Map<AttributesNames, String> attributes, int userId) throws InvalidBookNumberException, NullOrEmptyStringException, InvalidIdException {
        CommonUser user;
        try {
            user = Admin.findUserById(userId);
        } catch (InvalidIdException e) {
            logger.error("Could not find user by id");
            return false;
        }
        for (var attributeName : attributes.keySet()){
            user.modifyUser(attributeName, attributes.get(attributeName));
        }
        try{
            LibraryDatabase.modifyCommonUser(user);
        } catch (SQLException e) {
            logger.warn("Could not execute query in DB in modifyFewUserAttributes "+ e.getMessage());
            return false;
        }
        return true;
    }

    static public boolean modifyFewBookAttributes(Map<AttributesNames, String> attributes, int bookId) throws InvalidBookNumberException, NullOrEmptyStringException, InvalidIdException {
        Book book;
        try {
            book = Admin.findBookById(bookId);
        } catch (InvalidIdException e) {
            logger.error("Could not find book by id");
            return false;
        }
        for (var attributeName : attributes.keySet()){
            book.modifyBook(attributeName, attributes.get(attributeName));
        }
        try{
            LibraryDatabase.modifyBook(book);
        } catch (SQLException e) {
            logger.warn("Could not execute query in DB in modifyFewBookAttributes "+ e.getMessage());
            return false;
        }
        return true;
    }

    static public boolean modifyUser(AttributesNames attributeName, String modifiedVal, User modifiedUser) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {
        modifiedUser.modifyUser(attributeName, modifiedVal);
        try{
        if (currentUser != null || modifiedUser.getClass().equals(CommonUser.class))
        {
            LibraryDatabase.modifyCommonUser((CommonUser) modifiedUser);
        }
        else {
            LibraryDatabase.modifyAdmin((Admin) modifiedUser);
        }}
        catch (SQLException e){
            logger.warn("Could not execute query in DB in modifyUser "+ e.getMessage());
            return false;
        }
        return true;
    }

    static public boolean modifyBook(AttributesNames attributeName, String modifiedVal, Book modifiedBook) throws NullOrEmptyStringException, InvalidIdException {
        modifiedBook.modifyBook(attributeName, modifiedVal);
        try{
            LibraryDatabase.modifyBook(modifiedBook);
        } catch (SQLException e) {
            logger.warn("Could not execute query in DB in modifyBook "+ e.getMessage());
            return false;
        }
        return true;
    }
    static public Vector<String> showUsers(){
        Vector<String> users_rep = new Vector<>();
        for (var user: Admin.getUsers()){
            users_rep.add(user.describe());
        }
        return users_rep;
    }


}
