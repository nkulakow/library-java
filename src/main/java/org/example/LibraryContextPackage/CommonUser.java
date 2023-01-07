package org.example.LibraryContextPackage;

import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;

public class CommonUser extends User implements LibraryContextActions{
    @Getter
    private Integer booksNr;
    @Getter
    private int userId;

    public void setUserId(int id) throws InvalidIdException
    {
        if(id < 0)
        {
            throw new InvalidIdException("Niepoprawne id użytkownika.");
        }
        this.userId = id;
    }

    public void setBooksNr(Integer booksNr) throws InvalidBookNumberException
    {
        if(booksNr != null && booksNr < 0)
        {
            throw new InvalidBookNumberException("Ujemna liczba książek.");
        }
        this.booksNr = booksNr;
    }

    public CommonUser(String login, String password, String name, String surname, int userId, String mail, Integer booksNr) throws NullOrEmptyStringException, InvalidBookNumberException, InvalidIdException
    {
        super(login, password, name, surname, mail);
        this.setUserId(userId);
        this.setBooksNr(booksNr);
    }

    public void orderBook(Book book, long months)
    {
        try
        {
            book.setUserId(this.getUserId());
            book.setReturnDate(ZonedDateTime.now().plusMonths(months));
        }
        catch (InvalidIdException e)
        {
            logger.warn("Invalid id in orderBook method " + e.getMessage());
            return;
        }

    }

    public HashSet<Book> showBooks(Admin admin)
    {
        HashSet<LibraryContextActions> books = admin.searchForObject(( String searchPattern ,Admin _admin)->{_admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getBooks()));return _admin.search(searchPattern);}, Integer.valueOf(this.getUserId()).toString());
        HashSet<Book> results = new HashSet<>();
        for(LibraryContextActions libObj:books)
        {
            results.add((Book) libObj);
        }
        return results;
    }

    public void returnBook(Book book)
    {
        try
        {
            book.setUserId(null);
            book.setReturnDate(null);
        }
        catch (InvalidIdException e)
        {
            logger.warn("Invalid id in returnBook method " + e.getMessage());
            return;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getLogin());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommonUser otherUser = (CommonUser) obj;
        if(this.getLogin().equals(otherUser.getLogin()) || this.getUserId() == otherUser.getUserId())
        {
            return true;
        }
        return false;
    }

    @Override
    public String describe()
    {
        String mail;
        if(this.getMail() == null)
        {
            mail = "";
        }
        else {
            mail = this.getMail();
        }
        return Integer.valueOf(this.getUserId()).toString() + " " + this.getName() + " " + this.getSurname() + " " + mail;
    }

    @Override
    public boolean askToJoinCollection(Admin admin) {
        CommonUser user = (CommonUser) this;
        return admin.updateUsers(user, LibObjectsChangeMode.Add);
    }

    @Override
    public boolean askToLeaveCollection(Admin admin) {
        CommonUser user = (CommonUser) this;
        admin.setToSearch(new HashSet<LibraryContextActions>(Admin.getBooks()));
        HashSet<LibraryContextActions> results = admin.search(Integer.valueOf(this.getUserId()).toString());
        if(!results.isEmpty())
        {
            return false;
        }
        return admin.updateUsers(user, LibObjectsChangeMode.Remove);
    }

    @Override
    public boolean modifyUser(AttributesNames attributeName, Object modifiedVal) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {
        if(super.modifyUser(attributeName, modifiedVal)){
            return true;
        }
        else {
             if (attributeName == AttributesNames.password) {
                setPassword((String) modifiedVal);
                return true;
            }
            if (attributeName == AttributesNames.booksNr) {
                setBooksNr((int) modifiedVal);
                return true;
            }
            return false;
        }
    }

}
