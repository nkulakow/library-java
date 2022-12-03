package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

public class Book {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String category;
    @Getter @Setter
    private ZonedDateTime returnDate;
    public Book(String name, String category)
    {
        this.name = name;
        this.category = category;
        this.returnDate = null;
    }
}
