package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Admin extends User{
    @Getter @Setter
    private int adminId;
    public Admin(String name, String password, int userId, int adminId)
    {
        super(name, password, userId);
        this.adminId = adminId;
    }
}
