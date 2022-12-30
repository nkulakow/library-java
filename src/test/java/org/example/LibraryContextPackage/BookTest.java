package org.example.LibraryContextPackage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    public void testBook() throws NullOrEmptyStringException, InvalidIdException
    {
        Book book = new Book("a", "b", 0, "c", true, null, null);
        Assertions.assertEquals("a", book.getName());
        Assertions.assertEquals("b", book.getCategory());
        Assertions.assertEquals(0, book.getBookId());
        Assertions.assertEquals("c", book.getAuthor());
        Assertions.assertEquals(true, book.getAvailable());
        Assertions.assertNull(book.getUserId());
        Assertions.assertNull(book.getReturnDate());
    }

}