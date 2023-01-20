package org.example.GUI;

import org.example.LibraryContextPackage.*;
import org.example.Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

abstract class FrameContentManager {
    public static final int USERS = 0;
    public static final int BOOKS = 1;
    public static final int BOOKS_ORDERING = 2;

    public static final int BOOKS_ORDERED = 3;
    public static final int BOOKS_BORROWED = 4;


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

    public static String[] getUserData() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();

        var data_panel = (JPanel) bottom_panel.getComponent(0);

        var name_panel = (JPanel)(data_panel.getComponent(1));
        var name_field = (JTextField) (name_panel.getComponent(1));

        var surname_panel = (JPanel)(data_panel.getComponent(2));
        var surname_field = (JTextField) (surname_panel.getComponent(1));

        var login_panel = (JPanel)(data_panel.getComponent(3));
        var login_field = (JTextField) (login_panel.getComponent(1));

        var password_panel = (JPanel)(data_panel.getComponent(4));
        var password_field = (JPasswordField) (password_panel.getComponent(1));

        var mail_panel = (JPanel)(data_panel.getComponent(5));
        var mail_field = (JTextField) (mail_panel.getComponent(1));

        String name, surname, login, mail, password_str;
        char[] password;
        name = name_field.getText();
        surname = surname_field.getText();
        login = login_field.getText();
        password = password_field.getPassword();
        password_str = new String(password);
        mail = mail_field.getText();

        return new String[]{name, surname, login, mail, password_str};
    }

    public static String[] getBookData() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();

        var data_panel = (JPanel) bottom_panel.getComponent(0);

        var name_panel = (JPanel)(data_panel.getComponent(1));
        var name_field = (JTextField) (name_panel.getComponent(1));

        var author_panel = (JPanel)(data_panel.getComponent(2));
        var author_field = (JTextField) (author_panel.getComponent(1));

        var category_panel = (JPanel)(data_panel.getComponent(3));
        var category_field = (JTextField) (category_panel.getComponent(1));

        String name, author, category;
        name = name_field.getText();
        author = author_field.getText();
        category = category_field.getText();

        return new String[]{name, author, category};
    }

    public static void clearBottomPanel(int mode) {
        var panel = FrameContentManager.getBottomFramePanel();
        panel.removeAll();
        if(mode == FrameContentManager.USERS)
            panel.add(ComponentDesigner.makeUserAddingPanel());
        else if (mode == FrameContentManager.BOOKS)
            panel.add(ComponentDesigner.makeBookAddingPanel());
        else if (mode == FrameContentManager.BOOKS_BORROWED)
            panel.add(ComponentDesigner.makeBookReturnPanel());
        panel.validate();
        panel.repaint();
    }

    public static String[] getSelfData() {
        var data_panel = LibraryGUI.main_page.getAccount_panel();

        var name_panel = (JPanel)(data_panel.getComponent(1));
        var name_field = (JTextField) (name_panel.getComponent(1));

        var surname_panel = (JPanel)(data_panel.getComponent(2));
        var surname_field = (JTextField) (surname_panel.getComponent(1));

        var login_panel = (JPanel)(data_panel.getComponent(3));
        var login_field = (JTextField) (login_panel.getComponent(1));

        var password_panel = (JPanel)(data_panel.getComponent(4));
        var password_field = (JPasswordField) (password_panel.getComponent(1));

        var mail_panel = (JPanel)(data_panel.getComponent(5));
        var mail_field = (JTextField) (mail_panel.getComponent(1));

        String name, surname, login, mail, password_str;
        char[] password;
        name = name_field.getText();
        surname = surname_field.getText();
        login = login_field.getText();
        password = password_field.getPassword();
        password_str = new String(password);
        mail = mail_field.getText();

        return new String[]{name, surname, login, mail, password_str};
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
        var table = ComponentDesigner.makeBookTable(new String[][]{}, FrameContentManager.BOOKS);
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);
        LibraryGUI.main_page.setSearch_table(table);
        LibraryGUI.main_page.current_mode = FrameContentManager.BOOKS;

        Searcher.search_mode = Searcher.BOOKS;
        Searcher.last_results = null;

        FrameContentManager.clearBottomPanel(FrameContentManager.BOOKS);
    }
}

