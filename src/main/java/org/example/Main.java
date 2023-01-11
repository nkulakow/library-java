package org.example;
import org.example.GUI.LibraryGUI;
import org.example.LibraryContextPackage.LibraryContext;

public class Main {
    public static void main(String[] args) {
        LibraryGUI.GUIInit();
        LibraryContext.LibContextInit();
        LibraryGUI.showFrame(LibraryGUI.LOG_FRAME);
    }

    public static void exit() {
        System.exit(0);
    }
}