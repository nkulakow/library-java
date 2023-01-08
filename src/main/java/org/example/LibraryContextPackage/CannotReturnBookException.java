package org.example.LibraryContextPackage;

public class CannotReturnBookException extends Exception{

        public CannotReturnBookException(String error)
        {
            super(error);
        }
}
