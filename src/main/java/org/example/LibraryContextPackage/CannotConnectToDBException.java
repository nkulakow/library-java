package org.example.LibraryContextPackage;

public class CannotConnectToDBException extends Exception{
    public CannotConnectToDBException(String error)
    {
        super(error);
    }
}
