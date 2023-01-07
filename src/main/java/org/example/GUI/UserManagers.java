package org.example.GUI;

import org.example.LibraryContextPackage.*;

import javax.swing.*;
import java.awt.*;
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
        content_panel.add(new AddingPanel(LibraryGUI.main_page));
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
            boolean validInDB = LibraryContext.addObject(new CommonUser(
                    login,
                    password,
                    name,
                    surname,
                    id,
                    mail,
                    0   //books number
            ));
            //jak validinDB=false tzn ze sie w db cos nie udalo i zmiany sa wprowadzone jedynie lokalnie
        } catch (NullOrEmptyStringException e) {
            panel.changePrompt("User data cannot be empty");
        } catch (InvalidIdException | NumberFormatException e) {
            panel.changePrompt("Incorrect user id");
        } catch (InvalidBookNumberException ignored) {

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
        repr.add(user.describe());
        list = new JList<>(repr);
        list.setBounds(content_panel.getSize().width / 2 - 300, 0, 600, 30 * list.getModel().getSize());

        var id_field        = new JTextField(Integer.toString(user.getUserId()));
        var name_field      = new JTextField(user.getName());
        var surname_field   = new JTextField(user.getSurname());
        var mail_field      = new JTextField(user.getMail());
        var login_field     = new JTextField(user.getLogin());
        var password_field  = new JTextField(user.getPassword());
        var panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBounds(content_panel.getSize().width / 2 - 300, list.getHeight(), 600, 30 * 3);
        panel.add(id_field);
        panel.add(name_field);
        panel.add(surname_field);
        panel.add(mail_field);
        panel.add(login_field);
        panel.add(password_field);

        var button = new OptionPanel.OptionButton("Confirm");
        button.setBounds(content_panel.getSize().width / 2 - 300, list.getHeight() + panel.getHeight(), 150, 30);
        button.setAction_manager(new UsersModificationApplier());

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(list);
        content_panel.add(panel);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class UsersModificationApplier extends FrameContentManager {
    public UsersModificationApplier() {
        super(0);
    }

    @Override
    void manage(JPanel content_panel) {

    }
}