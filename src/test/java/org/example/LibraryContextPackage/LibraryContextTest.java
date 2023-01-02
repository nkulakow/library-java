package org.example.LibraryContextPackage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryContextTest {

    @Test
    public void testSearchForObject() throws NullOrEmptyStringException, InvalidIdException
    {
        Book book = new Book("asfa", "snfak", 0, "aksfna", true, null, null);
        LibraryContext.LibContextInit();
        LibraryContext.addObject(book);
        HashSet<LibraryContextActions> results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getBooks()));return admin.search(searchPattern);},"asfa");
        Book result = (Book) results.iterator().next();
        Assertions.assertEquals(book, result);

    }
    @Test
    public void testAddObject() throws NullOrEmptyStringException, InvalidIdException
    {
        Book book = new Book("asfa", "snfak", 0, "aksfna", true, null, null);
        LibraryContext.LibContextInit();
        LibraryContext.addObject(book);
        Assertions.assertFalse(Admin.getBooks().isEmpty());
        Assertions.assertEquals(1, Admin.getBooks().size());
    }
}