class UserTableShower extends FrameContentManager {
    @Override
    void manage() {
        var table = ComponentDesigner.makeUserTable(new String[][]{});
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);
        LibraryGUI.main_page.setSearch_table(table);
        LibraryGUI.main_page.current_mode = FrameContentManager.USERS;

        Searcher.search_mode = Searcher.USERS;
        Searcher.last_results = null;

        FrameContentManager.clearBottomPanel(FrameContentManager.USERS);
    }
}

class ModifyPanelShower extends FrameContentManager {
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();
        bottom_panel.removeAll();
        bottom_panel.add(ComponentDesigner.makeUserModifyPanel());
        bottom_panel.validate();
    }
}

class AddingPanelShower extends FrameContentManager {
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();
        bottom_panel.removeAll();
        if(LibraryGUI.main_page.current_mode == FrameContentManager.USERS)
            bottom_panel.add(ComponentDesigner.makeUserAddingPanel());
        else
            bottom_panel.add(ComponentDesigner.makeBookAddingPanel());
        bottom_panel.validate();
    }
}

class Searcher extends FrameContentManager {
    public static final int USERS = 0;
    public static final int BOOKS = 1;
    public static final int BOOKS_ORDER = 2;
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
        else if (Searcher.search_mode == Searcher.BOOKS)
            table = ComponentDesigner.makeBookTable(data, FrameContentManager.BOOKS);
        else
            table = ComponentDesigner.makeBookTable(data, FrameContentManager.BOOKS_ORDERING);
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
        bottom_panel.add(ComponentDesigner.makeUserModifyPanel());
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
            try {
                selected_index = model.getMinSelectionIndex();
                this.manage();
            } catch (NullPointerException exception) {
                System.out.println("Choose non empty table position");
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
    }
}

class UserModifier extends FrameContentManager {
    @Override
    void manage() {
        String name, surname, login, mail, password;
        String[] data = FrameContentManager.getUserData();

        name = data[0];
        surname = data[1];
        login = data[2];
        mail = data[3];
        password = data[4];

        Map<AttributesNames, String> map= new HashMap<>();
        map.put(AttributesNames.name, name);
        map.put(AttributesNames.surname, surname);
        map.put(AttributesNames.mail, mail);
        map.put(AttributesNames.login, login);
        map.put(AttributesNames.password, password);
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

class UserDeleter extends FrameContentManager {
    @Override
    void manage() {
        int index = UserSelector.selected_index;
        var selected = Searcher.last_results.toArray();
        CommonUser user;
        try {
            user = (CommonUser) selected[index];
            LibraryContext.removeObject(user);
            ((DefaultTableModel) LibraryGUI.main_page.getSearch_table().getModel()).removeRow(index);

            System.out.println("User successfully deleted");
        } catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect to database, check your connection");
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }
}

class UserAdder extends FrameContentManager {
    @Override
    void manage() {
        String name, surname, login, mail, password;
        String[] data = FrameContentManager.getUserData();

        name = data[0];
        surname = data[1];
        login = data[2];
        mail = data[3];
        password = data[4];

        int id = LibraryContext.generateCommonUserID();

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
            System.out.println("User successfully added");
        } catch (NullOrEmptyStringException e) {
            System.out.println("User data cannot be empty");
        } catch (InvalidIdException | NumberFormatException e) {
            System.out.println("Incorrect user id");
        } catch (InvalidLoginException e) {
            System.out.println("Login already exists");
        } catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect to database, check your connection");
        } catch (InvalidBookNumberException ignored) {

        }
    }
}

