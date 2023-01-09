package org.example.GUI;

import org.example.LibraryContextPackage.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class BookAddingShower extends FrameContentManager {

    public BookAddingShower() {
        super(FrameContentManager.BOOKS);
    }
    @Override
    void manage(JPanel content_panel) {
        content_panel.removeAll();
        content_panel.setLayout(null);
        var panel = new BookAddingPanel(LibraryGUI.main_page);
        panel.setBounds(content_panel.getWidth() / 2 - panel.getSize().width, 0, panel.getSize().width, panel.getSize().height);
        content_panel.add(panel);
        content_panel.validate();
        content_panel.repaint();
    }
}

class BookAdder extends FrameContentManager {
    public BookAdder() {
        super(FrameContentManager.BOOKS);
    }

    @Override
    void manage(JPanel content_panel) {
        BookAddingPanel panel = (BookAddingPanel) content_panel.getComponent(0);
        var book_data = panel.getData();
        String name    = book_data.get(0);
        String category = book_data.get(1);
        String author     = book_data.get(2);
        int id          = LibraryContext.generateBookID();
        try {
            boolean validInDB = LibraryContext.addObject(new Book(
                    name,
                    category,
                    id,
                    author,
                    true,
                    null,
                    null
            ));
            panel.changePrompt("" +
                    "Book successfully added");
            //jak validinDB=false tzn ze sie w db cos nie udalo i zmiany sa wprowadzone jedynie lokalnie
        } catch (NullOrEmptyStringException e) {
            panel.changePrompt("Book data cannot be empty");
        } catch (InvalidIdException | NumberFormatException e) {
            panel.changePrompt("Incorrect book id");
        }
        LibraryGUI.main_page.adjustPromptSize();
    }
}

class BooksDeleter extends FrameContentManager {
    public BooksDeleter() {
        super(FrameContentManager.BOOKS);
    }

    @Override
    void manage(JPanel content_panel) {
        int index = DeleteChooser.last_results.getSelectedIndex();
        var selected = Searcher.last_results.toArray();
        Book book;
        try {
            book = (Book) selected[index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return;
        }
        LibraryContext.removeObject(book);
        var prompt = LibraryGUI.main_page.getPrompt();
        prompt.setText("Book successfully deleted");
        LibraryGUI.main_page.adjustPromptSize();
    }
}


class BooksModifier extends FrameContentManager {
    public BooksModifier() {
        super(FrameContentManager.BOOKS);
    }

    @Override
    void manage(JPanel content_panel) {
        JList<String> list;
        var repr = new Vector<String>();

        int index = ModifyChooser.last_results.getSelectedIndex();
        var selected = Searcher.last_results.toArray();
        Book book;
        try {
            book = (Book) selected[index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return;
        }
        BookModificationApplier.last_modified_id = book.getBookId();
        repr.add(book.describe());
        list = new JList<>(repr);
        list.setBounds(content_panel.getSize().width / 2 - 300, 0, 600, 30 * list.getModel().getSize());

        var name_field      = new JTextField(book.getName());
        var category_field   = new JTextField(book.getCategory());
        var author_field     = new JTextField(book.getAuthor());

        var panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.setBounds(content_panel.getSize().width / 2 - 300, list.getHeight(), 600, 30 * 3);
        panel.add(name_field);
        panel.add(category_field);
        panel.add(author_field);

        var button = new OptionPanel.OptionButton("Confirm");
        button.setBounds(content_panel.getSize().width / 2 - 300, list.getHeight() + panel.getHeight(), 150, 30);
        button.addActionListener(LibraryGUI.main_page);
        button.setAction_manager(new BookModificationApplier());

        var prompt = LibraryGUI.main_page.getPrompt();
        prompt.setText("");
        prompt.setBounds(content_panel.getSize().width / 2 - 150, list.getHeight() + panel.getHeight() + button.getHeight(), 300, 30);
        LibraryGUI.main_page.adjustPromptSize();

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

class BookModificationApplier extends FrameContentManager {

    public static int last_modified_id;
    public BookModificationApplier() {
        super(FrameContentManager.BOOKS);
    }

    @Override
    void manage(JPanel content_panel) {
        var panel = (JPanel) content_panel.getComponent(1);
        JTextField name_f, category_f, author_f;
        String name, author, cateogry;

        name_f      = (JTextField) panel.getComponent(0);
        name        = name_f.getText();
        category_f   = (JTextField) panel.getComponent(1);
        cateogry     = category_f.getText();
        author_f      = (JTextField) panel.getComponent(2);
        author        = author_f.getText();

        Map<AttributesNames, String> map= new HashMap<>();
        map.put(AttributesNames.name, name);
        map.put(AttributesNames.category, cateogry);
        map.put(AttributesNames.author, author);

        var prompt = LibraryGUI.main_page.getPrompt();
        try {
            LibraryContext.modifyFewBookAttributes(map, BookModificationApplier.last_modified_id);
            prompt.setText("Book successfully modified");
        } catch (NullOrEmptyStringException e) {
            prompt.setText("Book data cannot be empty");
        } catch (InvalidBookNumberException | InvalidIdException ignored) {

        }
        LibraryGUI.main_page.adjustPromptSize();
    }
}

class BooksReturner extends FrameContentManager {
    public BooksReturner() {
        super(0);
    }

    @Override
    void manage(JPanel content_panel) {
        var selected = ReturnChooser.last_books.toArray();
        int index = ReturnChooser.last_results.getSelectedIndex();
        Book book;
        try {
            book = (Book) selected[index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return;
        }
        var prompt = LibraryGUI.main_page.getPrompt();
        try {
            LibraryContext.returnBook(book);
            prompt.setText(book.getName() + " by " + book.getAuthor() + " successfully returned");
        }
        catch (CannotReturnBookException e){
            prompt.setText("Could not return book, please contact administrator");
        }
        LibraryGUI.main_page.adjustPromptSize();
    }
}

class BooksOrderer extends FrameContentManager {
    public BooksOrderer() {
        super(0);
    }

    @Override
    void manage(JPanel content_panel) {
        int index = OrderChooser.last_results.getSelectedIndex();
        var prompt = LibraryGUI.main_page.getPrompt();
        var selected = Searcher.last_results.toArray();
        long months;
        try {
            months = Long.parseLong(OrderChooser.months_field.getText());
        }
        catch (java.lang.NumberFormatException e){
            prompt.setText("Input valid months number.");
            return;
        }
        Book book;
        try {
            book = (Book) selected[index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return;
        }
        LibraryContext.orderBook(book, months);
        prompt.setText(book.getName() + " by " + book.getAuthor() + " successfully ordered");
        LibraryGUI.main_page.adjustPromptSize();
    }
}