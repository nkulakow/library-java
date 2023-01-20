package org.example.GUI;

import oracle.jdbc.driver.PlsqlIbtBindInfo;
import org.example.LibraryContextPackage.LibraryContextActions;

import javax.swing.*;
import java.awt.*;

public class LibraryGUI {
    public static final int LOG_FRAME = 0;
    public static final int MAIN_FRAME = 1;
    public static final int ADMIN = 0;
    public static final int USER = 1;
    public static LogInPage login_frame;
    public static MainPage main_page;
    public static MainPage user_page;
    private static Prompt prompt;

    public static JLabel main_prompt;
    public static void GUIInit() {
        LibraryGUI.main_prompt = ComponentDesigner.makeDefaultLabel("");
        LibraryGUI.login_frame = new LogInPage();
        LibraryGUI.main_page = new MainPage();
        LibraryGUI.user_page = new MainPage();
        LibraryGUI.prompt = new Prompt();
    }

    public static void showFrame(final int frame_id) {
        if (frame_id == LibraryGUI.LOG_FRAME)
            LibraryGUI.login_frame.setVisible(true);
    }

    public static void changeAfterLogged(int mode) {
        LibraryGUI.login_frame.setVisible(false);
        LibraryGUI.main_page.showContent(mode);
        LibraryGUI.main_page.setVisible(true);
    }

    public static void sendMessageToLoginPage(final String mes){

    }

    public static void changePrompt(String text) {
        LibraryGUI.prompt.getLabel().setText(text);
        LibraryGUI.prompt.setSize(15 * text.length(), Prompt.size.height);
        LibraryGUI.prompt.center();
        LibraryGUI.prompt.setVisible(true);
    }

    public static class GUIData {
        public static final Color BACKGROUND_COLOR = new Color(252, 252, 189);
    }
}