package org.example;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String password;
    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
    }
}
