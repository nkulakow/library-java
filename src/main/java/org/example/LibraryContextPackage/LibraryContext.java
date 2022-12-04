package org.example.LibraryContextPackage;

import lombok.Getter;
import java.util.Arrays;
import java.util.HashSet;
//import java.util.Hashtable;

public class LibraryContext {
    @Getter
    private static HashSet<Book> books;
    @Getter
    private static HashSet<User> users;
    @Getter
    private static HashSet<Admin> admins;
    public static void LibContextInit() throws NullOrEmptyStringException, InvalidIdException
    {
        LibraryContext.books = new HashSet<>();
        LibraryContext.users = new HashSet<>();
        LibraryContext.admins = new HashSet<>();
        LibraryContext.admins.add(new Admin("root", "root", 0));
    }

    static public int checkLogging(String login, char[] password) {
        for(Admin ad: LibraryContext.getAdmins())
        {
            char[] ad_password = ad.getPassword().toCharArray();
            if(login.equals(ad.getLogin()) && Arrays.equals(password, ad_password))
            {
                return 1;
            }
        }
        for(User usr: LibraryContext.getUsers())
        {
            char[] usr_password = usr.getPassword().toCharArray();
            if(login.equals(usr.getLogin()) && Arrays.equals(password, usr_password))
            {
                return 0;
            }
        }
        return -1;
    }

    static public boolean addBook(Book book)
    {
        return books.add(book);
    }

    static public boolean addUser(User user)
    {
        return users.add(user);
    }

    static public boolean addAdmin(Admin admin)
    {
        return admins.add(admin);
    }

    static public boolean removeBook(Book book)
    {
        return books.remove(book);
    }

    static public boolean removeUser(User user)
    {
        return users.remove(user);
    }

    static public boolean removeAdmin(Admin admin)
    {
        return admins.remove(admin);
    }

    static public Book searchForBook(String name)
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

    static public Book searchForBook(int id)
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

    static public User searchForUser(String login)
    {
        for(User user:users)
        {
            if(user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    static public Admin searchForAdmin(int id)
    {
        for(Admin admin:admins)
        {
            if(admin.getAdminId() == id) {
                return admin;
            }
        }
        return null;
    }

    static public Admin searchForAdmin(String login)
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
