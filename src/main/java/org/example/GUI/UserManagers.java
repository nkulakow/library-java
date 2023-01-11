package org.example.GUI;

import org.example.LibraryContextPackage.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class UserAddingShower extends FrameContentManager {

    public UserAddingShower(final int mode) {
        super(mode);
    }
    @Override
    void manage(JPanel content_panel) {
        content_panel.removeAll();
        content_panel.setLayout(null);
        var panel = new AddingPanel(LibraryGUI.main_page);
        panel.setBounds(content_panel.getWidth() / 2 - panel.getSize().width / 2, 0, panel.getSize().width, panel.getSize().height);
        content_panel.add(panel);
        LibraryGUI.main_page.getPrompt().setBounds(panel.getX(), panel.getHeight(), 0, 30);
        LibraryGUI.main_page.changePrompt("");
        content_panel.validate();
        content_panel.repaint();
    }
}

class UserAdder extends FrameContentManager {

    public UserAdder(final int mode) {
        super(mode);
    }
    @Override
    void manage(JPanel content_panel) {
        AddingPanel panel = (AddingPanel) content_panel.getComponent(0);
        var user_data = panel.getData();
        String login    = user_data.get(0);
        String password = user_data.get(1);
        String name     = user_data.get(2);
        String surname  = user_data.get(3);
        String mail     = user_data.get(4);

        int id          = LibraryContext.generateCommonUserID();

        try {
            LibraryContext.addObject(new CommonUser(
                    login,
                    password,
                    name,
                    surname,
                    id,
                    mail,
                    0   //books number
            ));
            LibraryGUI.main_page.changePrompt("User successfully added");
            //jak validinDB=false tzn ze sie w db cos nie udalo i zmiany sa wprowadzone jedynie lokalnie
        } catch (NullOrEmptyStringException e) {
            LibraryGUI.main_page.changePrompt("User data cannot be empty");
        } catch (InvalidIdException | NumberFormatException e) {
            LibraryGUI.main_page.changePrompt("Incorrect user id");
        } catch (InvalidLoginException e) {
            LibraryGUI.main_page.changePrompt("Login already exists");
        } catch (CannotConnectToDBException e) {
            LibraryGUI.main_page.changePrompt("Cannot connect to database, check your connection");
        } catch (InvalidBookNumberException ignored) {

        }
        content_panel.removeAll();
        content_panel.add(panel);
        content_panel.add(LibraryGUI.main_page.getPrompt());
        content_panel.validate();
        content_panel.repaint();
    }
}

class UsersModifier extends FrameContentManager {
    public UsersModifier() {
        super(FrameContentManager.USERS);
    }

    @Override
    void manage(JPanel content_panel) {
        int index = ModifyChooser.last_results.getSelectedIndex();
        var selected = Searcher.last_results.toArray();
        CommonUser user;
        try {
            user = (CommonUser) selected[index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return;
        }
        UsersModificationApplier.last_modified_id = user.getUserId();

        var name_field      = new JTextField(user.getName());
        var surname_field   = new JTextField(user.getSurname());
        var mail_field      = new JTextField(user.getMail());
        var login_field     = new JTextField(user.getLogin());
        var password_field  = new JTextField(user.getPassword());
        var panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBounds(content_panel.getSize().width / 2 - 300, 0, 600, 30 * 3);
        panel.setBackground(LibraryGUI.GUIData.BACKGROUND_COLOR);
        panel.add(name_field);
        panel.add(surname_field);
        panel.add(mail_field);
        panel.add(login_field);
        panel.add(password_field);

        var button = new OptionPanel.OptionButton("Confirm");
        button.setBounds(content_panel.getSize().width / 2 - 300, panel.getHeight(), 150, 30);
        button.addActionListener(LibraryGUI.main_page);
        button.setAction_manager(new UsersModificationApplier(FrameContentManager.ADMIN_USER_MOD));

        LibraryGUI.main_page.getPrompt().setBounds(button.getX(), panel.getHeight() + button.getHeight(), 300, 30);
        LibraryGUI.main_page.changePrompt("");

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(panel);
        content_panel.add(button);
        content_panel.add(LibraryGUI.main_page.getPrompt());
        content_panel.validate();
        content_panel.repaint();
    }
}

class UsersModificationApplier extends FrameContentManager {
    public UsersModificationApplier(final int mode) {
        super(mode);
    }

