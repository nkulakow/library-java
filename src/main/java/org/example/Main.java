package org.example;
import org.example.GUI.LibraryGUI;

public class Main {
    static LibraryGUI gui_manager;
    public static void main(String[] args) {
        new LibraryGUI();
        LibraryGUI.showFrame(LibraryGUI.LOG_FRAME);
    }
}