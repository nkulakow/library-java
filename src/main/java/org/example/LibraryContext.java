package org.example;

import lombok.Getter;


import java.util.Arrays;
import java.util.LinkedList;

public class LibraryContext {
    @Getter
    private LinkedList<Book> bookList;
    @Getter
    private LinkedList<User> users;
    @Getter
    private LinkedList<Admin> admins;
    @Getter
    private boolean isAdmin;
    public LibraryContext()
    {
        this.bookList = new LinkedList<>();
        this.users = new LinkedList<>();
        this.admins = new LinkedList<>();
        this.admins.addLast(new Admin("root", "root", 0));
    }

    public boolean checkLogging(String login, char[] password) {
        for(Admin ad: this.getAdmins())
        {
            char[] ad_password = ad.getPassword().toCharArray();
            if(login.equals(ad.getLogin()) && Arrays.equals(password, ad_password))
            {
                isAdmin = true;
                return true;
            }
        }
        isAdmin = false;
        return false;
    }
}
