package org.example.GUI;

import org.example.Database.LibraryDatabase;
import org.example.LibraryContextPackage.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Vector;

abstract class FrameContentManager {
    abstract void manage(JPanel content_panel);
}

class UsersShower extends FrameContentManager{
    @Override
    void manage(JPanel content_panel) {
        content_panel.removeAll();
        var users_repr = new Vector<>(LibraryDatabase.getUsers("select * from nkulakow.pap_users"));
        var list = new JList<>(users_repr);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        content_panel.add(list);
        content_panel.setLayout(new FlowLayout());
        content_panel.validate();
        content_panel.repaint();
    }
}

class UserAddingShower extends FrameContentManager {
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
            } catch (InvalidIdException |  NumberFormatException e) {
                panel.changePrompt("Incorrect user id");
            } catch (InvalidBookNumberException ignored) {

            }
    }
}

class SearchShower extends FrameContentManager {
    public static final int USERS = 0;
    public static final int BOOKS = 1;

    private final int search_mode;

    public SearchShower(final int mode) {
        this.search_mode = mode;
    }
    @Override
    void manage(JPanel content_panel) {
        content_panel.removeAll();
        content_panel.setLayout(null);

        var search_field = new JTextField();
        search_field.setBounds(content_panel.getSize().width / 2 - 150, 0, 300, 30);

        var button = new OptionPanel.OptionButton("Search");
        button.addActionListener(LibraryGUI.main_page);
        button.setAction_manager(new Searcher(this.search_mode));
        button.setBounds(content_panel.getSize().width / 2 - 150, 30, 300, 30);

        content_panel.add(search_field);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class Searcher extends FrameContentManager {
    private final int search_mode;
    public Searcher(final int mode) {
        this.search_mode = mode;
    }
    @Override
    void manage(JPanel content_panel) {
        JTextField field = (JTextField) content_panel.getComponent(0);
        String pattern = field.getText();
        HashSet<LibraryContextActions> results;
        if(this.search_mode == SearchShower.USERS)
            results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<>(Admin.getUsers()));return admin.search(searchPattern);},pattern);
        else
            results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<>(Admin.getBooks()));return admin.search(searchPattern);},pattern);

        var infos = new Vector<String>();
        for(var result : results) {
            infos.add(result.describe());
        }
        var list = new JList<>(infos);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        content_panel.removeAll();
        content_panel.setLayout(new FlowLayout());
        content_panel.add(list);
        content_panel.validate();
        content_panel.repaint();
    }
}

class MockManager extends FrameContentManager {
    @Override
    void manage(JPanel content_panel) {

    }
}