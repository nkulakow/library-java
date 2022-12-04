package org.example.LibraryContextPackage;

import lombok.Getter;
import java.util.Arrays;
import java.util.HashSet;
//import java.util.Hashtable;

public class LibraryContext {
    @Getter
    private HashSet<Book> books;
    @Getter
    private HashSet<User> users;
    @Getter
    private HashSet<Admin> admins;
    public LibraryContext() throws NullOrEmptyStringException, InvalidIdException
    {
        this.books = new HashSet<>();
        this.users = new HashSet<>();
        this.admins = new HashSet<>();
        this.admins.add(new Admin("root", "root", 0));
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
        return books.add(book);
    }

    public boolean addUser(User user)
    {
        return users.add(user);
    }

    public boolean addAdmin(Admin admin)
    {
        return admins.add(admin);
    }

    public boolean removeBook(Book book)
    {
        return books.remove(book);
    }

    public boolean removeUser(User user)
    {
        return users.remove(user);
    }

    public boolean removeAdmin(Admin admin)
    {
        return admins.remove(admin);
    }

    public Book searchForBook(String name)
    {
        for(Book book:books)
        {
            if(book.getName().equals(name))
            {
                return book;
            }
        }
        return null;
    }

    public Book searchForBook(int id)
    {
        for(Book book:books)
        {
            if(book.getBookId() == id)
            {
                return book;
            }
        }
        return null;
    }

    public User searchForUser(String login)
    {
        for(User user:users)
        {
            if(user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public Admin searchForAdmin(int id)
    {
        for(Admin admin:admins)
        {
            if(admin.getAdminId() == id) {
                return admin;
            }
        }
        return null;
    }

    public Admin searchForAdmin(String login)
    {
        for(Admin admin:admins)
        {
            if(admin.getLogin().equals(login)) {
                return admin;
            }
        }
        return null;
    }
}
