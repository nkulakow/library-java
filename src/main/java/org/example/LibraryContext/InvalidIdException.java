package org.example;

public class InvalidIdException extends Exception{
    public InvalidIdException(String error)
    {
        super(error);
    }
}
