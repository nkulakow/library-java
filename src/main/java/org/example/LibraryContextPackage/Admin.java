package org.example.LibraryContextPackage;

import lombok.Getter;
import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;

public class Admin extends User implements LibraryContextActions{

    private static HashSet<Book> books = new HashSet<>();

    private static HashSet<CommonUser> users = new HashSet<>();

    private static HashSet<Admin> admins = new HashSet<>();

    private HashSet<LibraryContextActions> toSearch;
    @Getter
    private int adminId;

    public void setToSearch(HashSet<LibraryContextActions> set)
    {
        this.toSearch = set;
    }
    public static HashSet<Book> getBooks()
    {
        return books;
    }

    /**
     * Used for tests to clear all books, admins and users as they're static attributes.
     */
    public static void clearAll(){
        books.clear();
        users.clear();
        admins.clear();
    }

    /**
     * Returns all CommonUsers.
     */
    public static HashSet<CommonUser> getUsers()
    {
        return new HashSet<>(users);
    }

    /**
     * Returns all Admins.
     */
    public static HashSet<Admin> getAdmins()
    {
        return new HashSet<>(admins);
    }

    /**
     * Adds or removes Books from local data. Returns true if succeeded.
     */
    public boolean updateBooks(Book book, LibObjectsChangeMode mode)
    {
        return switch (mode) {
            case Add -> Admin.books.add(book);
            case Remove -> Admin.books.remove(book);
        };
    }

    /**
     * Adds or removes CommonUsers from local data. Returns true if succeeded.
     */
    public boolean updateUsers(CommonUser user, LibObjectsChangeMode mode)
    {
        return switch (mode) {
            case Add -> Admin.users.add(user);
            case Remove -> Admin.users.remove(user);
        };
    }

    /**
     * Adds or removes Admins from local data. Returns true if succeeded.
     */
    public boolean updateAdmins(Admin admin, LibObjectsChangeMode mode)
    {
        return switch (mode) {
            case Add -> Admin.admins.add(admin);
            case Remove -> Admin.admins.remove(admin);
        };
    }

    /**
     * Sets Admin ID
     */
    public void setAdminId(int id) throws InvalidIdException
    {
        if(id < 0)
        {
            throw new InvalidIdException("Invalid Admin ID.");
        }
        this.adminId = id;
    }

    /**
     * Creates Admin Object.
     */
    public Admin(String login, String password, String name, String surname, String mail, int id) throws NullOrEmptyStringException, InvalidIdException, InvalidLoginException {
        super(login, password, name, surname, mail);
        this.setAdminId(id);
    }


    public HashSet<LibraryContextActions> search(String search_pattern)
    {
        HashSet<LibraryContextActions> results = new HashSet<>();
        String[] patterns = search_pattern.split(" ");
        String toSearch = "";
        for(String pattern: patterns)
        {
            toSearch = toSearch.concat(pattern + " ");
        }
        if(toSearch.length() > 0)
            toSearch = toSearch.substring(0, toSearch.length() - 1);
        for(LibraryContextActions libObj: this.toSearch)
        {
            if(libObj.describe().toLowerCase().contains(toSearch.toLowerCase()))
            {
                results.add(libObj);
            }
        }
        return results;
    }

    public boolean addObject(LibraryContextActions libObject)
    {
        return libObject.askToJoinCollection(this);
    }

    public boolean removeObject(LibraryContextActions libObject)
    {

        return libObject.askToLeaveCollection(this);
    }

    public HashSet<LibraryContextActions> searchForObject(Isearch searchObject, String searchPattern)
    {
        return searchObject.searchLib(searchPattern, this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getAdminId(), this.getLogin());
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
        return this.getLogin().equals(otherAdmin.getLogin()) || this.getAdminId() == otherAdmin.getAdminId();
    }

    /**
     * Returns string description of this Admin.
     */
    @Override
    public String describe()
    {
        String mail;
        if(this.getMail() == null)
            mail = "";
        else
            mail = this.getMail();
        return Integer.valueOf(this.getAdminId()).toString() + " " + this.getName() + " " + this.getSurname() + " " + mail;
    }

    @Override
    public boolean askToJoinCollection(Admin admin) {
        Admin newAdmin = this;
        return admin.updateAdmins(newAdmin, LibObjectsChangeMode.Add);
    }

    @Override
    public boolean askToLeaveCollection(Admin admin) {
        Admin newAdmin = this;
        return admin.updateAdmins(newAdmin, LibObjectsChangeMode.Remove);
    }

    /**
     * Modifies this Admin selected attribute.
     */
    @Override
    public boolean modifyUser(AttributesNames attributeName, String modifiedVal) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException {
        if(super.modifyUser(attributeName, modifiedVal)){
            return true;
        }
        else {
            if (attributeName == AttributesNames.password) {
                byte[] bytePasswd = (modifiedVal).getBytes();
                setPassword(Base64.getEncoder().encodeToString(bytePasswd));
                return true;
            }
            return false;
        }
    }

    @Override
    public String[] getRepresentation() {
        return new String[0];
    }

    /**
     * Finds CommonUser by given ID.
     */
    public static CommonUser findUserById(int userID) throws InvalidIdException {
        for (var user : Admin.getUsers()){
            if(user.getUserId() == userID){
                return user;
            }
        }
        throw new InvalidIdException("No common user with given ID");
    }

    /**
     * Finds Book by given ID.
     */
    public static Book findBookById(int bookId) throws InvalidIdException {
        for (var book : Admin.getBooks()){
            if(book.getBookId() == bookId){
                return book;
            }
        }
        throw new InvalidIdException("No common book with given ID");
    }

}
