package org.example.GUI;

import org.example.LibraryContextPackage.*;

import javax.swing.*;

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
        int id          = Integer.parseInt(book_data.get(3));
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
            //jak validinDB=false tzn ze sie w db cos nie udalo i zmiany sa wprowadzone jedynie lokalnie
        } catch (NullOrEmptyStringException e) {
            panel.changePrompt("Book data cannot be empty");
        } catch (InvalidIdException | NumberFormatException e) {
            panel.changePrompt("Incorrect book id");
        }
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
        System.out.println(book.describe());
        LibraryContext.removeObject(book);
    }
}
