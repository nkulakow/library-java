package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

public class LibraryContext {
    @Getter @Setter
    private LinkedList<Book> bookList;
    @Getter @Setter
    private LinkedList<User> users;
    @Getter @Setter
    private LinkedList<Admin> admins;
    public LibraryContext()
    {
        this.bookList = new LinkedList<>();
        this.users = new LinkedList<>();
        this.admins = new LinkedList<>();
        this.admins.addLast(new Admin("root", "root", 0));
    }
}
