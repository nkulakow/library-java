package org.example;

import lombok.Getter;

import java.util.Objects;

public class User {
    @Getter
    private String login;
    @Getter
    private String password;

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

//    public void setUserId(int id) throws InvalidIdException
//    {
//        if(id < 0)
//        {
//            throw new InvalidIdException("Nieprawidłowe id użytkownika.");
//        }
//        this.userId = id;
//    }
    public User(String name, String password) throws NullOrEmptyStringException
    {
        this.setLogin(name);
        this.setPassword(password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getLogin());
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
        if(this.getLogin() == otherUser.getLogin())
        {
            return true;
        }
        return false;
    }
}
