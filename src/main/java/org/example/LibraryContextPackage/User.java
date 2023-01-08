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
    public void setLogin(String login) throws NullOrEmptyStringException, InvalidLoginException {
        if(login == null || login.isEmpty())
        {
            throw new NullOrEmptyStringException("Login użytkownika nie może być pusty.");
        }
        if( this.getClass() == CommonUser.class){
        for( var user : Admin.getUsers()){
            if( login.equals(user.getLogin()) && user != this){
                throw new InvalidLoginException("CommonUser login already exists");
            }
        }}
        else{
            for( var user : Admin.getAdmins()){
                if( login.equals(user.getLogin()) && user != this){
                    throw new InvalidLoginException("Admin login already exists");
                }
            }
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

    public User(String login, String password, String name, String surname, String mail) throws NullOrEmptyStringException, InvalidLoginException {
        this.setLogin(login);
        this.setPassword(password);
        this.setName(name);
        this.setSurname((surname));
        this.setMail(mail);
    }

    public boolean modifyUser(AttributesNames attributeName, String modifiedVal) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException, InvalidLoginException {
        if (attributeName == AttributesNames.login){
            setLogin( modifiedVal);
            return true;
        } else if (attributeName == AttributesNames.name) {
            setName( modifiedVal);
            return true;
        } else if (attributeName == AttributesNames.surname) {
            setSurname( modifiedVal);
            return true;
        }
        else if (attributeName == AttributesNames.mail) {
            setMail( modifiedVal);
            return true;
        }
        return false;
    }

}
