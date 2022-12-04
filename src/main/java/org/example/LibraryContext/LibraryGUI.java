package org.example;

import lombok.Getter;

import java.awt.*;


public class LibraryGUI{
    public static final int LOG_FRAME = 0;
    private static Page login_frame;
    private static Page main_page;
    @Getter
    private static LibraryContext libContext;

    public static void init() throws NullOrEmptyStringException, InvalidIdException
    {
        LibraryGUI.libContext = new LibraryContext();
        login_frame = new LogInPage();
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        LibraryGUI.main_page = new MainPage();
    }
    private LibraryGUI() {
    }

    public static Page getFrame() {
        return LibraryGUI.login_frame;
    }

    public static void showFrame(final int frame_id) {
        if (frame_id == LibraryGUI.LOG_FRAME)
            LibraryGUI.login_frame.setVisible(true);
    }

    public static void changeAfterLoggedToAdminSite() {
        LibraryGUI.login_frame.setVisible(false);
        LibraryGUI.main_page.setVisible(true);
    }
    public static void main(String[] args) throws NullOrEmptyStringException, InvalidIdException{
        LibraryGUI.init();
        LibraryGUI.showFrame(LibraryGUI.LOG_FRAME);
    }
}