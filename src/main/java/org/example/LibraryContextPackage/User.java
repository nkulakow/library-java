package org.example.LibraryContextPackage;

import lombok.Getter;

import java.util.Objects;

public class User {
    @Getter
    private String login;
    @Getter
    private String password;
    @Getter
    private String name;
    @Getter
    private String surname;
    @Getter
    private int userId;
    @Getter
    private String mail;
    @Getter
    private Integer booksNr;


    public void setBooksNr(Integer booksNr) throws InvalidBookNumberException
    {
        if(booksNr != null && booksNr < 0)
        {
            throw new InvalidBookNumberException("Ujemna liczba książek.");
        }
        this.booksNr = booksNr;
    }
    public void setMail(String mail) throws NullOrEmptyStringException
    {
        if(mail != null && mail.isEmpty())
        {
            throw new NullOrEmptyStringException("Brak maila.");
        }
        this.mail = mail;
    }

    public void setUserId(int id) throws InvalidIdException
    {
        if(id < 0)
        {
            throw new InvalidIdException("Niepoprawne id użytkownika.");
        }
        this.userId = id;
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

    public User(String login, String password, String name, String surname, int userId, String mail, Integer booksNr) throws NullOrEmptyStringException, InvalidBookNumberException, InvalidIdException
    {
        this.setLogin(login);
        this.setPassword(password);
        this.setName(name);
        this.setSurname((surname));
        this.setUserId(userId);
        this.setMail(mail);
        this.setBooksNr(booksNr);
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
        User otherUser = (User) obj;
        if(this.getLogin().equals(otherUser.getLogin()) || this.getUserId() == otherUser.getUserId())
        {
            return true;
        }
        return false;
    }
}
