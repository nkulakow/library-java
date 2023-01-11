package org.example.Database;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.LibraryContextPackage.*;

import java.io.*;
import java.sql.Connection;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.Date;


public class LibraryDatabase {
    private static final Logger logger = LogManager.getLogger(org.example.Database.LibraryDatabase.class);

    private static final String URL = "jdbc:oracle:thin:@//ora4.ii.pw.edu.pl:1521/pdb1.ii.pw.edu.pl";
    private static String LOGIN;
    private static String HASHEDPASSWORD;
    private static Connection CONNECTION;
    @Getter
    private static Hashtable<Integer, ArrayDeque<CommonUser>> takenBooks;
    @Getter
    private static Hashtable<Integer, ArrayDeque<Long>> takenBooksOrderedTime;
    public static void initLoginInfo() throws IOException {
        try {
            FileInputStream fileStream = new FileInputStream("src/main/resources/info.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
            LOGIN = br.readLine();
            HASHEDPASSWORD = br.readLine();
            fileStream.close();
        }
        catch (IOException e){
            logger.error("Could not get login to database info in initLoginInfo method " + e.getMessage());
            throw e;
        }
    }
    public static void initLoginInfoForTests()  {
            LOGIN = "n";
            HASHEDPASSWORD = "password";
    }

    /**
     * Returns an arraylist of all the admins.
     */
    public static ArrayList<Admin> getAdmins() throws SQLException, NullOrEmptyStringException, InvalidIdException, InvalidLoginException {
        ArrayList<Admin> admins = new ArrayList<>();
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            ResultSet result = query.executeQuery(DatabaseConstants.InquiriesConstants.SELECT_ADMINS);
            while (result.next()) {
                int id = result.getInt(DatabaseConstants.AdminConstants.ADMIN_ID);
                String name = result.getString(DatabaseConstants.BasicConstants.NAME);
                String surname = result.getString(DatabaseConstants.AdminConstants.ADMIN_SURNAME);
                String mail = result.getString(DatabaseConstants.BasicConstants.MAIL);
                String login = result.getString(DatabaseConstants.BasicConstants.LOGIN);
                String password = result.getString(DatabaseConstants.BasicConstants.PASSWORD);
                admins.add(new Admin(login, password, name, surname, mail, id));
            }
            logger.info("Executed getAdmins method.");
        } catch (java.sql.SQLException | NullOrEmptyStringException | InvalidIdException e) {
            logger.warn("Could not execute query in getAdmins method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
        return admins;
    }
    /**
     * Returns an arraylist of all the books.
     */
    public static ArrayList<Book> getBooks() throws SQLException, NullOrEmptyStringException, InvalidIdException {
        ArrayList<Book> books = new ArrayList<>();
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            ResultSet result = query.executeQuery(DatabaseConstants.InquiriesConstants.SELECT_BOOKS);
            while (result.next()) {
                int id = result.getInt(DatabaseConstants.BasicConstants.BOOK_ID);
                String name = result.getString(DatabaseConstants.BasicConstants.NAME);
                String author = result.getString(DatabaseConstants.BooksConstants.AUTHOR);
                String category = result.getString(DatabaseConstants.BooksConstants.CATEGORY);
                boolean available = result.getInt(DatabaseConstants.BooksConstants.AVAILABLE)==1;
                java.sql.Date return_date_demo = result.getDate(DatabaseConstants.BooksConstants.RETURN_DATE);
                ZonedDateTime return_date = null;
                if (return_date_demo!=null){
                    LocalDate localDate = return_date_demo.toLocalDate();
                    return_date = localDate.atStartOfDay(ZoneId.systemDefault());
                }
                Integer user_id = result.getInt(DatabaseConstants.BasicConstants.USER_ID);
                user_id = user_id==0?null:user_id;
                if (user_id != null && !takenBooks.containsKey(id))
                {
                    takenBooks.put(id, new ArrayDeque<>());
                    takenBooksOrderedTime.put(id, new ArrayDeque<>());
                }
                books.add(new Book(name, category, id, author, available, user_id, return_date));
            }
            logger.info("Executed getBooks method.");
        } catch (java.sql.SQLException | NullOrEmptyStringException | InvalidIdException e) {
            logger.warn("Could not execute query in getBooks method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
        return books;
    }
    /**
     * initializes the hashtables for takenBooks and takenBooksOrderedTime.
     */
    public static void initWaiting() throws SQLException, InvalidIdException {
        Hashtable<Integer, ArrayDeque<CommonUser>> takenBooksTemp = new Hashtable<>();
        Hashtable<Integer, ArrayDeque<Long>> takenBooksOrderedTimeTemp = new Hashtable<>();

        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            ResultSet result = query.executeQuery(DatabaseConstants.InquiriesConstants.SELECT_WAITING);
            while (result.next()) {
                int book_id = result.getInt(DatabaseConstants.BasicConstants.BOOK_ID);
                int user_id = result.getInt(DatabaseConstants.BasicConstants.USER_ID);
                long months = result.getInt(DatabaseConstants.WaitingConstants.MONTHS);
                if (takenBooksTemp.containsKey(book_id))
                {
                    takenBooksTemp.get(book_id).add(Admin.findUserById(user_id));
                    takenBooksOrderedTimeTemp.get(book_id).add(months);
                }
                else
                {
                    takenBooksTemp.put(book_id, new ArrayDeque<>());
                    takenBooksTemp.get(book_id).add(Admin.findUserById(user_id));
                    takenBooksOrderedTimeTemp.put(book_id, new ArrayDeque<>());
                    takenBooksOrderedTimeTemp.get(book_id).add(months);
                }
            }
            logger.info("Executed getWaiting method");
        } catch (java.sql.SQLException | InvalidIdException e) {
            logger.warn("Could not execute query in getWaiting method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
        takenBooks = takenBooksTemp;
        takenBooksOrderedTime = takenBooksOrderedTimeTemp;
    }

    /**
     * Returns an arraylist of all the common users.
     */
    public static ArrayList<CommonUser> getCommonUsers() throws SQLException, InvalidBookNumberException, NullOrEmptyStringException, InvalidIdException, InvalidLoginException {
        ArrayList<CommonUser> users = new ArrayList<>();
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            ResultSet result = query.executeQuery(DatabaseConstants.InquiriesConstants.SELECT_COMMON_USERS);
            while (result.next()) {
                int id = result.getInt(DatabaseConstants.BasicConstants.USER_ID);
                String name = result.getString(DatabaseConstants.UserConstants.USER_NAME);
                String surname = result.getString(DatabaseConstants.UserConstants.USER_SURNAME);
                String mail = result.getString(DatabaseConstants.BasicConstants.MAIL);
                String login = result.getString(DatabaseConstants.BasicConstants.LOGIN);
                String password = result.getString(DatabaseConstants.BasicConstants.PASSWORD);
                int booksNumber = result.getInt(DatabaseConstants.UserConstants.USER_BOOKS_NR);
                users.add(new CommonUser(login, password, name, surname, id, mail, booksNumber));
            }
            logger.info("Executed getUsers method.");
        } catch (java.sql.SQLException | NullOrEmptyStringException | InvalidIdException | InvalidBookNumberException e) {
            logger.warn("Could not execute query in getAdmins method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
        return users;
    }
    /**
     * Adds a new user to PAP_USERS, and their login/password info to PAP_USERS_PASSWD.
     */
    public static void addUser(CommonUser user) throws SQLException {
        String id = String.valueOf(user.getUserId()), name = user.getName(), surname = user.getSurname(),
                mail = user.getMail(), login = user.getLogin(), password= user.getPassword();
        String query_str1 = "insert into nkulakow.pap_users values (" + id + ",'" + name + "','" + surname + "','" + mail+ "','" + 0  + "')";
        String query_str2 = "insert into nkulakow.PAP_USERS_PASSWD values(" + id + ",'" + login + "','" + password+"')";
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str1);
            query.executeUpdate(query_str2);
            logger.info("Executed addUser method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in addUser method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }
    /**
     * Deletes the chosen user from the PAP_USER and their login info from PAP_USERS_PASSWD.
     */
    public static void removeUser(CommonUser user) throws SQLException {
        String id = String.valueOf(user.getUserId());
        String query_str1 = "delete from nkulakow.PAP_USERS_PASSWD where user_id=" + id;
        String query_str2 = "delete from nkulakow.PAP_USERS where user_id=" + id;
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str1);
            query.executeUpdate(query_str2);
            logger.info("Executed removeUser method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in removeUser method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }
    /**
     * Adds a book to PAP_BOOKS.
     */
    public static void addBook(Book book) throws SQLException {
        String name = book.getName(), author = book.getAuthor(), category = book.getCategory();
        int book_id = book.getBookId(), available = book.getAvailable()?1:0;
        Integer user_id = book.getUserId();
        ZonedDateTime date_zoned = book.getReturnDate();
        Date return_date;
        if (date_zoned==null)
        {
            return_date =null;
        }
        else{
            Instant instant = date_zoned.toLocalDateTime().toInstant(ZoneOffset.UTC);
            return_date = Date.from(instant);
        }
        String query_str = "insert into nkulakow.pap_books values("+book_id+", '"+name+"', '"+author+"', '"+category+"', "+available+", "+return_date+", "+ user_id+")";
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str);
            logger.info("Executed addBook method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in addBook method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }
    /**
     * Removes a book from PAP_BOOKS.
     */
    public static void removeBook(Book book) throws SQLException {
        String id = String.valueOf(book.getBookId());
        String query_str = "delete from nkulakow.PAP_BOOKS where book_id=" + id;
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str);
            logger.info("Executed removeBook method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in removeUser method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }
    /**
     * Adds a new row in PAP_WAITING.
     */
    public static void addWaiting(Book book, long months, int userId) throws SQLException {
        int book_id = book.getBookId();
        String query_str = "insert into nkulakow.pap_waiting values("+book_id+", "+userId+", "+months+")";

        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str);
            logger.info("Executed addWaiting method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in addWaiting method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }
    /**
     * Removes a row from PAP_WAITING.
     */
    public static void removeWaiting(Book book, Integer user_id_int) throws SQLException {
        String book_id = String.valueOf(book.getBookId());
        String user_id = String.valueOf(user_id_int);
        String query_str = "delete from nkulakow.PAP_WAITING where book_id=" + book_id + "and user_id=" + user_id;

        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str);
            logger.info("Executed removeWaiting method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in removeWaiting method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }

    private static String getAutoPassword()
    {
        byte[] bytePasswd = Base64.getDecoder().decode(HASHEDPASSWORD);
        return new String(bytePasswd);
    }

    public static void modifyCommonUser(CommonUser user) throws SQLException {
        String id = String.valueOf(user.getUserId()), name = user.getName(), surname = user.getSurname(),
                mail = user.getMail(), login = user.getLogin(), password= user.getPassword();
        int books_nr = user.getBooksNr();
        String query_str1 = "update nkulakow.PAP_USERS set first_name='"+name+"', last_name='" +surname+ "', mail='"+mail+"', books_nr="+books_nr+" where user_id=" + id;
        String query_str2 = "update nkulakow.PAP_USERS_PASSWD set login='"+login+"', password='" +password+ "'where user_id=" + id;
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str1);
            query.executeUpdate(query_str2);
            logger.info("Executed modifyCommonUser method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in modifyCommonUser method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }

    public static void modifyAdmin(Admin admin) throws SQLException {
        String id = String.valueOf(admin.getAdminId()), name = admin.getName(), surname = admin.getSurname(),
                mail = admin.getMail(), login = admin.getLogin(), password= admin.getPassword();
        String query_str1 = "update nkulakow.PAP_ADMINS set name='"+name+"', surname='" +surname+ "', mail='"+mail+"' where admin_id=" + id;
        String query_str2 = "update nkulakow.PAP_ADMINS_PASSWD set login='"+login+"', password='" +password+ "'where admin_id=" + id;
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            logger.info("Connected to database.");
            Statement query = CONNECTION.createStatement();
            query.executeUpdate(query_str1);
            query.executeUpdate(query_str2);
            logger.info("Executed modifyAdmin method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in modifyAdmin method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }


    public static void modifyBook(Book book) throws SQLException {
        String name = book.getName(), author = book.getAuthor(), category = book.getCategory(), user_str="";
        int book_id = book.getBookId(), available = book.getAvailable()?1:0;
        Integer user_id = book.getUserId();
        ZonedDateTime date_zoned = book.getReturnDate();
        Date date_date = null;
        if (date_zoned!=null){
            Instant instant = date_zoned.toLocalDateTime().toInstant(ZoneOffset.UTC);
            date_date = Date.from(instant);
        }
        if (user_id != null) {
        if (user_id != 0){
            user_str = ", user_id="+user_id;
        }}
        else{user_str =", user_id=null";}
        String query_str = "update nkulakow.PAP_BOOKS set name='"+name+"', author='"+author+"', cathegory='"+category+"', available="+available+", return_date=?"+user_str+ " where book_id="+book_id;

        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, getAutoPassword());
            PreparedStatement statement = CONNECTION.prepareStatement(query_str);
            if(date_date != null){statement.setDate(1, new java.sql.Date(date_date.getTime()));}
            else{statement.setDate(1, null);}
            logger.info("Connected to database.");
            statement.executeUpdate();
            logger.info("Executed modifyBook method.");
        } catch (java.sql.SQLException e) {
            logger.warn("Could not execute query in modifyBook method " + e.getMessage());
            throw e;
        }
        CONNECTION.close();
    }
}

class DatabaseConstants {
    public static final class BasicConstants {
        public static final String USER_ID = "user_id";
        public static final String BOOK_ID = "book_id";
        public static final String NAME = "name";
        public static final String MAIL = "mail";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
    }

    public static final class UserConstants {
        public static final String USER_NAME = "first_name";
        public static final String USER_SURNAME = "last_name";
        public static final String USER_BOOKS_NR = "books_nr";
    }
    public static final class AdminConstants {
        public static final String ADMIN_ID = "admin_id";
        public static final String ADMIN_SURNAME = "surname";
    }
    public static final class BooksConstants {
        public static final String AUTHOR = "author";
        public static final String CATEGORY = "cathegory";
        public static final String AVAILABLE = "available";
        public static final String RETURN_DATE = "return_date";

    }
    public static final class WaitingConstants {
        public static final String MONTHS = "months";
    }
    public static final class InquiriesConstants {
        public static final String SELECT_COMMON_USERS = "SELECT u.*, p.login, p.password from nkulakow.PAP_USERS u join nkulakow.PAP_USERS_PASSWD p on(u.user_id=p.user_id)";
        public static final String SELECT_ADMINS = "SELECT a.*, p.login, p.password from nkulakow.PAP_ADMINS a join nkulakow.PAP_ADMINS_PASSWD p on(a.admin_id=p.admin_id)";
        public static final String SELECT_BOOKS = "SELECT * from nkulakow.PAP_BOOKS";
        public static final String SELECT_WAITING = "SELECT * from nkulakow.PAP_WAITING";
    }
}
