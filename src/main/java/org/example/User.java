package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class User {
    @Getter @Setter
    private String login;
    @Getter @Setter
    private String password;

    @Getter @Setter
    private int userId;
    public User(String name, String password, int id)
    {
        this.userId = id;
        this.login = name;
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getUserId());
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
        if(this.getUserId() == otherUser.getUserId())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
