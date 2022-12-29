package org.example;
import org.example.Database.LibraryDatabase;
import org.example.GUI.LibraryGUI;
import org.example.LibraryContextPackage.InvalidBookNumberException;
import org.example.LibraryContextPackage.InvalidIdException;
import org.example.LibraryContextPackage.LibraryContext;
import org.example.LibraryContextPackage.NullOrEmptyStringException;

public class Main {
    //  static LibraryGUI gui_manager;
    public static void main(String[] args) throws NullOrEmptyStringException, InvalidIdException, InvalidBookNumberException {

        LibraryGUI.GUIInit();
        LibraryContext.LibContextInit();
        LibraryGUI.showFrame(LibraryGUI.LOG_FRAME);
    }

    public static void exit() {
        System.exit(0);
    }
}