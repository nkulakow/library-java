package org.example;

import lombok.Getter;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

public class LibraryContext {
    @Getter
    private HashSet<Book> books;
    @Getter
    private Hashtable<Integer, Book> booksHash;
    @Getter
    private HashSet<User> users;
    @Getter
    private Hashtable<Integer, User> usersHash;
    @Getter
    private HashSet<Admin> admins;
    @Getter Hashtable<Integer, Admin> adminsHash;
    public LibraryContext()
    {
        this.books = new HashSet<>();
        this.booksHash = new Hashtable<>();
        this.users = new HashSet<>();
        this.usersHash = new Hashtable<>();
        this.admins = new HashSet<>();
        this.adminsHash = new Hashtable<>();
        this.admins.add(new Admin("root", "root", 0, 0));
        this.adminsHash.put(0, new Admin("root", "root", 0, 0));
    }

    public int checkLogging(String login, char[] password) {
        for(Admin ad: this.getAdmins())
        {
            char[] ad_password = ad.getPassword().toCharArray();
            if(login.equals(ad.getLogin()) && Arrays.equals(password, ad_password))
            {
                return 1;
            }
        }
        for(User usr: this.getUsers())
        {
            char[] usr_password = usr.getPassword().toCharArray();
            if(login.equals(usr.getLogin()) && Arrays.equals(password, usr_password))
            {
                return 0;
            }
        }
        return -1;
    }

    public boolean addBook(Book book)
    {
        if(this.addBookSet(book))
        {
            this.booksHash.put(book.getBookId(), book);
            return true;
        }
        return false;
    }
    private boolean addBookSet(Book book)
    {
        return books.add(book);
    }

    public boolean addUser(User user)
    {
        if(this.addUserSet(user))
        {
            this.usersHash.put(user.getUserId(), user);
            return true;
        }
        return false;
    }
    private boolean addUserSet(User user)
    {
        return users.add(user);
    }

    public boolean addAdmin(Admin admin)
    {
        if(this.addAdminSet(admin))
        {
            this.adminsHash.put(admin.getAdminId(), admin);
            return true;
        }
        return false;
    }
    private boolean addAdminSet(Admin admin)
    {
        return admins.add(admin);
    }

    public boolean removeBook(Book book)
    {
        if(this.removeBookSet(book))
        {
            this.booksHash.remove(book.getBookId());
            return true;
        }
        return false;
    }
    private boolean removeBookSet(Book book)
    {
        return books.remove(book);
    }

    public boolean removeUser(User user)
    {
        if(this.removeUserSet(user))
        {
            this.usersHash.remove(user.getUserId());
            return true;
        }
        return false;
    }
    private boolean removeUserSet(User user)
    {
        return users.remove(user);
    }

    public boolean removeAdmin(Admin admin)
    {
        if(this.removeAdminSet(admin))
        {
            this.adminsHash.remove(admin.getAdminId());
            return true;
        }
        return false;
    }
    private boolean removeAdminSet(Admin admin)
    {
        return admins.remove(admin);
    }
}