class SelfModifier extends FrameContentManager {
    @Override
    void manage() {
        String name, surname, login, mail, password;
        String[] data = FrameContentManager.getSelfData();

        name = data[0];
        surname = data[1];
        login = data[2];
        mail = data[3];
        password = data[4];

        Map<AttributesNames, String> map= new HashMap<>();
        map.put(AttributesNames.name, name);
        map.put(AttributesNames.surname, surname);
        map.put(AttributesNames.mail, mail);
        map.put(AttributesNames.login, login);
        map.put(AttributesNames.password, password);
        try {
            if (LibraryContext.getCurrentUser() != null)
                LibraryContext.modifyFewUserAttributes(map, LibraryContext.getCurrentUser().getUserId());
            else
                LibraryContext.modifyFewUserAttributes(map, LibraryContext.getCurrentAdmin().getAdminId());
            System.out.println("Your data successfully modified");
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

class BookSelector extends FrameContentManager implements ListSelectionListener {

    public static int selected_id;
    public static int selected_index;
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();
        bottom_panel.removeAll();
        bottom_panel.add(ComponentDesigner.makeBookModifyPanel());
        bottom_panel.validate();

        var searched = Searcher.last_results.toArray();

        var selected = (Book) searched[selected_index];
        BookSelector.selected_id = selected.getBookId();

        var modify_panel = (JPanel) bottom_panel.getComponent(0);

        var name_panel = (JPanel)(modify_panel.getComponent(1));
        var name_field = (JTextField) (name_panel.getComponent(1));

        var author_panel = (JPanel)(modify_panel.getComponent(2));
        var author_field = (JTextField) (author_panel.getComponent(1));

        var category_panel = (JPanel)(modify_panel.getComponent(3));
        var category_field = (JTextField) (category_panel.getComponent(1));

        name_field.setText(selected.getName());
        author_field.setText(selected.getAuthor());
        category_field.setText(selected.getCategory());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        var model = (ListSelectionModel) e.getSource();
        if(!model.isSelectionEmpty()) {
            try {
                selected_index = model.getMinSelectionIndex();
                this.manage();
            } catch (NullPointerException exception) {
                System.out.println("Choose non empty table position");
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
    }
}

class BookModifier extends FrameContentManager {
    @Override
    void manage() {
        String name, author, category;
        String[] data = FrameContentManager.getBookData();

        name = data[0];
        author = data[1];
        category = data[2];

        Map<AttributesNames, String> map= new HashMap<>();
        map.put(AttributesNames.name, name);
        map.put(AttributesNames.author, author);
        map.put(AttributesNames.category, category);
        try {
            LibraryContext.modifyFewBookAttributes(map, BookSelector.selected_id);
            int row_index = BookSelector.selected_index;
            LibraryGUI.main_page.getSearch_table().getModel().setValueAt(name, row_index, ObjectTable.column_book_name);
            LibraryGUI.main_page.getSearch_table().getModel().setValueAt(author, row_index, ObjectTable.column_book_author_);
            LibraryGUI.main_page.getSearch_table().getModel().setValueAt(category, row_index, ObjectTable.column_book_category);
        } catch (NullOrEmptyStringException e) {
            System.out.println("Book data cannot be empty");
        }  catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect to database, check your connection");
        } catch (InvalidBookNumberException | InvalidIdException ignored) {

        }
    }
}

class BookOrderSelector extends FrameContentManager implements ListSelectionListener {

    public static int selected_id;
    public static int selected_index;
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();
        bottom_panel.removeAll();
        bottom_panel.add(ComponentDesigner.makeOrderPanel());
        bottom_panel.validate();

        var searched = Searcher.last_results.toArray();

        var selected = (Book) searched[selected_index];
        BookOrderSelector.selected_id = selected.getBookId();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        var model = (ListSelectionModel) e.getSource();
        if(!model.isSelectionEmpty()) {
            try {
                selected_index = model.getMinSelectionIndex();
                this.manage();
            } catch (NullPointerException exception) {
                System.out.println("Choose non empty table position");
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
    }
}

class BookOrderer extends FrameContentManager {
    @Override
    void manage() {
        var bottom_panel = FrameContentManager.getBottomFramePanel();

        var order_panel = (JPanel) bottom_panel.getComponent(0);
        var months_panel = (JPanel) order_panel.getComponent(1);
        var months_field = (JTextField) months_panel.getComponent(1);

        int index = BookOrderSelector.selected_index;
        var selected = Searcher.last_results.toArray();
        long months;
        try {
            months = Long.parseLong(months_field.getText());
        }
        catch (java.lang.NumberFormatException e){
            System.out.println("Input valid months number.");
            return;
        }
        Book book;
        try {
            book = (Book) selected[index];
            LibraryContext.orderBook(book, months);
            System.out.println(book.getName() + " by " + book.getAuthor() + " successfully ordered");
        } catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect to database, check your connection");
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }
}

class BookAdder extends FrameContentManager {
    @Override
    void manage() {
        String name, author, category;
        String[] data = FrameContentManager.getBookData();

        name = data[0];
        author = data[1];
        category = data[2];

        int id = LibraryContext.generateBookID();

        try {
            LibraryContext.addObject(new Book(
                    name,
                    category,
                    id,
                    author,
                    true,
                    null,
                    null   //books number
            ));
            System.out.println("Book successfully added");
        } catch (NullOrEmptyStringException e) {
            System.out.println("Book data cannot be empty");
        } catch (InvalidIdException | NumberFormatException e) {
            System.out.println("Incorrect book id");
        } catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect to database, check your connection");
        }
    }
}

class BookDeleter extends FrameContentManager {
    @Override
    void manage() {
        int index = BookSelector.selected_index;
        var selected = Searcher.last_results.toArray();
        Book book;
        try {
            book = (Book) selected[index];
            LibraryContext.removeObject(book);
            ((DefaultTableModel) LibraryGUI.main_page.getSearch_table().getModel()).removeRow(index);

            System.out.println("Book successfully deleted");
        } catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect to database, check your connection");
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }
}

class Deleter extends FrameContentManager {
    private static final UserDeleter user_deleter = new UserDeleter();
    private static final BookDeleter book_deleter = new BookDeleter();
    @Override
    void manage() {
        if(LibraryGUI.main_page.current_mode == FrameContentManager.USERS)
            Deleter.user_deleter.manage();
        else
            Deleter.book_deleter.manage();
    }
}

class AccountPanelSwitcher extends FrameContentManager {
    @Override
    void manage() {
        int min_height = 1000;
        if(LibraryGUI.main_page.getAccount_panel().isVisible()) {
            LibraryGUI.main_page.getAccount_panel().setVisible(false);
            LibraryGUI.main_page.setMinimumSize(MainPage.default_minimum_size);
        }
        else {
            LibraryGUI.main_page.getAccount_panel().setVisible(true);
            if(LibraryGUI.main_page.getHeight() < min_height) {
                LibraryGUI.main_page.setSize(new Dimension(LibraryGUI.main_page.getWidth(), min_height));
            }
            LibraryGUI.main_page.setMinimumSize(new Dimension(LibraryGUI.main_page.getMinimumSize().width, min_height));
        }
    }
}

class BorrowedBooksShower extends FrameContentManager {
    @Override
    void manage() {
        var table = ComponentDesigner.makeBorrowedBookTable(LibraryContext.getBorrowedBooksRepresentation(), FrameContentManager.BOOKS_BORROWED);
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);
        LibraryGUI.main_page.setSearch_table(table);
        LibraryGUI.main_page.current_mode = FrameContentManager.BOOKS_BORROWED;

        Searcher.search_mode = Searcher.BOOKS_BORROWED;
        Searcher.last_results = null;

        FrameContentManager.clearBottomPanel(FrameContentManager.BOOKS_BORROWED);
    }
}

class BooksReturner extends FrameContentManager {
    @Override
    void manage() {
        var books = LibraryContext.getBorrowedBooks().toArray();
        int index = LibraryGUI.main_page.getSearch_table().getSelectedRow();
        var to_return = (Book) books[index];
        try {
            LibraryContext.returnBook(to_return);
            ((DefaultTableModel) LibraryGUI.main_page.getSearch_table().getModel()).removeRow(index);
            System.out.println("Book returned " + to_return.describe());
        } catch (CannotReturnBookException e) {
            System.out.println("Could not return");
        } catch (CannotConnectToDBException e) {
            System.out.println("Cannot connect");
        }
    }
}

class OrderedBooksShower extends FrameContentManager {
    @Override
    void manage() {
        var table = ComponentDesigner.makeOrderedBookTable(LibraryContext.getOrderedBooksRepresentation(), FrameContentManager.BOOKS_BORROWED);
        var pane = LibraryGUI.main_page.getTable_pane();
        pane.setViewportView(table);
        LibraryGUI.main_page.setSearch_table(table);
        LibraryGUI.main_page.current_mode = FrameContentManager.BOOKS_ORDERED;

        Searcher.search_mode = Searcher.BOOKS_ORDERED;
        Searcher.last_results = null;

        FrameContentManager.clearBottomPanel(FrameContentManager.BOOKS_ORDERED);
    }
}

class MockManager extends FrameContentManager {
    @Override
    void manage() {

    }
}