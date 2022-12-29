package org.example.LibraryContextPackage;

import lombok.Getter;
import java.util.HashSet;
import java.util.Base64;

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
        LibraryContext.admins.add(new Admin("nkulakow", "bmt1bGFrb3c=", 0));
        LibraryContext.admins.add(new Admin("mwawrzy1", "bXdhd3J6eTE=", 1));
        LibraryContext.admins.add(new Admin("mkielbus", "bWtpZWxidXM=", 2));
        LibraryContext.admins.add(new Admin("jhapunik", "amhhcHVuaWs=", 3));
    }

    static public boolean checkLoggingAdmins(String login, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        for (Admin ad : LibraryContext.getAdmins()) {
            if (login.equals(ad.getLogin()) && encodedPassword.equals(ad.getPassword())) {
                return true;
            }
        }
        return false;
    }
    static public boolean checkLoggingUsers(String login, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        for(User usr: LibraryContext.getUsers())
        {
            if(login.equals(usr.getLogin()) && encodedPassword.equals(usr.getPassword()))
            {
                return true;
            }
        }
        return false;
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
