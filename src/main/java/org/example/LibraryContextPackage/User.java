package org.example.LibraryContextPackage;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public abstract class User {
    @Getter
    protected String login;
    @Getter
    private String password;
    @Getter
    private String name;
    @Getter
    private String surname;
    @Getter
    private String mail;
    protected final Logger logger = LogManager.getLogger(org.example.LibraryContextPackage.User.class);

    public void setMail(String mail) throws NullOrEmptyStringException
    {
        if(mail != null && mail.isEmpty())
        {
            throw new NullOrEmptyStringException("Brak maila.");
        }
        this.mail = mail;
    }

    public void setSurname(String surname) throws NullOrEmptyStringException
    {
        if(surname == null || surname.isEmpty())
        {
            throw new NullOrEmptyStringException("Brak nazwiska.");
        }
        this.surname = surname;
    }
    public void setName(String name) throws NullOrEmptyStringException
    {
        if(name == null || name.isEmpty())
        {
            throw new NullOrEmptyStringException("Brak imienia.");
        }
        this.name = name;
    }
    public void setLogin(String login) throws NullOrEmptyStringException
    {
        if(login == null || login.isEmpty())
        {
            throw new NullOrEmptyStringException("Login użytkownika nie może być pusty.");
        }
        this.login = login;
    }

    public void setPassword(String password) throws NullOrEmptyStringException
    {
        if(password == null || password.isEmpty())
        {
            throw new NullOrEmptyStringException("Hasło użytkownika nie może być puste.");
        }
        this.password = password;
    }

    public User(String login, String password, String name, String surname, String mail) throws NullOrEmptyStringException
    {
        this.setLogin(login);
        this.setPassword(password);
        this.setName(name);
        this.setSurname((surname));
        this.setMail(mail);
    }

    public boolean modifyUser(AttributesNames attributeName, Object modifiedVal) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {
        if (attributeName == AttributesNames.login){
            setLogin((String) modifiedVal);
            return true;
        } else if (attributeName == AttributesNames.name) {
            setName((String) modifiedVal);
            return true;
        } else if (attributeName == AttributesNames.surname) {
            setSurname((String) modifiedVal);
            return true;
        }
        else if (attributeName == AttributesNames.mail) {
            setMail((String) modifiedVal);
            return true;
        }
        return false;
    }

}
