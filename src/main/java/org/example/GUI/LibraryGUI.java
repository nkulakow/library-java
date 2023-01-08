package org.example.GUI;

public class LibraryGUI {
    public static final int LOG_FRAME = 0;
    public static final int MAIN_FRAME = 1;
    private static Page login_frame;
    public static Page main_page;
    public static Page user_page;
    public static void GUIInit() {
        login_frame = new LogInPage();
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
        LibraryGUI.login_frame.sendMessageToPrompt(mes);
    }
}