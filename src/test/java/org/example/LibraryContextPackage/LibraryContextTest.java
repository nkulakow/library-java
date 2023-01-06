package org.example.LibraryContextPackage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryContextTest {

    @Test
    public void testRemoveObject() throws InvalidIdException, InvalidBookNumberException, NullOrEmptyStringException
    {
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
        CommonUser user = new CommonUser(login, "alksnfka", "lasmf", "oahoa", id, "oiahfiau", 0);
        LibraryContext.LibContextInit();
        LibraryContext.addObject(user);
        int size = Admin.getUsers().size();
        LibraryContext.removeObject(user);
        Assertions.assertEquals(size - 1, Admin.getUsers().size());
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
    public void testSearchForObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException
    {
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
        LibraryContext.LibContextInit();
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
    public void testAddObject() throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException
    {
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
        LibraryContext.LibContextInit();
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
}