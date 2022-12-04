package org.example.GUI;

import lombok.Getter;
import org.example.LibraryContextPackage.InvalidIdException;
import org.example.LibraryContextPackage.LibraryContext;
import org.example.LibraryContextPackage.NullOrEmptyStringException;

//import java.awt.*;

public class LibraryGUI {
    public static final int LOG_FRAME = 0;
    private static Page login_frame;
    private static Page main_page;

    @Getter
    private static LibraryContext libContext;

//    public LibraryGUI() throws NullOrEmptyStringException, InvalidIdException {
//        login_frame = new LogInPage();
//        LibraryGUI.libContext = new LibraryContext();
//        //   int width = Toolkit.getDefaultToolkit().getScreenSize().width;
//        //   int height = Toolkit.getDefaultToolkit().getScreenSize().height;
//        LibraryGUI.main_page = new MainPage();
//
//    }
    public static void init() throws NullOrEmptyStringException, InvalidIdException
    {
        LibraryGUI.libContext = new LibraryContext();
        login_frame = new LogInPage();
      //  int width = Toolkit.getDefaultToolkit().getScreenSize().width;
     //   int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        LibraryGUI.main_page = new MainPage();
    }
    public LibraryGUI() {
    }


//    public Page getFrame() {
//        return LibraryGUI.login_frame;
//    }

    public static void showFrame(final int frame_id) {
        if (frame_id == LibraryGUI.LOG_FRAME)
            LibraryGUI.login_frame.setVisible(true);
    }

    public static void changeAfterLoggedToAdminSite() {
        LibraryGUI.login_frame.setVisible(false);
        LibraryGUI.main_page.setVisible(true);
    }
}