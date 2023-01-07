package org.example.GUI;

import org.example.Database.LibraryDatabase;
import org.example.LibraryContextPackage.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Vector;

abstract class FrameContentManager {
    public static final int USERS = 0;
    public static final int BOOKS = 1;
    protected final int search_mode;

    public FrameContentManager(final int mode) {
        this.search_mode = mode;
    }
    abstract void manage(JPanel content_panel);
}

class Shower extends FrameContentManager {

    public Shower() {
        super(0);
    }
    @Override
    void manage(JPanel content_panel) {
        content_panel.removeAll();
        Vector<String> repr;
        repr = new Vector<>(LibraryDatabase.getUsers("select * from nkulakow.pap_users"));
        var list = new JList<>(repr);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        content_panel.add(list);
        content_panel.setLayout(new FlowLayout());
        content_panel.validate();
        content_panel.repaint();
    }
}

class SearchShower extends FrameContentManager {
    public SearchShower(final int mode) {
        super(mode);
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
        button.setBounds(search_field.getX(), 30, 150, 30);

        content_panel.add(search_field);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class Searcher extends FrameContentManager {
    public Searcher(final int mode) {
        super(mode);
    }
    public static HashSet<LibraryContextActions> last_results;

    public static JList<String> getSearchList(JPanel content_panel, final int mode) {
        JTextField field = (JTextField) content_panel.getComponent(0);
        String pattern = field.getText();
        HashSet<LibraryContextActions> results;
        if(mode == Searcher.USERS)
            results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<>(Admin.getUsers()));return admin.search(searchPattern);},pattern);
        else
            results = LibraryContext.searchForObject((String searchPattern, Admin admin)->{admin.setToSearch(new HashSet<>(Admin.getBooks()));return admin.search(searchPattern);},pattern);

        Searcher.last_results = results;
        var infos = new Vector<String>();
        for(var result : results) {
            infos.add(result.describe());
        }
        var list = new JList<>(infos);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        return list;
    }
    @Override
    void manage(JPanel content_panel) {
        var list = Searcher.getSearchList(content_panel, this.search_mode);
        content_panel.removeAll();
        content_panel.setLayout(new FlowLayout());
        content_panel.add(list);
        content_panel.validate();
        content_panel.repaint();
    }
}


class ModifyShower extends FrameContentManager {
    public ModifyShower(final int mode) {
        super(mode);
    }
    @Override
    public void manage(JPanel content_panel) {
        var search_field = new JTextField();
        search_field.setBounds(content_panel.getSize().width / 2 - 150, 0, 300, 30);

        var button = new OptionPanel.OptionButton("Search for data to modify");
        button.addActionListener(LibraryGUI.main_page);
        button.setAction_manager(new ModifyChooser(FrameContentManager.USERS));
        button.setBounds(content_panel.getSize().width / 2 - 150, 30, 300, 30);

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(search_field);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class ModifyChooser extends FrameContentManager {
    public ModifyChooser(final int mode) {
        super(mode);
    }

    public static JList<String> last_results;

    @Override
    void manage(JPanel content_panel) {
        var list = Searcher.getSearchList(content_panel, this.search_mode);
        list.setBounds(content_panel.getSize().width / 2 - 300, 0, 600, 30 * list.getModel().getSize());
        ModifyChooser.last_results = list;

        var label = new JLabel("Select data to modify");
        label.setBounds(list.getX(), list.getHeight(), 300, 30);

        var button = new OptionPanel.OptionButton("Select");
        button.addActionListener(LibraryGUI.main_page);
        button.setAction_manager(new UsersModifier());
        button.setBounds(list.getX(), list.getHeight() + 30, 150, 30);

        content_panel.removeAll();
        content_panel.add(list);
        content_panel.add(label);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class MockManager extends FrameContentManager {

    public MockManager() {
        super(0);
    }
    @Override
    void manage(JPanel content_panel) {

    }
}