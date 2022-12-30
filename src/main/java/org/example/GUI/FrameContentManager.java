package org.example.GUI;

import org.example.Database.LibraryDatabase;

import javax.swing.*;
import java.awt.*;
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
        LibraryDatabase.addUser(user_data.get(0), user_data.get(1), user_data.get(2), user_data.get(3));
    }
}

class MockManager extends FrameContentManager {
    @Override
    void manage(JPanel content_panel) {

    }
}