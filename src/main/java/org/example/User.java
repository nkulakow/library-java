package org.example;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter @Setter
    private String login;
    @Getter @Setter
    private String password;
    public User(String name, String password)
    {
        this.login = name;
        this.password = password;
    }
}
