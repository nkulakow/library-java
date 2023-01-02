package org.example.LibraryContextPackage;

import lombok.Getter;

import java.util.Collection;
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

    public void orderBook(Book book)
    {
        try
        {
            book.setUserId(this.getUserId());
        }
        catch (InvalidIdException e)
        {
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
        //TODO
        return null;
    }

//    @Override
//    public void prepareForSearch()
//    {
//        this.login = "";
//        this.userId = -1;
//    }

    @Override
    public boolean askToJoinCollection(Admin admin) {
        CommonUser user = (CommonUser) this;
        return admin.updateUsers(user, LibObjectsChangeMode.Add);
    }

    @Override
    public boolean askToLeaveCollection(Admin admin) {
        CommonUser user = (CommonUser) this;
        return admin.updateUsers(user, LibObjectsChangeMode.Remove);
    }

//    public LibraryContextActions askToSearch(Admin admin)
//    {
//        CommonUser user = (CommonUser) this;
//        return admin.search(user);
//    }
}
