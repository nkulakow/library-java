package org.example.LibraryContextPackage;

public class InvalidLoginException extends Exception{

        public InvalidLoginException(String error)
        {
            super(error);
        }
}
