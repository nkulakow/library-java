package org.example.GUI;

import lombok.Getter;
import org.example.Database.LibraryDatabase;
import org.example.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import lombok.Setter;
import lombok.Getter;

public class MainPage extends Page {
    //content in center
    private JPanel center_panel;
    private OptionPanel main_options;
    private OptionPanel search_options;
    private OptionPanel modify_options;
    private JPanel content_panel;
    private OptionPanel current_options;

    public MainPage () {
        super(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        this.init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.current_options = null;
        this.initCenter();
        this.initOptions();
        this.initSearchOptions();
        this.initModifyOptions();
        this.initBottom();
    }

    private void initCenter() {
        this.center_panel = new JPanel();
        this.center_panel.setLayout(null);
        this.add(this.center_panel, BorderLayout.CENTER);

        //content panel
        this.content_panel = new JPanel();
        this.content_panel.setBounds(
                OptionPanel.OptionButton.BUTTON_WIDTH,
                0,
                800,
                800);
        this.center_panel.add(this.content_panel);
        this.content_panel.setVisible(true);
    }

    private void initOptions() {
        String[]  option_text = {
                "Search in database",
                "Modify database",
                "Show my account",
                "Exit"
        };
        this.main_options = new OptionPanel(new Vector<>(List.of(option_text)));
        for (var option : this.main_options.getOptions()) {
            option.addActionListener(this);
        }
        this.main_options.getOptions().get(0).setActionType(OptionPanel.OptionButton.ACTION.SEARCH_IN_DATABASE);
        this.main_options.getOptions().get(1).setActionType(OptionPanel.OptionButton.ACTION.MODIFY_DATABASE);
        this.main_options.getOptions().get(2).setActionType(OptionPanel.OptionButton.ACTION.SHOW_ACCOUNT);
        this.main_options.getOptions().get(3).setActionType(OptionPanel.OptionButton.ACTION.EXIT);
        this.add(this.main_options, BorderLayout.WEST);
    }

    private void initSearchOptions() {
        String[] search_options_text = {
                "Show users",
                "Search for user",
                "Search for book",
        };
        OptionPanel srch_options = new OptionPanel(new Vector<>(List.of(search_options_text)));
        for (var option : srch_options.getOptions())
            option.addActionListener(this);
        this.search_options = srch_options;
        this.search_options.setBackground(this.getBackground());
        this.center_panel.add(this.search_options, BorderLayout.CENTER);
        int panel_height =( OptionPanel.OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * this.search_options.LENGTH - OptionPanel.MARGIN_SIZE;
        this.search_options.setBounds(0, 0, OptionPanel.OptionButton.BUTTON_WIDTH, panel_height);
        this.search_options.setVisible(false);

        this.search_options.getOptions().get(0).setActionType(OptionPanel.OptionButton.ACTION.SHOW_USERS);
        this.search_options.getOptions().get(0).setAction_manager(new UsersShower());
    }

    private void initModifyOptions() {
        String[] modify_options_text = {
                "Add user",
                "Modify user",
                "Delete user",
                "Add book",
                "Modify book",
                "Delete book",
                "Add loan",
                "Modify loan",
                "Delete loan"
        };
        OptionPanel mod_options = new OptionPanel(new Vector<>(List.of(modify_options_text)));
        for (var option : mod_options.getOptions())
            option.addActionListener(this);
        this.modify_options = mod_options;
        this.modify_options.setBackground(this.getBackground());
        this.center_panel.add(this.modify_options, BorderLayout.CENTER);
        int panel_height =( OptionPanel.OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * this.modify_options.LENGTH - OptionPanel.MARGIN_SIZE;
        this.modify_options.setBounds(0, 0, OptionPanel.OptionButton.BUTTON_WIDTH, panel_height);
        this.modify_options.setVisible(false);

        this.modify_options.getOptions().get(0).setActionType(OptionPanel.OptionButton.ACTION.ADD_USER);
    }

    private void showOptions(OptionPanel options) {
        if(this.current_options != null) {
            this.current_options.setVisible(false);
        }
        this.current_options = options;
        this.current_options.setVisible(true);
        this.current_options.setBackground(Color.ORANGE);
        this.center_panel.validate();
    }

    private void initBottom() {
        this.add(new BottomPanel(), BorderLayout.SOUTH);
    }

    private void showUsers() {
        this.content_panel.removeAll();
        var users_repr = new Vector<>(LibraryDatabase.getUsers("select * from nkulakow.pap_users"));
        var list = new JList<>(users_repr);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        this.content_panel.add(list);
        this.content_panel.setLayout(new FlowLayout());
        this.content_panel.validate();
        this.content_panel.repaint();
    }

    private void addUser() {
        this.content_panel.removeAll();
        this.content_panel.setLayout(new FlowLayout());
        this.content_panel.setLayout(null);
        this.content_panel.add(new AddingPanel(this));
        this.content_panel.validate();
        this.content_panel.repaint();
    }

    private void addUserToDatabase() {
        AddingPanel panel = (AddingPanel) this.content_panel.getComponent(0);
        var user_data = panel.getData();
        LibraryDatabase.addUser(user_data.get(0), user_data.get(1), user_data.get(2), user_data.get(3));
    }

    private void addBook() {}
    private void addLoan() {}
    private void deleteUser() {}
    private void deleteBook() {}
    private void deleteLoan() {}
    private void updateUser() {}
    private void updateBook() {}
    private void updateLoan() {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof OptionPanel.OptionButton button) {
            switch (button.getActionType()) {
                case SEARCH_IN_DATABASE -> this.showOptions(this.search_options);
                case MODIFY_DATABASE -> this.showOptions(this.modify_options);
                case EXIT -> Main.exit();
                case SHOW_USERS -> ((OptionPanel.OptionButton) e.getSource()).getAction_manager().manage(this.content_panel);
                case ADD_USER -> this.addUser();
                case ADD_BOOK -> this.addBook();
                case ADD_LOAN -> this.addLoan();
                case DELETE_USER -> this.deleteUser();
                case DELETE_BOOK -> this.deleteBook();
                case DELETE_LOAN -> this.deleteLoan();
                case UPDATE_USER -> this.updateUser();
                case UPDATE_BOOK -> this.updateBook();
                case UPDATE_LOAN -> this.updateLoan();
                case ADD_TO_DATABASE -> this.addUserToDatabase();
                default -> {
                }
            }
        }
    }
}

class OptionPanel extends JPanel {
    final private Vector<String> options_text;
    private Vector<OptionButton> options;
    public final int LENGTH;

    public static final int MARGIN_SIZE = 5;

    public OptionPanel(final Vector<String> foptions) {
        LENGTH = foptions.size();
        this.options_text = foptions;
        this.setLayout(null);
        this.setBackground(Color.ORANGE);
        this.setPreferredSize(new Dimension(250, 400));
        this.initOptions();
    }

    private void initOptions() {
        this.options = new Vector<>();
        int i = 0;
        for(String option : this.options_text) {
            OptionButton button = new OptionButton(option);
            button.setBounds(0, (OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * i, OptionButton.BUTTON_WIDTH, OptionButton.BUTTON_HEIGHT);
            this.options.add(button);
            this.add(button);
            i++;
        }
    }

    public Vector<OptionButton> getOptions() {
        return this.options;
    }

    static class OptionButton extends JButton {
        private ACTION action;
        public enum ACTION {
            MODIFY_DATABASE, ADD_TO_DATABASE,
                ADD_USER, ADD_BOOK, ADD_LOAN,
                UPDATE_USER, UPDATE_BOOK, UPDATE_LOAN,
                DELETE_USER, DELETE_BOOK, DELETE_LOAN,
            SEARCH_IN_DATABASE,
                SHOW_ACCOUNT, EXIT, SHOW_USERS
        }
        public final static int BUTTON_WIDTH = 250;
        public final static int BUTTON_HEIGHT = 50;

        @Getter @Setter
        private FrameContentManager action_manager;

        OptionButton(final String text) {
            super(text);
            this.setBackground(new Color(179, 122, 82));
            this.setForeground(new Color(60,60 ,60));
            this.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
            this.setFocusPainted(false);
            this.setHorizontalAlignment(JButton.CENTER);
            this.setBorder(new LineBorder(Color.BLACK));
        }

        public ACTION getActionType() {
            return this.action;
        }

        public void setActionType(final ACTION new_action) {
            this.action = new_action;
        }
    }
}

class AddingPanel extends JPanel {
    private final JTextField id_fild;
    private final JTextField name_fild;
    private final JTextField surname_fild;
    private final JTextField mail_fild;
    private OptionPanel.OptionButton add_button;
    public AddingPanel(ActionListener listener) {
        this.setLayout(new GridLayout(5, 1));
        this.id_fild = new JTextField("Id");
        this.add(this.id_fild);
        this.name_fild = new JTextField("Name");
        this.add(this.name_fild);
        this.surname_fild = new JTextField("Surname");
        this.add(this.surname_fild);
        this.mail_fild = new JTextField("Mail");
        this.add(this.mail_fild);
        this.add_button = new OptionPanel.OptionButton("Add");
        this.add_button.addActionListener(listener);
        this.add_button.setActionType(OptionPanel.OptionButton.ACTION.ADD_TO_DATABASE);
        this.add(this.add_button);
        this.setBounds(200, 0, 400, 150);
    }

    public ArrayList<String> getData() {
        ArrayList<String> result = new ArrayList<>();
        result.add(this.id_fild.getText());
        result.add(this.name_fild.getText());
        result.add(this.surname_fild.getText());
        result.add(this.mail_fild.getText());
        return result;
    }
}

class BottomPanel extends JPanel {
    public BottomPanel() {
        this.setBackground(new Color(94, 94, 94));
        this.setPreferredSize(new Dimension(100, 100));
    }
}