package org.example.LibraryContextPackage;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Book {
    @Getter
    private String name;
    @Getter
    private String category;
    @Getter @Setter
    private ZonedDateTime returnDate;
    @Getter
    private String author;
    @Getter
    private int bookId;
    @Getter
    private Integer userId;
    @Getter
    private Boolean available;

    public void setAvailable(boolean available)
    {
        this.available = available;
    }
    public void setUserId(Integer userId) throws InvalidIdException
    {
        if(userId != null && userId < 0)
        {
            throw new InvalidIdException("Niepoprawne id użytkownika.");
        }
        this.userId = userId;
    }
    public void setAuthor(String author) throws NullOrEmptyStringException
    {
        if(author != null && author.isEmpty())
        {
            throw new NullOrEmptyStringException("Brak autora.");
        }
        this.author = author;
    }
    public void setName(String name) throws NullOrEmptyStringException
    {
        if(name == null || name.isEmpty())
        {
            throw new NullOrEmptyStringException("Nazwa książki nie może być pusta.");
        }
        this.name = name;
    }

    public void setCategory(String category) throws NullOrEmptyStringException
    {
        if(category == null || category.isEmpty())
        {
            throw new NullOrEmptyStringException("Nazwa kategorii nie może być pusta.");
        }
        this.category = category;
    }

    public void setBookId(int id) throws InvalidIdException
    {
        if(id < 0)
        {
            throw new InvalidIdException("Niepoprawne id książki.");
        }
        this.bookId = id;
    }
    public Book(String name, String category, int id, String author, boolean available, Integer userId, ZonedDateTime date) throws NullOrEmptyStringException, InvalidIdException
    {
        this.setBookId(id);
        this.setName(name);
        this.setCategory(category);
        this.setReturnDate(date);
        this.setAuthor(author);
        this.setAvailable(available);
        this.setUserId(userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getBookId());
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
