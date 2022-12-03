package org.example.GUI;

import java.awt.*;
import java.nio.charset.MalformedInputException;

public class LibraryGUI {
    public static final int LOG_FRAME = 0;
    private static Page login_frame;
    private static Page main_page;
    public LibraryGUI() {
        login_frame = new LogInPage();

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        LibraryGUI.main_page = new MainPage();
    }

    public Page getFrame() {
        return LibraryGUI.login_frame;
    }

    public static void showFrame(final int frame_id) {
        if (frame_id == LibraryGUI.LOG_FRAME)
            LibraryGUI.login_frame.setVisible(true);
    }

    public static void changeAfterLogged() {
        LibraryGUI.login_frame.setVisible(false);
        LibraryGUI.main_page.setVisible(true);
    }
}