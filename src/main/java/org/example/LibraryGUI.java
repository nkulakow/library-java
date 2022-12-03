package org.example;

import lombok.Getter;

import java.awt.*;


public class LibraryGUI{
    public static final int LOG_FRAME = 0;
    private static Page login_frame;
    private static Page main_page;
    @Getter
    private LibraryContext libContext;
    public LibraryGUI() {
        this.libContext = new LibraryContext();
        login_frame = new LogInPage(this.getLibContext());
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        LibraryGUI.main_page = new MainPage(this.getLibContext());
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
    public static void main(String[] args) {
        LibraryGUI lib = new LibraryGUI();
        LibraryGUI.showFrame(0);
    }
}