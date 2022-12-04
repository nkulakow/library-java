package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Book {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String category;
    @Getter @Setter
    private ZonedDateTime returnDate;

    @Getter @Setter
    private int bookId;
    public Book(String name, String category, int id)
    {
        this.bookId = id;
        this.name = name;
        this.category = category;
        this.returnDate = null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book otherBook = (Book) obj;
        if(this.getBookId() == otherBook.getBookId())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
