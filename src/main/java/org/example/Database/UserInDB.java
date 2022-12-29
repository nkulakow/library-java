package org.example.Database;

import lombok.Getter;
import lombok.Setter;

class UserInDB {
    @Getter @Setter
    int id;
    @Getter @Setter
    String name;
    @Getter @Setter
    String surname;
    @Getter @Setter
    String mail;
    @Getter @Setter
    int booksNumber;

    public UserInDB (final int fid, final String fname, final String fsurname, final String fmail, int booksNr) {
        this.id = fid;
        this.name = fname;
        this.surname = fsurname;
        this.mail = fmail;
        this.booksNumber = booksNr;
    }
}