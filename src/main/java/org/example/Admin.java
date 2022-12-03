package org.example;

import lombok.Getter;
import lombok.Setter;

public class Admin extends User{
    @Getter @Setter
    private int adminId;
    public Admin(String name, String password, int adminId)
    {
        super(name, password);
        this.adminId = adminId;
    }
}