    public static int last_modified_id;

    public static Map<AttributesNames, String> getDataMap(JPanel panel) {
        JTextField name_f, surname_f, mail_f, login_f, password_f;
        String name, surname, mail, login, password;

        name_f      = (JTextField) panel.getComponent(0);
        name        = name_f.getText();
        surname_f   = (JTextField) panel.getComponent(1);
        surname     = surname_f.getText();
        mail_f      = (JTextField) panel.getComponent(2);
        mail        = mail_f.getText();
        login_f     = (JTextField) panel.getComponent(3);
        login       = login_f.getText();
        password_f  = (JTextField) panel.getComponent(4);
        password    = password_f.getText();

        Map<AttributesNames, String> map= new HashMap<>();
        map.put(AttributesNames.name, name);
        map.put(AttributesNames.surname, surname);
        map.put(AttributesNames.mail, mail);
        map.put(AttributesNames.login, login);
        map.put(AttributesNames.password, password);

        return map;
    }

    @Override
    void manage(JPanel content_panel) {
        JPanel panel;
        if (this.search_mode == FrameContentManager.ADMIN_USER_MOD) {
            panel = (JPanel) content_panel.getComponent(1);
        }
        else {
            panel = (JPanel) content_panel.getComponent(0);
        }
        var map = UsersModificationApplier.getDataMap(panel);

        try {
            LibraryContext.modifyFewUserAttributes(map, UsersModificationApplier.last_modified_id);
            LibraryGUI.main_page.changePrompt("User successfully modified");
        } catch (NullOrEmptyStringException e) {
            LibraryGUI.main_page.changePrompt("User data cannot be empty");
        } catch (InvalidLoginException e) {
            LibraryGUI.main_page.changePrompt("Login already exists");
        } catch (CannotConnectToDBException e) {
            LibraryGUI.main_page.changePrompt("Cannot connect to database, check your connection");
        } catch (InvalidBookNumberException | InvalidIdException ignored) {

        }
    }
}

class UsersDeleter extends FrameContentManager {
    public UsersDeleter() {
        super(FrameContentManager.USERS);
    }

    @Override
    void manage(JPanel content_panel) {
        int index = DeleteChooser.last_results.getSelectedIndex();
        var selected = Searcher.last_results.toArray();
        CommonUser user;
        try {
            user = (CommonUser) selected[index];
            LibraryContext.removeObject(user);
            LibraryGUI.main_page.changePrompt("User successfully deleted");
        } catch (CannotConnectToDBException e) {
            LibraryGUI.main_page.changePrompt("Cannot connect to database, check your connection");
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }
}

class AdminModificationApplier extends FrameContentManager {
    public AdminModificationApplier() {
        super(FrameContentManager.USERS);
    }

    @Override
    void manage(JPanel content_panel) {
        var map = UsersModificationApplier.getDataMap(content_panel);
        try {
            LibraryContext.modifyFewAdminAttributes(map);
            LibraryGUI.main_page.changePrompt("Successfully changed your data");
        } catch (NullOrEmptyStringException e) {
            LibraryGUI.main_page.changePrompt("Admin data cannot be empty");
        } catch (InvalidLoginException e) {
            LibraryGUI.main_page.changePrompt("Incorrect login");
        } catch (InvalidIdException | InvalidBookNumberException ignored) {

        } catch (CannotConnectToDBException e) {
            LibraryGUI.main_page.changePrompt("Cannot connect to database, check your connection");
        }
    }
}