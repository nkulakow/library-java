package org.example.LibraryContextPackage;

import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;

public class CommonUser extends User implements LibraryContextActions{
    @Getter
    private Integer booksNr;
    @Getter
    private int userId;

    /**
     * Sets User ID.
     */
    public void setUserId(Integer id) throws InvalidIdException
    {
        if( id != null && id <= 0)
        {
            throw new InvalidIdException("Invalid User ID.");
        }
        this.userId = id;
    }

    /**
     * Sets number of the books.
     */
    public void setBooksNr(Integer booksNr) throws InvalidBookNumberException
    {
        if(booksNr != null && booksNr < 0)
        {
            throw new InvalidBookNumberException("Negative books number.");
        }
        this.booksNr = booksNr;
    }

    /**
     * Creates CommonUser Object.
     */
    public CommonUser(String login, String password, String name, String surname, Integer userId, String mail, Integer booksNr) throws NullOrEmptyStringException, InvalidBookNumberException, InvalidIdException, InvalidLoginException {
        super(login, password, name, surname, mail);
        this.setUserId(userId);
        this.setBooksNr(booksNr);
    }

    /**
     * Allows this CommonUser to borrow ordered earlier book for given months.
     */
    public void orderBook(Book book, long months)
    {
        try
        {
            book.setUserId(this.getUserId());
            book.setReturnDate(ZonedDateTime.now().plusMonths(months));
            this.booksNr += 1;
        }
        catch (InvalidIdException e)
        {
            logger.warn("Invalid id in orderBook method " + e.getMessage());
        }

    }

    public HashSet<Book> showBooks(Admin admin)
    {
        HashSet<LibraryContextActions> books = admin.searchForObject(( String searchPattern ,Admin _admin)->{_admin.setToSearch(new HashSet<>(Admin.getBooks()));return _admin.search(searchPattern);}, Integer.valueOf(this.getUserId()).toString());
        HashSet<Book> results = new HashSet<>();
        for(LibraryContextActions libObj:books)
        {
            results.add((Book) libObj);
        }
        return results;
    }

    /**
     * Allows this CommonUser to return borrowed earlier book.
     */
    public void returnBook(Book book)
    {
        try
        {
            book.setUserId(null);
            book.setReturnDate(null);
            if(this.booksNr - 1 >= 0)
                this.booksNr -= 1;
        }
        catch (InvalidIdException e)
        {
            logger.warn("Invalid id in returnBook method " + e.getMessage());
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

    /**
     * Returns string description of this CommonUser.
     */
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
        CommonUser user = this;
        return admin.updateUsers(user, LibObjectsChangeMode.Add);
    }

    @Override
    public boolean askToLeaveCollection(Admin admin) {
        CommonUser user = this;
        if(this.booksNr > 0)
            return false;
        for(ArrayDeque<CommonUser> queue:LibraryContext.getTakenBooks().values())
        {
            if(queue.contains(user))
                return false;
        }
        return admin.updateUsers(user, LibObjectsChangeMode.Remove);
    }

    /**
     * Modifies this CommonUser selected attribute.
     */
    @Override
    public boolean modifyUser(AttributesNames attributeName, String modifiedVal) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException {
        if(super.modifyUser(attributeName, modifiedVal)){
            return true;
        }
        else {
             if (attributeName == AttributesNames.password) {
                setPassword(modifiedVal);
                return true;
            }
            if (attributeName == AttributesNames.booksNr) {
                setBooksNr(Integer.valueOf(modifiedVal));
                return true;
            }
            return false;
        }
    }

}
