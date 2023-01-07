package org.example.LibraryContextPackage;

import org.example.Database.LibraryDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryContextTest {

    @Test
    public void testRemoveObject() throws InvalidIdException, InvalidBookNumberException, NullOrEmptyStringException, IOException {
        int id = 0;
        String login = "asf";
        for(CommonUser user:Admin.getUsers())
        {
            if(user.getUserId() > id)
            {
                id = user.getUserId();
            }
            if(user.getLogin().equals(login))
            {
                login = login.concat(user.getLogin());
            }
        }
        id += 1;
        int uid = id;
        CommonUser user = new CommonUser(login, "alksnfka", "lasmf", "oahoa", id, "oiahfiau", 0);
        for(Book book:Admin.getBooks())
        {
            if(book.getBookId() > id)
            {
                id = book.getUserId();
            }
        }
        id += 1;
        Book bookTest = new Book("alksnf", "aslfn", id, "slakf", true, uid, null);
        LibraryContext.LibContextInitForTests(false);
        LibraryContext.addObject(user);
        int size = Admin.getUsers().size();
        LibraryContext.removeObject(user);
        Assertions.assertEquals(size - 1, Admin.getUsers().size());
        LibraryContext.removeObject(user);
        Assertions.assertEquals(size - 1, Admin.getUsers().size());
        LibraryContext.addObject(user);
        size = Admin.getAdmins().size();
        LibraryContext.removeObject(user);
        Assertions.assertEquals(size, Admin.getAdmins().size());
        LibraryContext.removeObject(bookTest);
        LibraryContext.removeObject(user);
        Assertions.assertEquals(size - 1, Admin.getUsers().size());
        for(Admin admin:Admin.getAdmins())
        {
            if(admin.getAdminId() > id)
            {
                id = admin.getAdminId();
            }
            if(admin.getLogin().equals(login))
            {
                login = login.concat(admin.getLogin());
            }
        }
        id += 1;
        Admin admin = new Admin(login, "asfkn", "aksnf", "aoishfaiu", "aoisha", id);
        LibraryContext.addObject(admin);
        size = Admin.getAdmins().size();
        LibraryContext.removeObject(admin);
        Assertions.assertEquals(size - 1, Admin.getAdmins().size());
        LibraryContext.removeObject(admin);
        Assertions.assertEquals(size - 1, Admin.getAdmins().size());
        for(Book book:Admin.getBooks())
        {
            if(book.getBookId() > id)
            {
                id = book.getUserId();
            }
        }
        id += 1;
        Book book = new Book("alksnf", "aslfn", id, "slakf", true, null, null);
        LibraryContext.addObject(book);
        size = Admin.getBooks().size();
        LibraryContext.removeObject(book);
        Assertions.assertEquals(size - 1, Admin.getBooks().size());
        LibraryContext.removeObject(book);
        Assertions.assertEquals(size - 1, Admin.getBooks().size());
    }
    @Test
    public void testSearchForObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, IOException {
        int id = 0;
        for(Book book:Admin.getBooks())
        {
            if(book.getBookId() > id)
            {
                id = book.getUserId();
            }
        }
        id += 1;
        Book book = new Book("asfa", "snfak", id, "aksfna", true, null, null);
        LibraryContext.LibContextInitForTests(false);
        LibraryContext.addObject(book);
        HashSet<LibraryContextActions> results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getBooks()));return admin.search(searchPattern);},Integer.valueOf(id).toString());
        Book result = (Book) results.iterator().next();
        Assertions.assertEquals(book, result);
        LibraryContext.removeObject(book);
        String login = "asf";
        for(CommonUser user:Admin.getUsers())
        {
            if(user.getUserId() > id)
            {
                id = user.getUserId();
            }
            if(user.getLogin().equals(login))
            {
                login = login.concat(user.getLogin());
            }
        }
        id += 1;
        CommonUser user = new CommonUser(login, "alksnfka", "lasmf", "oahoa", id, "oiahfiau", 0);
        LibraryContext.addObject(user);
        results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getUsers()));return admin.search(searchPattern);},Integer.valueOf(id).toString());
        CommonUser userResult = (CommonUser) results.iterator().next();
        Assertions.assertEquals(user, userResult);
        LibraryContext.removeObject(user);
        login = "as";
        for(Admin admin:Admin.getAdmins())
        {
            if(admin.getAdminId() > id)
            {
                id = admin.getAdminId();
            }
            if(admin.getLogin().equals(login))
            {
                login = login.concat(admin.getLogin());
            }
        }
        id += 1;
        Admin sAdmin = new Admin(login, "asfkn", "aksnf", "aoishfaiu", "aoisha", id);
        LibraryContext.addObject(sAdmin);
        results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getAdmins()));return admin.search(searchPattern);},Integer.valueOf(id).toString());
        Admin adminResult = (Admin) results.iterator().next();
        Assertions.assertEquals(sAdmin, adminResult);
        LibraryContext.removeObject(sAdmin);
    }
    @Test
    public void testAddObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, IOException {
        int id = 0;
        for(Book book:Admin.getBooks())
        {
            if(book.getBookId() > id)
            {
                id = book.getUserId();
            }
        }
        id += 1;
        Book book1 = new Book("asfa", "snfak", id, "aksfna", true, null, null);
        LibraryContext.LibContextInitForTests(false);
        int size = Admin.getBooks().size();
        LibraryContext.addObject(book1);
        Assertions.assertFalse(Admin.getBooks().isEmpty());
        Assertions.assertEquals(size + 1, Admin.getBooks().size());
        LibraryContext.addObject(book1);
        Assertions.assertEquals(size + 1, Admin.getBooks().size());
        LibraryContext.removeObject(book1);
        String login = "asf";
        for(CommonUser user:Admin.getUsers())
        {
            if(user.getUserId() > id)
            {
                id = user.getUserId();
            }
            if(user.getLogin().equals(login))
            {
                login = login.concat(user.getLogin());
            }
        }
        id += 1;
        CommonUser user = new CommonUser(login, "alksnfka", "lasmf", "oahoa", id, "oiahfiau", 0);
        size = Admin.getUsers().size();
        LibraryContext.addObject(user);
        Assertions.assertEquals(size + 1, Admin.getUsers().size());
        LibraryContext.addObject(user);
        Assertions.assertEquals(size + 1, Admin.getUsers().size());
        LibraryContext.removeObject(user);
        login = "saf";
        for(Admin admin:Admin.getAdmins())
        {
            if(admin.getAdminId() > id)
            {
                id = admin.getAdminId();
            }
            if(admin.getLogin().equals(login))
            {
                login = login.concat(admin.getLogin());
            }
        }
        id += 1;
        Admin admin = new Admin(login, "alksnfka", "lasmf", "oahoa", "oiahfiau", id);
        size = Admin.getAdmins().size();
        LibraryContext.addObject(admin);
        Assertions.assertEquals(size + 1, Admin.getAdmins().size());
        LibraryContext.addObject(admin);
        Assertions.assertEquals(size + 1, Admin.getAdmins().size());
        LibraryContext.removeObject(admin);
    }
    @Test
    public void testModifyBookAndUser() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {
        Book book = new Book("asfa", "snfak", 1, "aksfna", true, null, null);
        LibraryContext.LibContextInitForTests(true);
        LibraryContext.addObject(book);
        LibraryContext.modifyBook(AttributesNames.name, "mybook", book);
        Assertions.assertEquals("mybook", book.getName());
        Assertions.assertTrue(Admin.getBooks().contains(book));
        for(var book1 : Admin.getBooks()){
            Assertions.assertEquals(book, book1);
        }
        LibraryContext.removeObject(book);
        CommonUser user = new CommonUser("asf", "alksnfka", "lasmf", "oahoa", 1, "oiahfiau", 0);
        LibraryContext.addObject(user);
        LibraryContext.modifyUser(AttributesNames.password, "mypass", user);
        Assertions.assertEquals("mypass", user.getPassword());
        Assertions.assertTrue(Admin.getUsers().contains(user));
        LibraryContext.removeObject(user);
    }
    @Test
    public void testModifyAdmin() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException{
        LibraryContext.LibContextInitForTests(false);
        Admin sAdmin = new Admin("as", "asfkn", "aksnf", "aoishfaiu", "aoisha", 1);
        LibraryContext.addObject(sAdmin);
        LibraryContext.modifyUser(AttributesNames.login, "mylogin", sAdmin);
        Assertions.assertEquals("mylogin", sAdmin.getLogin());
        Assertions.assertTrue(Admin.getAdmins().contains(sAdmin));
        LibraryContext.removeObject(sAdmin);
    }
}