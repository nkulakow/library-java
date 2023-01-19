//package org.example.LibraryContextPackage;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import java.time.ZonedDateTime;
//import java.util.HashSet;
//
//public class LibraryContextTest {
//
//    private final Logger logger = LogManager.getLogger(org.example.LibraryContextPackage.LibraryContextTest.class);
//    @Test
//    public void testRemoveObject() throws InvalidIdException, InvalidBookNumberException, NullOrEmptyStringException, InvalidLoginException, CannotConnectToDBException {
//        int id = 0;
//        String login = "asf";
//        for(CommonUser user:Admin.getUsers())
//        {
//            if(user.getUserId() > id)
//            {
//                id = user.getUserId();
//            }
//            if(user.getLogin().equals(login))
//            {
//                login = login.concat(user.getLogin());
//            }
//        }
//        id += 1;
//        CommonUser user = new CommonUser(login, "alksnfka", "lasmf", "oahoa", id, "oiahfiau", 0);
//        for(Book book:Admin.getBooks())
//        {
//            if(book.getBookId() > id)
//            {
//                id = book.getBookId();
//            }
//        }
//        id += 1;
//        Book bookTest = new Book("alksnf", "aslfn", id, "slakf", true, null, null);
//        LibraryContext.LibContextInitForTests(false);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(user));
//        int size = Admin.getUsers().size();
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(user));
//        LibraryContext.removeObject(user);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(user));
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(bookTest));
//        user.orderBook(bookTest, 1);
//        size = Admin.getUsers().size();
//        LibraryContext.removeObject(user);
//        Assertions.assertEquals(size, Admin.getUsers().size());
//        user.returnBook(bookTest);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(user));
//
//        Assertions.assertEquals(size - 1, Admin.getUsers().size());
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(bookTest));
//
//        for(Admin admin:Admin.getAdmins())
//        {
//            if(admin.getAdminId() > id)
//            {
//                id = admin.getAdminId();
//            }
//            if(admin.getLogin().equals(login))
//            {
//                login = login.concat(admin.getLogin());
//            }
//        }
//        id += 1;
//        Admin admin = new Admin(login, "asfkn", "aksnf", "aoishfaiu", "aoisha", id);
//        LibraryContext.addObject(admin);
//        size = Admin.getAdmins().size();
//        LibraryContext.removeObject(admin);
//
//        Assertions.assertEquals(size - 1, Admin.getAdmins().size());
//        LibraryContext.removeObject(admin);
//        Assertions.assertEquals(size - 1, Admin.getAdmins().size());
//        for(Book book:Admin.getBooks())
//        {
//            if(book.getBookId() > id)
//            {
//                id = book.getBookId();
//            }
//        }
//        id += 1;
//        Book book = new Book("alksnf", "aslfn", id, "slakf", true, null, null);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(book));
//        size = Admin.getBooks().size();
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(book));
//        Assertions.assertEquals(size - 1, Admin.getBooks().size());
//        LibraryContext.removeObject(book);
//        Assertions.assertEquals(size - 1, Admin.getBooks().size());
//    }
//    @Test
//    public void testSearchForObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException {
//        int id = 0;
//        for(Book book:Admin.getBooks())
//        {
//            if(book.getBookId() > id)
//            {
//                id = book.getBookId();
//            }
//        }
//        id += 1;
//        Book book = new Book("asfa", "snfak", id, "aksfna", true, null, null);
//        LibraryContext.LibContextInitForTests(false);
//        Assertions.assertThrows(CannotConnectToDBException.class, () -> {LibraryContext.addObject(book);});
//        HashSet<LibraryContextActions> results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getBooks()));return admin.search(searchPattern);},Integer.valueOf(id).toString());
//        Book result = (Book) results.iterator().next();
//        Assertions.assertEquals(book, result);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(book));
//        String login = "asf";
//        for(CommonUser user:Admin.getUsers())
//        {
//            if(user.getUserId() > id)
//            {
//                id = user.getUserId();
//            }
//            if(user.getLogin().equals(login))
//            {
//                login = login.concat(user.getLogin());
//            }
//        }
//        id += 1;
//        CommonUser user = new CommonUser(login, "alksnfka", "lasmf", "oahoa", id, "oiahfiau", 0);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(user));
//        results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getUsers()));return admin.search(searchPattern);},Integer.valueOf(id).toString());
//        CommonUser userResult = (CommonUser) results.iterator().next();
//        Assertions.assertEquals(user, userResult);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(user));
//        login = "as";
//        for(Admin admin:Admin.getAdmins())
//        {
//            if(admin.getAdminId() > id)
//            {
//                id = admin.getAdminId();
//            }
//            if(admin.getLogin().equals(login))
//            {
//                login = login.concat(admin.getLogin());
//            }
//        }
//        id += 1;
//        Admin sAdmin = new Admin(login, "asfkn", "aksnf", "aoishfaiu", "aoisha", id);
//        LibraryContext.addObject(sAdmin);
//        results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getAdmins()));return admin.search(searchPattern);},Integer.valueOf(id).toString());
//        Admin adminResult = (Admin) results.iterator().next();
//        Assertions.assertEquals(sAdmin, adminResult);
//        LibraryContext.removeObject(sAdmin);
//    }
//    @Test
//    public void testAddObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException {
//        int id = 0;
//        for(Book book:Admin.getBooks())
//        {
//            if(book.getBookId() > id)
//            {
//                id = book.getBookId();
//            }
//        }
//        id += 1;
//        Book book1 = new Book("asfa", "snfak", id, "aksfna", true, null, null);
//        LibraryContext.LibContextInitForTests(false);
//        int size = Admin.getBooks().size();
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(book1));
//        Assertions.assertFalse(Admin.getBooks().isEmpty());
//        Assertions.assertEquals(size + 1, Admin.getBooks().size());
//        LibraryContext.addObject(book1);
//        Assertions.assertEquals(size + 1, Admin.getBooks().size());
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(book1));
//        String login = "asf";
//        for(CommonUser user:Admin.getUsers())
//        {
//            if(user.getUserId() > id)
//            {
//                id = user.getUserId();
//            }
//            if(user.getLogin().equals(login))
//            {
//                login = login.concat(user.getLogin());
//            }
//        }
//        id += 1;
//        CommonUser user = new CommonUser(login, "alksnfka", "lasmf", "oahoa", id, "oiahfiau", 0);
//        size = Admin.getUsers().size();
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(user));
//        Assertions.assertEquals(size + 1, Admin.getUsers().size());
//        LibraryContext.addObject(user);
//        Assertions.assertEquals(size + 1, Admin.getUsers().size());
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(user));
//        login = "saf";
//        for(Admin admin:Admin.getAdmins())
//        {
//            if(admin.getAdminId() > id)
//            {
//                id = admin.getAdminId();
//            }
//            if(admin.getLogin().equals(login))
//            {
//                login = login.concat(admin.getLogin());
//            }
//        }
//        id += 1;
//        Admin admin = new Admin(login, "alksnfka", "lasmf", "oahoa", "oiahfiau", id);
//        size = Admin.getAdmins().size();
//        LibraryContext.addObject(admin);
//        Assertions.assertEquals(size + 1, Admin.getAdmins().size());
//        LibraryContext.addObject(admin);
//        Assertions.assertEquals(size + 1, Admin.getAdmins().size());
//        LibraryContext.removeObject(admin);
//    }
//    @Test
//    public void testModifyBookAndUser() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException {
//        Book book = new Book("asfa", "snfak", 1, "aksfna", true, null, null);
//        LibraryContext.LibContextInitForTests(true);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(book));
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.modifyBook(AttributesNames.name, "mybook", book));
//        Assertions.assertEquals("mybook", book.getName());
//        Assertions.assertTrue(Admin.getBooks().contains(book));
//        for(var book1 : Admin.getBooks()){
//            Assertions.assertEquals(book, book1);
//        }
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.removeObject(book));
//        CommonUser user = new CommonUser("asf", "alksnfka", "lasmf", "oahoa", 1, "oiahfiau", 0);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(user));
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.modifyUser(AttributesNames.password, "mypass", user));
//        Assertions.assertEquals("mypass", user.getPassword());
//        Assertions.assertTrue(Admin.getUsers().contains(user));
//        try {
//            LibraryContext.removeObject(user);
//        }
//        catch (CannotConnectToDBException e){
//            logger.info("CannotConnectToDBException");
//        }
//    }
//
//    @Test
//    public void testModifyAdmin() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException, CannotConnectToDBException {
//        LibraryContext.LibContextInitForTests(false);
//        Admin sAdmin = new Admin("as", "asfkn", "aksnf", "aoishfaiu", "aoisha", 1);
//        LibraryContext.addObject(sAdmin);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.modifyUser(AttributesNames.login, "mylogin", sAdmin));
//        Assertions.assertEquals("mylogin", sAdmin.getLogin());
//        Assertions.assertTrue(Admin.getAdmins().contains(sAdmin));
//        LibraryContext.removeObject(sAdmin);
//    }
//    @Test
//    public void testOrderBook() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {
//        LibraryContext.LibContextInitForTests(true);
//        Book book1 = new Book("book1", "cat1", 1, "author1", true, null, null);
//        Book book2 = new Book("book2", "cat2", 2, "author2", true, null, null);
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(book1));
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.addObject(book2));
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.orderBook(book1, 2));
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.orderBook(book2, 3));
//        Assertions.assertTrue(LibraryContext.getTakenBooks().containsKey(book1.getBookId()));
//        Assertions.assertEquals(0, LibraryContext.getTakenBooksOrderedTime().get(book2.getBookId()).size());
//        Assertions.assertEquals(ZonedDateTime.now().plusMonths(3).getMonth(), book2.getReturnDate().getMonth());
//        Assertions.assertEquals(ZonedDateTime.now().plusMonths(2).getDayOfMonth(), book1.getReturnDate().getDayOfMonth());
//        Assertions.assertEquals(LibraryContext.getCurrentUser().getUserId(), book1.getUserId());
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.orderBook(book1, 4));
//        Assertions.assertThrows(CannotConnectToDBException.class,()->LibraryContext.orderBook(book2, 5));
//        Assertions.assertEquals(1, LibraryContext.getTakenBooksOrderedTime().get(book2.getBookId()).size());
//        Assertions.assertEquals(4, LibraryContext.getTakenBooksOrderedTime().get(book1.getBookId()).getFirst());
//        Assertions.assertEquals(1, LibraryContext.getTakenBooks().get(book2.getBookId()).getFirst().getUserId());
//    }
//}