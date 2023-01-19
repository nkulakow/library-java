package org.example.GUI;

import org.example.LibraryContextPackage.*;
import org.example.Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
        LibraryGUI.main_page.setSearch_table(table);

        Searcher.search_mode = Searcher.BOOKS;
    }
}

class UserTableShower extends FrameContentManager {
    @Override
    void manage() {
        var table = ComponentDesigner.makeUserTable(new String[][]{});
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);
        LibraryGUI.main_page.setSearch_table(table);

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

    public static HashSet<LibraryContextActions> last_results;
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
        Searcher.last_results = results;
        data_length = results.size();

        String[][] data = new String[data_length][columns_number];
        int i = 0;
        for(var object : results) {
            data[i] = object.getRepresentation();
            i++;
        }
        ObjectTable table;
        if(Searcher.search_mode == Searcher.USERS)
            table = ComponentDesigner.makeUserTable(data);
        else
            table = ComponentDesigner.makeBookTable(data);
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);
        LibraryGUI.main_page.setSearch_table(table);
    }
}

class UserSelector extends FrameContentManager implements ListSelectionListener {

    public static int selected_id;
    public static int selected_index;
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();
        bottom_panel.removeAll();
        bottom_panel.add(ComponentDesigner.makeModifyPanel());
        bottom_panel.validate();

        var searched = Searcher.last_results.toArray();
        
        var selected = (CommonUser) searched[selected_index];
        UserSelector.selected_id = selected.getUserId();

        var modify_panel = (JPanel) bottom_panel.getComponent(0);

        var name_panel = (JPanel)(modify_panel.getComponent(1));
        var name_field = (JTextField) (name_panel.getComponent(1));

        var surname_panel = (JPanel)(modify_panel.getComponent(2));
        var surname_field = (JTextField) (surname_panel.getComponent(1));

        var login_panel = (JPanel)(modify_panel.getComponent(3));
        var login_field = (JTextField) (login_panel.getComponent(1));

        var password_panel = (JPanel)(modify_panel.getComponent(4));
        var password_field = (JPasswordField) (password_panel.getComponent(1));

        var mail_panel = (JPanel)(modify_panel.getComponent(5));
        var mail_field = (JTextField) (mail_panel.getComponent(1));

        name_field.setText(selected.getName());
        surname_field.setText(selected.getSurname());
        login_field.setText(selected.getLogin());
        password_field.setText(selected.getPassword());
        mail_field.setText(selected.getMail());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        var model = (ListSelectionModel) e.getSource();
        if(!model.isSelectionEmpty()) {
            selected_index = model.getMinSelectionIndex();
            this.manage();
        }
    }
}

class UserModifier extends FrameContentManager {
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();

        var modify_panel = (JPanel) bottom_panel.getComponent(0);

        var name_panel = (JPanel)(modify_panel.getComponent(1));
        var name_field = (JTextField) (name_panel.getComponent(1));

        var surname_panel = (JPanel)(modify_panel.getComponent(2));
        var surname_field = (JTextField) (surname_panel.getComponent(1));

        var login_panel = (JPanel)(modify_panel.getComponent(3));
        var login_field = (JTextField) (login_panel.getComponent(1));

        var password_panel = (JPanel)(modify_panel.getComponent(4));
        var password_field = (JPasswordField) (password_panel.getComponent(1));

        var mail_panel = (JPanel)(modify_panel.getComponent(5));
        var mail_field = (JTextField) (mail_panel.getComponent(1));

        String name, surname, login, mail, password_str;
        char[] password;
        name = name_field.getText();
        surname = surname_field.getText();
        login = login_field.getText();
        password = password_field.getPassword();
        password_str = new String(password);
        mail = mail_field.getText();

        Map<AttributesNames, String> map= new HashMap<>();
        map.put(AttributesNames.name, name);
        map.put(AttributesNames.surname, surname);
        map.put(AttributesNames.mail, mail);
        map.put(AttributesNames.login, login);
        map.put(AttributesNames.password, password_str);
        try {
            LibraryContext.modifyFewUserAttributes(map, UserSelector.selected_id);
            int row_index = UserSelector.selected_index;
            LibraryGUI.main_page.getSearch_table().getModel().setValueAt(name, row_index, ObjectTable.column_user_name);
            LibraryGUI.main_page.getSearch_table().getModel().setValueAt(surname, row_index, ObjectTable.column_user_surname);
            LibraryGUI.main_page.getSearch_table().getModel().setValueAt(login, row_index, ObjectTable.column_user_login);
            LibraryGUI.main_page.getSearch_table().getModel().setValueAt(mail, row_index, ObjectTable.column_user_mail);
        } catch (NullOrEmptyStringException e) {
            System.out.println("User data cannot be empty");
        } catch (InvalidLoginException e) {
            System.out.println("Login already exists");
        } catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect to database, check your connection");
        } catch (InvalidBookNumberException | InvalidIdException ignored) {

        }
    }
}

class MockManager extends FrameContentManager {
    @Override
    void manage() {

    }
}