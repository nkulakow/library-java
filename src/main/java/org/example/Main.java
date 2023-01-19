package org.example;
import org.example.GUI.LibraryGUI;
import org.example.LibraryContextPackage.LibraryContext;

public class Main {
    //  static LibraryGUI gui_manager;
    public static void main(String[] args) {


        LibraryGUI.GUIInit();
        LibraryContext.LibContextInit();
        LibraryGUI.showFrame(LibraryGUI.LOG_FRAME);
//    LibraryGUI.changeAfterLogged(LibraryGUI.USER);
    }

    public static void exit() {
        System.exit(0);
    }
}