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
        return new HashSet<Book>(books);
    }

    public static HashSet<CommonUser> getUsers()
    {
        return new HashSet<CommonUser>(users);
    }

    public static HashSet<Admin> getAdmins()
    {
        return new HashSet<Admin>(admins);
    }

    public boolean updateBooks(Book book, LibObjectsChangeMode mode)
    {
        boolean toReturn;
        switch (mode)
        {
            case Add:
                toReturn = Admin.books.add(book);
                break;
            case Remove:
                toReturn = Admin.books.remove(book);
                break;
            default:
                toReturn = false;
        }
        return toReturn;
    }

    public boolean updateUsers(CommonUser user, LibObjectsChangeMode mode)
    {
        boolean toReturn;
        switch (mode)
        {
            case Add:
                toReturn = Admin.users.add(user);
                break;
            case Remove:
                toReturn = Admin.users.remove(user);
                break;
            default:
                toReturn = false;
        }
        return toReturn;
    }

    public boolean updateAdmins(Admin admin, LibObjectsChangeMode mode)
    {
        boolean toReturn;
        switch (mode)
        {
            case Add:
                toReturn = Admin.admins.add(admin);
                break;
            case Remove:
                toReturn = Admin.admins.remove(admin);
                break;
            default:
                toReturn = false;
        }
        return toReturn;
    }
    public void setAdminId(int id) throws InvalidIdException
    {
        if(id < 0)
        {
            throw new InvalidIdException("Nieprawidłowe id admina.");
        }
        this.adminId = id;
    }
    public Admin(String login, String password, String name, String surname, String mail, int id) throws NullOrEmptyStringException, InvalidIdException
    {
        super(login, password, name, surname, mail);
        this.setAdminId(id);
    }


    public HashSet<LibraryContextActions> search(String search_pattern)
    {
        HashSet<LibraryContextActions> results = new HashSet<>();
        String[] patterns = search_pattern.split(" ");
        for(String pattern:patterns)
        {
            for(LibraryContextActions libObj: this.toSearch)
            {
                if(libObj.describe().toLowerCase().contains(pattern.toLowerCase()))
                {
                    results.add(libObj);
                }
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
        if(this.getLogin().equals(otherAdmin.getLogin()) || this.getAdminId() == otherAdmin.getAdminId())
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
            mail = "";
        else
            mail = this.getMail();
        return Integer.valueOf(this.getAdminId()).toString() + " " + this.getName() + " " + this.getSurname() + " " + mail;
    }

    @Override
    public boolean askToJoinCollection(Admin admin) {
        Admin newAdmin = (Admin) this;
        return admin.updateAdmins(newAdmin, LibObjectsChangeMode.Add);
    }

    @Override
    public boolean askToLeaveCollection(Admin admin) {
        Admin newAdmin = (Admin) this;
        return admin.updateAdmins(newAdmin, LibObjectsChangeMode.Remove);
    }

    @Override
    public boolean modifyUser(AttributesNames attributeName, Object modifiedVal) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {
        if(super.modifyUser(attributeName, modifiedVal)){
            return true;
        }
        else {
            if (attributeName == AttributesNames.password) {
                byte[] bytePasswd = ((String) modifiedVal).getBytes();
                setPassword(Base64.getEncoder().encodeToString(bytePasswd));
                return true;
            }
            return false;
        }
    }

}
