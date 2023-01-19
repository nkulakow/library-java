package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class LibraryGUI {
    public static final int LOG_FRAME = 0;
    public static final int MAIN_FRAME = 1;
    private static LogInPage login_frame;
    public static MainPage main_page;
    public static Page user_page;

    public static JLabel main_prompt;
    public static void GUIInit() {
        LibraryGUI.main_prompt = ComponentDesigner.makeDefaultLabel("");
        LibraryGUI.login_frame = new LogInPage();
        LibraryGUI.main_page = new MainPage();
        LibraryGUI.user_page = new UserPage();
    }

    public static void showFrame(final int frame_id) {
        if (frame_id == LibraryGUI.LOG_FRAME)
            LibraryGUI.login_frame.setVisible(true);
    }

    public static void changeAfterLoggedToAdminSite() {
        LibraryGUI.login_frame.setVisible(false);
        LibraryGUI.main_page.setVisible(true);
    }

    public static void changeAfterLoggedToUserSite() {
        LibraryGUI.login_frame.setVisible(false);
        LibraryGUI.user_page.setVisible(true);
    }

    public static void sendMessageToLoginPage(final String mes){

    }

    public static class GUIData {
        public static final Color BACKGROUND_COLOR = new Color(252, 252, 189);
    }
}