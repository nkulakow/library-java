package org.example.LibraryContextPackage;

import lombok.Getter;

import java.util.Objects;

public class Admin extends User{

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
    public Admin(String name, String password, int id) throws NullOrEmptyStringException, InvalidIdException
    {
        super(name, password);
        this.setAdminId(id);
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
        if(this.getLogin() == otherAdmin.getLogin() || this.getAdminId() == otherAdmin.getAdminId())
        {
            return true;
        }
        return false;
    }
}
