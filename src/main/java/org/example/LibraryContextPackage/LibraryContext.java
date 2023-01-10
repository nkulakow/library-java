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

    public static void orderBook(Book book, long months) throws CannotConnectToDBException
    {
        if(takenBooks.containsKey(book.getBookId()))
        {
            takenBooks.get(book.getBookId()).add(currentUser);
            takenBooksOrderedTime.get(book.getBookId()).add(months);
            try {
                LibraryDatabase.addWaiting(book, months);
                logger.info("Added book to WAITING");
            }
            catch (SQLException e){
                logger.warn("Could not add book to WAITING");
                throw new CannotConnectToDBException("Could not make changes in DB");
            }
            logger.info("Ordered book and got into queue.");
        }
        else
        {
            currentUser.orderBook(book, months);
            takenBooks.put(book.getBookId(), new ArrayDeque<>());
            takenBooksOrderedTime.put(book.getBookId(), new ArrayDeque<>());
            book.setUserId(currentUser.getUserId()); // ?? czemu integer w jednym a w drugim int
            LibraryDatabase.modifyBook(book);
            logger.info("Borrowed book.");
        }
    }

    /**
    * Initializes LibContext specifically for tests - without allowing to connect do DB and without any Gui initialization.
     */
    public static void LibContextInitForTests(boolean AsUser) throws InvalidBookNumberException {
        try {
            LibraryDatabase.initLoginInfoForTests();

            autoAdmin = null;
            currentAdmin = null;
            currentUser = null;
            Admin.clearAll();
            autoAdmin = new Admin("root", "Null", "root", "root","root", 0);
            autoAdmin.addObject(autoAdmin);
            currentAdmin = autoAdmin;
            autoAdmin.addObject(currentAdmin);

            if(AsUser){
                currentUser = new CommonUser("user", "Null", "user", "Null", 1, "mail", 0);
                currentAdmin.addObject(currentUser);
            }
        }
        catch (NullOrEmptyStringException | InvalidIdException | InvalidLoginException e){
            logger.error("Error in LibContextInitForTests: " + e.getMessage());
        }
    }

    /**
     * Initializes LibContext. Using LibraryDatabase gets Admins, Books, Users and books orders info and creates objects corresponding to them.
     */
    public static void LibContextInit() {
        try {
            LibraryDatabase.initLoginInfo();

            autoAdmin = new Admin("root", "Null", "root", "root","root", 0);
            autoAdmin.addObject(autoAdmin);
            currentAdmin = autoAdmin;
            autoAdmin.addObject(currentAdmin);

            LibraryDatabase.initWaiting();

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

            takenBooks = LibraryDatabase.getTakenBooks();
            takenBooksOrderedTime = LibraryDatabase.getTakenBooksOrderedTime();

            logger.info("Executed LibraryContextInit.");

        }
        catch (NullOrEmptyStringException | InvalidIdException | SQLException | InvalidBookNumberException |
               IOException | InvalidLoginException e){
            logger.error("Error in LibContextInit: " + e.getMessage());
            LibraryGUI.sendMessageToLoginPage("Cannot initialize login process. Please exit and refer to all_logs.log file.");
        }

    }

    /**
     * Allows currentUser to return borrowed Book.
     */
    public static void returnBook(Book book) throws CannotReturnBookException, CannotConnectToDBException {
        try{
        ArrayDeque<CommonUser> users = takenBooks.get(book.getBookId());
        if (users.isEmpty()) {
            takenBooks.remove(book.getBookId());
            takenBooksOrderedTime.remove(book.getBookId());
            currentUser.returnBook(book);
            book.setUserId(null); // albo 0, wywala blad niewazne co
            LibraryDatabase.modifyBook(book);

            logger.info("Returned book, it is now available.");
        }
        else {
            currentUser.returnBook(book);
            try {
                book.setUserId(takenBooks.get(book.getBookId()).remove().getUserId());
            } catch (InvalidIdException e) {
                logger.warn("Exception in return Book: " + e.getMessage());
            }
            book.setReturnDate(ZonedDateTime.now().plusMonths(takenBooksOrderedTime.get(book.getBookId()).remove()));
            logger.info("Returned book, it is now borrowed by next user.");
        }
            try {
                LibraryDatabase.removeWaiting(book);
                logger.info("Removed book from WAITING");
            }
            catch (SQLException e){
                logger.warn("Could not remove book in WAITING");
                throw new CannotConnectToDBException("Could not make changes in DB");
            }
        }
        catch (NoSuchElementException | java.lang.NullPointerException e){
            logger.error("In returnBook: " + e.getMessage());
            throw new CannotReturnBookException("Exception in returnBook occurred.");
        }

    }

    /**
     * Returns hash table of penalties for borrowed by currentUser books
     */
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

    /**
     * Returns true if Admins login information are correct. Sets currentAdmin to the one logged in.
     */
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

    /**
     * Returns true if Users login information are correct. Sets currentUser to the one logged in.
     */
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

    /**
     * Add Object to local data and to Database. Returns true if object was added to Database, false if only locally.
     */
    static public void addObject(LibraryContextActions libObject) throws CannotConnectToDBException {
        if (currentAdmin.addObject(libObject)) {
            logger.info("Added object locally.");
            if (libObject.getClass().equals(CommonUser.class)) {
                try {
                    LibraryDatabase.addUser((CommonUser) libObject);
                    logger.info("Added user to DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove user in DB");
                    throw new CannotConnectToDBException("Could not make changes in DB");
                }
            }
            if (libObject.getClass().equals(Book.class)) {
                try {
                    LibraryDatabase.addBook((Book) libObject);
                    logger.info("Added user to DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove user in DB");
                    throw new CannotConnectToDBException("Could not make changes in DB");
                }
            }
        }
    }

    /**
     * Remove Object from local data and from Database. Returns true if object was removed Database, false if only locally.
     */
    static public void removeObject(LibraryContextActions libObject) throws CannotConnectToDBException {
        if (currentAdmin.removeObject(libObject)) {
            logger.info("Removed object locally.");
            if (libObject.getClass().equals(CommonUser.class)) {
                try {
                    LibraryDatabase.removeUser((CommonUser) libObject);
                    logger.info("Removed user from DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove user in DB");
                    throw new CannotConnectToDBException("Could not make changes in DB");
                }
            }
            if (libObject.getClass().equals(Book.class)) {
                try {
                    LibraryDatabase.removeBook((Book) libObject);
                    logger.info("Removed book from DB");
                }
                catch (SQLException e){
                    logger.warn("Could not remove book in DB");
                    throw new CannotConnectToDBException("Could not make changes in DB");
                }
            }
        }
    }

    /**
     * Searches for Object by given pattern. Return set of matching Objects.
     */
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

    /**
     * Allows CommonUser to modify his attributes and Admin to change selected CommonUser's attributes. Returns true if attributes were successfully modified in DB, otherwise returns false.
     */
    static public void modifyFewUserAttributes(Map<AttributesNames, String> attributes, int userId) throws InvalidBookNumberException, NullOrEmptyStringException, InvalidIdException, InvalidLoginException, CannotConnectToDBException {
        CommonUser user;
        try {
            user = Admin.findUserById(userId);
        } catch (InvalidIdException e) {
            logger.error("Could not find user by id");
            throw e;
        }
        try {
            for (var attributeName : attributes.keySet()) {
                user.modifyUser(attributeName, attributes.get(attributeName));
            }
        }
        catch (NullOrEmptyStringException | InvalidLoginException e){
            logger.error("Could not change attribute/s  " + e.getMessage());
            throw e;
        }
        try{
            LibraryDatabase.modifyCommonUser(user);
        } catch (SQLException e) {
            logger.warn("Could not execute query in DB in modifyFewUserAttributes "+ e.getMessage());
            throw new CannotConnectToDBException("Could not make changes in DB");
        }
    }

    /**
     * Allows Admin to modify his attributes. Returns true if attributes were successfully modified in DB, otherwise returns false.
     */
    static public void modifyFewAdminAttributes(Map<AttributesNames, String> attributes) throws InvalidBookNumberException, NullOrEmptyStringException, InvalidIdException, InvalidLoginException, CannotConnectToDBException {
        try {
            for (var attributeName : attributes.keySet()) {
                currentAdmin.modifyUser(attributeName, attributes.get(attributeName));
            }
        }
        catch (NullOrEmptyStringException | InvalidLoginException e){
            logger.error("Could not change attribute/s  " + e.getMessage());
            throw e;
        }
        try{
            LibraryDatabase.modifyAdmin(currentAdmin);
        } catch (SQLException e) {
            logger.warn("Could not execute query in DB in modifyFewUserAttributes "+ e.getMessage());
            throw new CannotConnectToDBException("Could not make changes in DB");
        }
    }

    /**
     * Allows Admin to modify selected Book's attributes. Returns true if attributes were successfully modified in DB, otherwise returns false.
     */
    static public void modifyFewBookAttributes(Map<AttributesNames, String> attributes, int bookId) throws InvalidBookNumberException, NullOrEmptyStringException, InvalidIdException, CannotConnectToDBException {
        Book book;
        try {
            book = Admin.findBookById(bookId);
        } catch (InvalidIdException e) {
            logger.error("Could not find book by id");
            throw e;
        }
        for (var attributeName : attributes.keySet()){
            book.modifyBook(attributeName, attributes.get(attributeName));
        }
        try{
            LibraryDatabase.modifyBook(book);
        } catch (SQLException e) {
            logger.warn("Could not execute query in DB in modifyFewBookAttributes "+ e.getMessage());
            throw new CannotConnectToDBException("Could not make changes in DB");
        }
    }

    /**
     * Allows to modify User's selected attribute. Returns true if attributes were successfully modified in DB, otherwise returns false.
     */
    static public void modifyUser(AttributesNames attributeName, String modifiedVal, User modifiedUser) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException {
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
            throw new CannotConnectToDBException("Could not make changes in DB");
        }
    }

    /**
     * Allows to modify Book's selected attribute. Returns true if attributes were successfully modified in DB, otherwise returns false.
     */
    static public void modifyBook(AttributesNames attributeName, String modifiedVal, Book modifiedBook) throws NullOrEmptyStringException, InvalidIdException, CannotConnectToDBException {
        modifiedBook.modifyBook(attributeName, modifiedVal);
        try{
            LibraryDatabase.modifyBook(modifiedBook);
        } catch (SQLException e) {
            logger.warn("Could not execute query in DB in modifyBook "+ e.getMessage());
            throw new CannotConnectToDBException("Could not make changes in DB");
        }
    }

    /**
     * Returns all CommonUsers descriptions.
     */
    static public Vector<String> showUsers(){
        Vector<String> users_rep = new Vector<>();
        for (var user: Admin.getUsers()){
            users_rep.add(user.describe());
        }
        return users_rep;
    }

    /**
     * Returns borrowed books of currentUser.
     */
    static public Vector<Book> getBorrowedBooks(){
        Vector<Book> borrowed = new Vector<>();
        for (var book: Admin.getBooks()){
            if(book.getUserId() != null){
            if(currentUser.getUserId() == book.getUserId()){
                borrowed.add(book);
            }}
        }
        return borrowed;
    }

    /**
     * Returns ordered books of currentUser.
     */
    static public Vector<Book> getOrderedBooks(){
        Vector<Book> borrowed = new Vector<>();
        for (var bookInt: takenBooks.keySet()){
            if(takenBooks.get(bookInt).contains(currentUser)){
                try {
                    borrowed.add(Admin.findBookById(bookInt));
                } catch (InvalidIdException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return borrowed;
    }

    /**
     * Generate ID for CommonUser.
     */
    public static int generateCommonUserID(){
        ArrayList<Integer> usersIds = new ArrayList<>();
        for (var user:Admin.getUsers()){usersIds.add(user.getUserId());}
        return getMinimumIDFromSortedArray(usersIds);
    }

    /**
     * Generate ID for Book.
     */
    public static int generateBookID(){
        ArrayList<Integer> booksIds = new ArrayList<>();
        for (var book:Admin.getBooks()){booksIds.add(book.getBookId());}
        return getMinimumIDFromSortedArray(booksIds);
    }

    /**
     * Generate ID, minimum and positive, from given sorted ArrayList.
     */
    private static int getMinimumIDFromSortedArray(ArrayList<Integer> booksIds) {
        booksIds.sort(Comparator.comparingInt(id -> id));
        for (int i = 1; i< booksIds.get(booksIds.size()-1); i++){
            if(!booksIds.contains(i)){return i;}
        }
        return booksIds.get(booksIds.size()-1)+1;
    }
}
