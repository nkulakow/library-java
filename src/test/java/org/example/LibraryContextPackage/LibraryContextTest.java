package org.example.LibraryContextPackage;

import org.example.Database.LibraryDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
public class LibraryContextTest {
    @Test
    public void testRemoveObject() throws InvalidIdException, InvalidBookNumberException, NullOrEmptyStringException, InvalidLoginException, CannotConnectToDBException {
        Book book = new Book("asfa", "snfak", 11, "aksfna", true, null, null);
        CommonUser user = new CommonUser("RemoveObject", "alksnfka", "lasmf", "oahoa", 10, "oiahfiau", 0);
        Admin admin = new Admin("RemoveObject", "asfkn", "aksnf", "aoishfaiu", "aoisha", 10);
        Book bookTest = new Book("alksnf", "aslfn", 110, "slakf", true, null, null);
        try (MockedStatic<LibraryDatabase> mocked = mockStatic(LibraryDatabase.class)) {
            mocked.when(LibraryDatabase::getTakenBooks).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getTakenBooksOrderedTime).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getAdmins).thenReturn(new ArrayList<Admin>());
            mocked.when(LibraryDatabase::getCommonUsers).thenReturn(new ArrayList<CommonUser>());
            mocked.when(LibraryDatabase::getBooks).thenReturn(new ArrayList<Book>());
            mocked.when(LibraryDatabase::initWaiting).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.removeBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyBook(bookTest)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(bookTest)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.removeBook(bookTest)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyCommonUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.removeUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyAdmin(admin)).then(invocationOnMock -> null);

            LibraryContext.LibContextInit();
            LibraryContext.addObject(user);
            int size;
            LibraryContext.removeObject(user);
            LibraryContext.addObject(user);
            LibraryContext.addObject(bookTest);
            user.orderBook(bookTest, 1);
            size = Admin.getUsers().size();
            LibraryContext.removeObject(user);
            Assertions.assertEquals(size, Admin.getUsers().size());
            user.returnBook(bookTest);
            LibraryContext.removeObject(user);

            Assertions.assertEquals(size - 1, Admin.getUsers().size());
            LibraryContext.removeObject(bookTest);

            LibraryContext.addObject(admin);
            size = Admin.getAdmins().size();
            LibraryContext.removeObject(admin);

            Assertions.assertEquals(size - 1, Admin.getAdmins().size());
            LibraryContext.removeObject(admin);
            Assertions.assertEquals(size - 1, Admin.getAdmins().size());

            LibraryContext.addObject(book);
            size = Admin.getBooks().size();
            LibraryContext.removeObject(book);
            Assertions.assertEquals(size - 1, Admin.getBooks().size());
            LibraryContext.removeObject(book);
            Assertions.assertEquals(size - 1, Admin.getBooks().size());
        }
    }
    @Test
    public void testSearchForObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException {
        Book book = new Book("asfa", "snfak", 11, "aksfna", true, null, null);
        CommonUser user = new CommonUser("SearchForObject", "alksnfka", "lasmf", "oahoa", 11, "oiahfiau", 0);
        Admin sAdmin = new Admin("SearchForObject", "asfkn", "aksnf", "aoishfaiu", "aoisha", 11);

        try (MockedStatic<LibraryDatabase> mocked = mockStatic(LibraryDatabase.class)) {
            mocked.when(LibraryDatabase::getTakenBooks).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getTakenBooksOrderedTime).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getAdmins).thenReturn(new ArrayList<Admin>());
            mocked.when(LibraryDatabase::getCommonUsers).thenReturn(new ArrayList<CommonUser>());
            mocked.when(LibraryDatabase::getBooks).thenReturn(new ArrayList<Book>());
            mocked.when(LibraryDatabase::initWaiting).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.removeBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyCommonUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.removeUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyAdmin(sAdmin)).then(invocationOnMock -> null);

            LibraryContext.LibContextInit();

            LibraryContext.addObject(book);
            HashSet<LibraryContextActions> results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getBooks()));return admin.search(searchPattern);},Integer.valueOf(11).toString());
            Book result = (Book) results.iterator().next();
            Assertions.assertEquals(book, result);
            LibraryContext.removeObject(book);

            LibraryContext.addObject(user);
            results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getUsers()));return admin.search(searchPattern);},Integer.valueOf(11).toString());
            CommonUser userResult = (CommonUser) results.iterator().next();
            Assertions.assertEquals(user, userResult);
            LibraryContext.removeObject(user);

            LibraryContext.addObject(sAdmin);
            results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getAdmins()));return admin.search(searchPattern);},Integer.valueOf(11).toString());
            Admin adminResult = (Admin) results.iterator().next();
            Assertions.assertEquals(sAdmin, adminResult);
            LibraryContext.removeObject(sAdmin);

        }

    }
    @Test
    public void testAddObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException {
        int bookid = 12, usrid=12, adminid=12;
        String userlogin = "AddObject";
        String adminlogin = "AddObject";

        Book book1 = new Book("asfa", "snfak", bookid, "aksfna", true, null, null);
        CommonUser user = new CommonUser(userlogin, "alksnfka", "lasmf", "oahoa", usrid, "oiahfiau", 0);
        Admin admin = new Admin(adminlogin, "alksnfka", "lasmf", "oahoa", "oiahfiau", adminid);
        try (MockedStatic<LibraryDatabase> mocked = mockStatic(LibraryDatabase.class)) {
            mocked.when(LibraryDatabase::getTakenBooks).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getTakenBooksOrderedTime).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getAdmins).thenReturn(new ArrayList<Admin>());
            mocked.when(LibraryDatabase::getCommonUsers).thenReturn(new ArrayList<CommonUser>());
            mocked.when(LibraryDatabase::getBooks).thenReturn(new ArrayList<Book>());
            mocked.when(LibraryDatabase::initWaiting).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyBook(book1)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(book1)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.removeBook(book1)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyCommonUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.removeUser(user)).then(invocationOnMock -> null);
            mocked.when(()->LibraryDatabase.modifyAdmin(admin)).then(invocationOnMock -> null);

            LibraryContext.LibContextInit();
            int size = Admin.getBooks().size();
            LibraryContext.addObject(book1);
            Assertions.assertFalse(Admin.getBooks().isEmpty());
            Assertions.assertEquals(size + 1, Admin.getBooks().size());
            LibraryContext.addObject(book1);
            Assertions.assertEquals(size + 1, Admin.getBooks().size());
            LibraryContext.removeObject(book1);

            size = Admin.getUsers().size();
            LibraryContext.addObject(user);
            Assertions.assertEquals(size + 1, Admin.getUsers().size());
            LibraryContext.addObject(user);
            Assertions.assertEquals(size + 1, Admin.getUsers().size());
            LibraryContext.removeObject(user);

            size = Admin.getAdmins().size();
            LibraryContext.addObject(admin);
            Assertions.assertEquals(size + 1, Admin.getAdmins().size());
            LibraryContext.addObject(admin);
            Assertions.assertEquals(size + 1, Admin.getAdmins().size());
            LibraryContext.removeObject(admin);

        }

    }
    @Test
    public void testModifyBookAndUser() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException {
        Book book = new Book("asfa", "snfak", 1, "aksfna", true, null, null);
        CommonUser user = new CommonUser("asf", "alksnfka", "lasmf", "oahoa", 1, "oiahfiau", 0);

        try (MockedStatic<LibraryDatabase> mocked = mockStatic(LibraryDatabase.class)) {
            mocked.when(() -> LibraryDatabase.modifyCommonUser(user)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(book)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addUser(user)).then(invocationOnMock -> null);
            mocked.when(LibraryDatabase::getTakenBooks).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getTakenBooksOrderedTime).thenReturn(new Hashtable<>());
            mocked.when(LibraryDatabase::getAdmins).thenReturn(new ArrayList<Admin>());
            mocked.when(LibraryDatabase::getCommonUsers).thenReturn(new ArrayList<CommonUser>());
            mocked.when(LibraryDatabase::getBooks).thenReturn(new ArrayList<Book>());
            mocked.when(LibraryDatabase::initWaiting).then(invocationOnMock -> null);

            LibraryContext.LibContextInit();
            LibraryContext.addObject(book);
            LibraryContext.modifyBook(AttributesNames.name, "mybook", book);
            Assertions.assertEquals("mybook", book.getName());
            Assertions.assertTrue(Admin.getBooks().contains(book));

            LibraryContext.addObject(user);
            LibraryContext.modifyUser(AttributesNames.password, "mypass", user);
            Assertions.assertEquals("mypass", user.getPassword());
            Assertions.assertTrue(Admin.getUsers().contains(user));
        }

    }

    @Test
    public void testModifyAdmin() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException, SQLException {
        Admin sAdmin = new Admin("ModifyAdmin", "asfkn", "aksnf", "aoishfaiu", "aoisha", 1);
        try (MockedStatic<LibraryDatabase> mocked = mockStatic(LibraryDatabase.class)){
            mocked.when(()->LibraryDatabase.modifyAdmin(sAdmin)).then(invocationOnMock -> null);
            mocked.when(LibraryDatabase::getAdmins).thenReturn(new ArrayList<Admin>());
            mocked.when(LibraryDatabase::getCommonUsers).thenReturn(new ArrayList<CommonUser>());
            mocked.when(LibraryDatabase::getBooks).thenReturn(new ArrayList<Book>());
            mocked.when(LibraryDatabase::initWaiting).then(invocationOnMock -> null);

            LibraryContext.LibContextInit();
            LibraryContext.addObject(sAdmin);
            LibraryContext.modifyUser(AttributesNames.login, "newLogin", sAdmin);
            Assertions.assertEquals("newLogin", sAdmin.getLogin());
            Assertions.assertTrue(Admin.getAdmins().contains(sAdmin));
        }
    }
    @Test
    public void testOrderBook() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, CannotConnectToDBException, InvalidLoginException {
        Book book1 = new Book("book1", "cat1", 1, "author1", true, null, null);
        Book book2 = new Book("book2", "cat2", 2, "author2", true, null, null);
        Book book3 = new Book("book2", "cat2", 3, "author2", true, null, null);
        CommonUser user = new CommonUser("testorderbook2", "pass2", "testorder2", "a", 151, "a", 0);
        ArrayDeque<CommonUser> user_deque = new ArrayDeque<>();
        user_deque.add(user);
        ArrayDeque<Long> months_deque = new ArrayDeque<>();
        months_deque.add(1L);
        Hashtable<Integer, ArrayDeque<CommonUser>> taken_books = new Hashtable<>();
        Hashtable<Integer, ArrayDeque<Long>> taken_books_time = new Hashtable<>();
        taken_books.put(book3.getBookId(), user_deque);
        taken_books_time.put(book3.getBookId(), months_deque);
        try (MockedStatic<LibraryDatabase> mocked = mockStatic(LibraryDatabase.class)){
            mocked.when(LibraryDatabase::getAdmins).thenReturn(new ArrayList<Admin>());
            ArrayList<CommonUser> users = new ArrayList<>();
            users.add(new CommonUser("testorderbook", "pass", "testorder", "a", 15, "a", 0));
            mocked.when(LibraryDatabase::getCommonUsers).thenReturn(users);
            mocked.when(LibraryDatabase::getBooks).thenReturn(new ArrayList<Book>());
            mocked.when(LibraryDatabase::initWaiting).then( invocationOnMock -> null);
            mocked.when(LibraryDatabase::getTakenBooks).thenReturn(taken_books);
            mocked.when(LibraryDatabase::getTakenBooksOrderedTime).thenReturn(taken_books_time);
            mocked.when(() -> LibraryDatabase.modifyBook(book1)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyBook(book2)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.modifyBook(book3)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(book1)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(book2)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addBook(book3)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addWaiting(book1, 4, 15)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addWaiting(book2, 5, 15)).then(invocationOnMock -> null);
            mocked.when(() -> LibraryDatabase.addWaiting(book3, 6, 15)).then(invocationOnMock -> null);

            LibraryContext.LibContextInit();
            Assertions.assertTrue(LibraryContext.checkLoggingUsers("testorderbook", "pass"));
            LibraryContext.addObject(book1);
            LibraryContext.addObject(book2);
            LibraryContext.addObject(book3);
            LibraryContext.orderBook(book1, 2);
            LibraryContext.orderBook(book2, 3);

            Assertions.assertTrue(LibraryContext.getTakenBooks().containsKey(book1.getBookId()));
            Assertions.assertEquals(0, LibraryContext.getTakenBooksOrderedTime().get(book2.getBookId()).size());
            Assertions.assertEquals(ZonedDateTime.now().plusMonths(3).getMonth(), book2.getReturnDate().getMonth());
            Assertions.assertEquals(ZonedDateTime.now().plusMonths(2).getDayOfMonth(), book1.getReturnDate().getDayOfMonth());
            Assertions.assertEquals(LibraryContext.getCurrentUser().getUserId(), book1.getUserId());
            Assertions.assertTrue(LibraryContext.getTakenBooks().containsKey(book3.getBookId()));
            LibraryContext.orderBook(book1, 4);
            LibraryContext.orderBook(book2, 5);
            LibraryContext.orderBook(book3, 6);
            Assertions.assertEquals(1, LibraryContext.getTakenBooksOrderedTime().get(book2.getBookId()).size());
            Assertions.assertEquals(4, LibraryContext.getTakenBooksOrderedTime().get(book1.getBookId()).getFirst());
            Assertions.assertEquals(15, LibraryContext.getTakenBooks().get(book2.getBookId()).getFirst().getUserId());
            Assertions.assertEquals(151, LibraryContext.getTakenBooks().get(book3.getBookId()).getFirst().getUserId());
        }
    }
}