package org.example;

public class NullOrEmptyStringException extends Exception
{
    public NullOrEmptyStringException(String error)
    {
        super(error);
    }
}
