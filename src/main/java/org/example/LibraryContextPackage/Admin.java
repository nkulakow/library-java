package org.example.LibraryContextPackage;

import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;

public class Admin extends User{

    @Getter
    private static HashSet<Book> books = new HashSet<>();
    @Getter
    private static HashSet<User> users = new HashSet<>();
    @Getter
    private static HashSet<Admin> admins = new HashSet<>();
    @Getter
    private int adminId;
    public void setAdminId(int id) throws InvalidIdException
    {
        if(id < 0)
        {
            throw new InvalidIdException("Nieprawidłowe id admina.");
        }
        this.adminId = id;
    }
    public Admin(String login, String password, String name, String surname, int userId, String mail, Integer booksNr, int id) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException
    {
        super(login, password, name, surname, userId, mail, booksNr);
        this.setAdminId(id);
    }

    public boolean addBook(Book book) {return books.add(book); }

    public boolean addUser(User user) {return users.add(user); }

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

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getAdminId(), this.getLogin());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin otherAdmin = (Admin) obj;
        if(this.getLogin().equals(otherAdmin.getLogin()) || this.getAdminId() == otherAdmin.getAdminId() || this.getUserId() == otherAdmin.getUserId())
        {
            return true;
        }
        return false;
    }
}
