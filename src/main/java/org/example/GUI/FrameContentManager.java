package org.example.GUI;

import org.example.LibraryContextPackage.*;
import org.example.Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

abstract class FrameContentManager {
    public static final int USERS = 0;
    public static final int BOOKS = 1;

    public static final int ORDERED_BOOKS = 2;
    public static final int BORROWED_BOOKS = 3;
    public static final int ADMIN_USER_MOD = 4;
    public static final int USER_USER_MOD = 5;

    public FrameContentManager() {

    }
    abstract void manage();

    public static void noDataPanic(JPanel content_panel) {
        LibraryGUI.main_prompt.setText("No data found");
        content_panel.setLayout(new FlowLayout());
    }

    public static JPanel getBottomFramePanel() {
        var content_panel = LibraryGUI.main_page.getContent_panel();
        var content_panel_layout = (BorderLayout) content_panel.getLayout();
        var center_panel = (JPanel) content_panel_layout.getLayoutComponent(BorderLayout.CENTER);
        var center_panel_layout = (BorderLayout) center_panel.getLayout();
        return (JPanel) center_panel_layout.getLayoutComponent(BorderLayout.SOUTH);
    }
}

class Exiter extends FrameContentManager {
    @Override
    void manage() {
        Main.exit();
    }
}

class AppLogger extends FrameContentManager {

    @Override
    void manage() {
        LibraryGUI.main_prompt.setFont(ComponentDesigner.getDefaultFont(25));
        String login = LibraryGUI.login_frame.getLogin_field().getText();
        String password = String.valueOf(LibraryGUI.login_frame.getPassword_field().getPassword());
        if (LibraryGUI.login_frame.getUserbox().isSelected())
        {
            if(LibraryContext.checkLoggingUsers(login, password)) {
                LibraryGUI.changeAfterLogged(LibraryGUI.USER);
            }
            else {
                LibraryGUI.main_prompt.setText("Incorrect login and/or password");
            }
        }
        else if (LibraryGUI.login_frame.getAdminbox().isSelected()) {
            if (LibraryContext.checkLoggingAdmins(login, password)) {
                LibraryGUI.changeAfterLogged(LibraryGUI.ADMIN);
            }
            else {
                LibraryGUI.main_prompt.setText("Incorrect login and/or password");
            }
        }
        else {
            LibraryGUI.main_prompt.setText("Select 'User' or 'Admin'");
        }
    }
}

class BookTableShower extends FrameContentManager {
    @Override
    void manage() {
        var table = ComponentDesigner.makeBookTable(new String[][]{});
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);

        Searcher.search_mode = Searcher.BOOKS;
    }
}

class UserTableShower extends FrameContentManager {
    @Override
    void manage() {
        var table = ComponentDesigner.makeUserTable(new String[][]{});
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);

        Searcher.search_mode = Searcher.USERS;
    }
}

class ModifyPanelShower extends FrameContentManager {
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();
        bottom_panel.removeAll();
        bottom_panel.add(ComponentDesigner.makeModifyPanel());
        bottom_panel.validate();
    }
}

class AddingPanelShower extends FrameContentManager {
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();
        bottom_panel.removeAll();
        bottom_panel.add(ComponentDesigner.makeAddingPanel());
        bottom_panel.validate();
    }
}

class Searcher extends FrameContentManager {
    public static final int USERS = 0;
    public static final int BOOKS = 1;
    public static int search_mode;
    @Override
    void manage() {
        var pattern = LibraryGUI.main_page.getSearch_field().getText();
        HashSet<LibraryContextActions> results;
        int data_length;
        int columns_number;

        if(Searcher.search_mode == Searcher.USERS) {
            results = LibraryContext.searchForObject((String searchPattern, Admin admin) -> {
                admin.setToSearch(new HashSet<>(Admin.getUsers()));
                return admin.search(searchPattern);
            }, pattern);
            columns_number = 5;
        } else {
            results = LibraryContext.searchForObject((String searchPattern, Admin admin) -> {
                admin.setToSearch(new HashSet<>(Admin.getBooks()));
                return admin.search(searchPattern);
            }, pattern);
            columns_number = 4;
        }
        data_length = results.size();

        String[][] data = new String[data_length][columns_number];
        int i = 0;
        for(var object : results) {
            data[i] = object.getRepresentation();
            i++;
        }
        JTable table;
        if(Searcher.search_mode == Searcher.USERS)
            table = ComponentDesigner.makeUserTable(data);
        else
            table = ComponentDesigner.makeBookTable(data);
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);
    }
}

class MockManager extends FrameContentManager {
    @Override
    void manage() {

    }
}