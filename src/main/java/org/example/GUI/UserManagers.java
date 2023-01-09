package org.example.GUI;

import org.example.LibraryContextPackage.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class UserAddingShower extends FrameContentManager {

    public UserAddingShower(final int mode) {
        super(mode);
    }
    @Override
    void manage(JPanel content_panel) {
        content_panel.removeAll();
        content_panel.setLayout(null);
        var panel = new AddingPanel(LibraryGUI.main_page);
        panel.setBounds(content_panel.getWidth() / 2 - panel.getSize().width, 0, panel.getSize().width, panel.getSize().height);
        content_panel.add(panel);
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
        int id          = Integer.parseInt(user_data.get(4));
        String mail     = user_data.get(5);
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
        } catch (NullOrEmptyStringException e) {
            LibraryGUI.main_page.getPrompt().setText("User data cannot be empty");
        } catch (InvalidIdException | NumberFormatException e) {
            LibraryGUI.main_page.getPrompt().setText("Incorrect user id");
        } catch (InvalidBookNumberException ignored) {

        } catch (InvalidLoginException e) {
            LibraryGUI.main_page.getPrompt().setText("Login already exists");
        } catch (CannotConnectToDBException e) {
            //
        }
    }
}

class UsersModifier extends FrameContentManager {
    public UsersModifier() {
        super(FrameContentManager.USERS);
    }

    @Override
    void manage(JPanel content_panel) {
        JList<String> list;
        var repr = new Vector<String>();

        int index = ModifyChooser.last_results.getSelectedIndex();
        var selected = Searcher.last_results.toArray();
        CommonUser user;
        try {
            user = (CommonUser) selected[index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return;
        }
        UsersModificationApplier.last_modified_id = user.getUserId();
        repr.add(user.describe());
        list = new JList<>(repr);
        list.setBounds(content_panel.getSize().width / 2 - 300, 0, 600, 30 * list.getModel().getSize());

        var name_field      = new JTextField(user.getName());
        var surname_field   = new JTextField(user.getSurname());
        var mail_field      = new JTextField(user.getMail());
        var login_field     = new JTextField(user.getLogin());
        var password_field  = new JTextField(user.getPassword());
        var panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBounds(content_panel.getSize().width / 2 - 300, list.getHeight(), 600, 30 * 3);
        panel.add(name_field);
        panel.add(surname_field);
        panel.add(mail_field);
        panel.add(login_field);
        panel.add(password_field);

        var button = new OptionPanel.OptionButton("Confirm");
        button.setBounds(content_panel.getSize().width / 2 - 300, list.getHeight() + panel.getHeight(), 150, 30);
        button.addActionListener(LibraryGUI.main_page);
        button.setAction_manager(new UsersModificationApplier(FrameContentManager.ADMIN_USER_MOD));

        var prompt = new JLabel();
        prompt.setBounds(content_panel.getSize().width / 2 - 150, list.getHeight() + panel.getHeight() + button.getHeight(), 300, 30);

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(list);
        content_panel.add(panel);
        content_panel.add(button);
        content_panel.add(prompt);
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
        else{
            panel = (JPanel) content_panel.getComponent(0);
        }
        var map = UsersModificationApplier.getDataMap(panel);
        try {
            LibraryContext.modifyFewUserAttributes(map, UsersModificationApplier.last_modified_id);
        } catch (NullOrEmptyStringException e) {
            JLabel prompt;
            if (this.search_mode == FrameContentManager.ADMIN_USER_MOD){
            prompt = (JLabel) content_panel.getComponent(3);}
            else {prompt = (JLabel) content_panel.getComponent(2);}
            prompt.setText("User data cannot be empty");
            content_panel.add(prompt);
            }
        catch (InvalidLoginException e) {
            JLabel prompt;
            if (this.search_mode == FrameContentManager.ADMIN_USER_MOD){
                prompt = (JLabel) content_panel.getComponent(3);}
            else {prompt = (JLabel) content_panel.getComponent(2);}
            prompt.setText("Login already exists");
            content_panel.add(prompt);
        }
         catch (InvalidBookNumberException | InvalidIdException ignored) {

        } catch (CannotConnectToDBException e) {
            //
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
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return;
        }
        System.out.println(user.describe());
        try {
            LibraryContext.removeObject(user);
        } catch (CannotConnectToDBException e) {
            //
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
        JLabel prompt = (JLabel) content_panel.getComponent(6);
        try {
            LibraryContext.modifyFewAdminAttributes(map);
            prompt.setText("Successfully changed your data");
        } catch (NullOrEmptyStringException e) {
            prompt.setText("Admin data cannot be empty");
        } catch (InvalidLoginException e) {
            prompt.setText("Incorrect login");
        } catch (InvalidIdException | InvalidBookNumberException ignored) {

        } catch (CannotConnectToDBException e) {
            //
        }
    }
}