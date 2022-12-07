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

    public UserInDB (final int fid, final String fname, final String fsurname) {
        this.id = fid;
        this.name = fname;
        this.surname = fsurname;
    }
}