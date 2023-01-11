package org.example.GUI;

import org.example.LibraryContextPackage.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Vector;

abstract class FrameContentManager {
    public static final int USERS = 0;
    public static final int BOOKS = 1;

    public static final int ORDERED_BOOKS = 2;
    public static final int BORROWED_BOOKS = 3;
    public static final int ADMIN_USER_MOD = 4;
    public static final int USER_USER_MOD = 5;
    protected final int search_mode;

    public FrameContentManager(final int mode) {
        this.search_mode = mode;
    }
    abstract void manage(JPanel content_panel);
}

class Shower extends FrameContentManager {

    public Shower(final int mode) {super(mode);}
    @Override
    void manage(JPanel content_panel) {
        content_panel.removeAll();
        Vector<String> repr;
        if (search_mode == FrameContentManager.USERS) {
            repr = new Vector<>(LibraryContext.showUsers());
        }
        else {
            repr = new Vector<>();
            var books = LibraryContext.getOrderedBooks();
            for (var book : books){repr.add(book.describe());}
        }
        var list = new JList<>(repr);
        list.setFont(new InfoListFont());
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
        list.setFont(new InfoListFont());

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
        button.setAction_manager(new ModifyChooser(this.search_mode));
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
        list.setBounds(10, 0, content_panel.getWidth() - 20, list.getCellBounds(0, 0).height * list.getModel().getSize());
        ModifyChooser.last_results = list;

        var label = new JLabel("Select data to modify");
        label.setBounds(list.getX(), list.getHeight(), 300, 30);
        label.setBackground(LibraryGUI.GUIData.BACKGROUND_COLOR);

        var button = new OptionPanel.OptionButton("Select");
        button.addActionListener(LibraryGUI.main_page);
        if (this.search_mode == FrameContentManager.USERS)
            button.setAction_manager(new UsersModifier());
        else
            button.setAction_manager(new BooksModifier());
        button.setBounds(list.getX(), list.getHeight() + 30, 150, 30);

        content_panel.removeAll();
        content_panel.add(list);
        content_panel.add(label);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class DeleteShower extends FrameContentManager {
    public DeleteShower(final int mode) {
        super(mode);
    }

    @Override
    void manage(JPanel content_panel) {
        var search_field = new JTextField();
        search_field.setBounds(content_panel.getSize().width / 2 - 150, 0, 300, 30);

        var button = new OptionPanel.OptionButton("Search for data to delete");
        button.addActionListener(LibraryGUI.main_page);
        button.setAction_manager(new DeleteChooser(this.search_mode));
        button.setBounds(content_panel.getSize().width / 2 - 150, 30, 300, 30);

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(search_field);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class DeleteChooser extends FrameContentManager {
    public DeleteChooser(final int mode) {
        super(mode);
    }

    public static JList<String> last_results;

    @Override
    void manage(JPanel content_panel) {
        var list = Searcher.getSearchList(content_panel, this.search_mode);
        list.setBounds(10, 0, content_panel.getWidth() - 20, list.getCellBounds(0, 0).height * list.getModel().getSize());
        list.setFont(new InfoListFont());
        DeleteChooser.last_results = list;

        LibraryGUI.main_page.getPrompt().setBounds(list.getX(), list.getHeight(), 300, 30);
        LibraryGUI.main_page.changePrompt("Select data to delete");

        var button = new OptionPanel.OptionButton("Delete");
        button.addActionListener(LibraryGUI.main_page);
        if (this.search_mode == FrameContentManager.USERS)
            button.setAction_manager(new UsersDeleter());
        else
            button.setAction_manager(new BooksDeleter());
        button.setBounds(list.getX(), list.getHeight() + 30, 150, 30);

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(list);
        content_panel.add(LibraryGUI.main_page.getPrompt());
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class ReturnChooser extends FrameContentManager {
    public ReturnChooser() {
        super(0);
    }
    public static JList<String> last_results;
    public static Vector<Book> last_books;

    JList<String> getBooksToReturn() {

        var results = LibraryContext.getBorrowedBooks();
        last_books = results;
        var infos = new Vector<String>();
        for(var result : results) {
            infos.add(result.describe());
        }
        var list = new JList<>(infos);
        list.setFont(new InfoListFont());
        return list;
    }
    @Override
    void manage(JPanel content_panel) {
        var list = getBooksToReturn();
        list.setBounds(10, 0, content_panel.getWidth() - 20, list.getCellBounds(0, 0).height * list.getModel().getSize());
        list.setFont(new InfoListFont());
        ReturnChooser.last_results = list;

        LibraryGUI.main_page.getPrompt().setBounds(list.getX(), list.getHeight(), 600, 30);
        LibraryGUI.main_page.changePrompt("Select book");

        var button = new UserOptionPanel.OptionButton("Return");
        button.addActionListener(LibraryGUI.user_page);

        button.setAction_manager(new BooksReturner());
        button.setBounds(list.getX(), list.getHeight() + 30, 150, 30);

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(list);
        content_panel.add(LibraryGUI.main_page.getPrompt());
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }
}

class OrderChooser extends FrameContentManager {
    public OrderChooser() {
        super(0);
    }
    public static JList<String> last_results;
    public static JTextField months_field = new JTextField();

    @Override
    void manage(JPanel content_panel) {
        var list = Searcher.getSearchList(content_panel, Shower.BOOKS);
        list.setBounds(0, 0, content_panel.getWidth(), 30 * list.getModel().getSize());
        list.setFont(new InfoListFont());
        OrderChooser.last_results = list;

        LibraryGUI.main_page.getPrompt().setBounds(list.getX(), list.getHeight(), 300, 30);
        LibraryGUI.main_page.changePrompt("Select book to order");

        var label_months = new JLabel("Select months: ");
        label_months.setBounds(list.getX(), list.getHeight()+30, 200, 30);
        label_months.setBackground(LibraryGUI.GUIData.BACKGROUND_COLOR);

        months_field.setBounds(list.getX() + 200, list.getHeight()+30, 100, 30);

        var button = new UserOptionPanel.OptionButton("Order");
        button.addActionListener(LibraryGUI.user_page);
        button.setAction_manager(new BooksOrderer());
        button.setBounds(list.getX(), list.getHeight() + 60, 150, 30);

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(list);
        content_panel.add(LibraryGUI.main_page.getPrompt());
        content_panel.add(button);
        content_panel.add(label_months);
        content_panel.add(months_field);
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