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

public class MainPage extends Page {
    private JPanel cards;
    //content in center
    private JPanel center_panel;
    private OptionPanel search_options;
    private OptionPanel modify_options;
    private JPanel content_panel;
    private JPanel current_options;

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
        this.initcards();
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

    private void initcards(){
        this.cards = new JPanel(new CardLayout());
        this.initcard1();
        this.initcard2();
        this.initcard3();
        this.initcard4();

        //this.add(cards, BorderLayout.CENTER);
        cardChanged("MAIN");
    }
    private void initcard1(){
        JPanel card1 = new JPanel(new BorderLayout());
        card1.setPreferredSize(new Dimension(300, 600));
        JPanel top_panel = new JPanel();
        JTextField searchtext = new JTextField();
        searchtext.setPreferredSize(new Dimension(250, 30));
        JButton searchbutton = new JButton("SEARCH");
        top_panel.add(searchbutton);
        top_panel.add(searchtext);
        card1.add(top_panel, BorderLayout.NORTH);
        this.cards.add(card1,"SEARCH");
    }

    private void initcard2(){
        JPanel card2 = new JPanel(new BorderLayout());
        JButton add = new JButton("ADD");
        JButton delete = new JButton("DELETE");
        JButton modify = new JButton("MODIFY");
        JPanel top_panel = new JPanel();
        top_panel.add(add);
        top_panel.add(delete);
        top_panel.add(modify);
        card2.add(top_panel, BorderLayout.WEST);
        this.cards.add(card2,"MODIFY");
    }

    private void initcard3(){
        JPanel card3 = new JPanel(new BorderLayout());
        JPanel top_panel = new JPanel();
        card3.setPreferredSize(new Dimension(300, 600));
        JLabel label= new JLabel("YOUR ACCOUNT");
        label.setFont(new Font("Verdana", Font.BOLD,20));
        top_panel.add(label);
        card3.add(top_panel, BorderLayout.NORTH);
        this.cards.add(card3,"ACCOUNT");
    }

    private void initcard4(){
        JPanel card4 = new JPanel();
        JLabel label = new JLabel("LIBRARY DATABASE");
        label.setFont(new Font("Verdana", Font.BOLD,20));
        card4.add(label);
        this.cards.add(card4, "MAIN");
    }

    public void cardChanged(String name){
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, name);
    }

    private void initOptions() {
        String[]  option_text = {
                "Search in database",
                "Modify database",
                "Show my account",
                "Exit"
        };
        OptionPanel options = new OptionPanel(new Vector<>(List.of(option_text)));
        for (var option : options.getOptions()) {
            option.addActionListener(this);
        }
        options.getOptions().get(0).setActionType(OptionPanel.OptionButton.SEARCH_IN_DATABASE);
        options.getOptions().get(1).setActionType(OptionPanel.OptionButton.MODIFY_DATABASE);
        options.getOptions().get(2).setActionType(OptionPanel.OptionButton.SHOW_ACCOUNT);
        options.getOptions().get(3).setActionType(OptionPanel.OptionButton.EXIT);
        this.add(options, BorderLayout.WEST);
    }

    private void initSearchOptions() {
        String[] search_options_text = {
                "Show users"
        };
        OptionPanel srch_options = new OptionPanel(new Vector<>(List.of(search_options_text)));
        for (var option : srch_options.getOptions())
            option.addActionListener(this);
        this.search_options = srch_options;
        this.search_options.setBackground(this.getBackground());
        this.center_panel.add(this.search_options, BorderLayout.CENTER);
        this.search_options.setBounds(0, 0, OptionPanel.OptionButton.BUTTON_WIDTH, OptionPanel.OptionButton.BUTTON_HEIGHT);
        this.search_options.setVisible(false);

        this.search_options.getOptions().get(0).setActionType(OptionPanel.OptionButton.SHOW_USERS);
    }

    private void initModifyOptions() {
        String[] modify_options_text = {
                "Add user"
        };
        OptionPanel mod_options = new OptionPanel(new Vector<>(List.of(modify_options_text)));
        for (var option : mod_options.getOptions())
            option.addActionListener(this);
        this.modify_options = mod_options;
        this.modify_options.setBackground(this.getBackground());
        this.center_panel.add(this.modify_options, BorderLayout.CENTER);
        this.modify_options.setBounds(0, 0, OptionPanel.OptionButton.BUTTON_WIDTH, OptionPanel.OptionButton.BUTTON_HEIGHT);
        this.modify_options.setVisible(false);

        this.modify_options.getOptions().get(0).setActionType(OptionPanel.OptionButton.ADD_USER);
    }

    private void showOptions(OptionPanel options) {
        if(this.current_options != null) {
            this.current_options.setVisible(false);
        }
        this.current_options = options;
        this.current_options.setVisible(true);
        this.center_panel.validate();
    }

    private void initBottom() {
        this.add(new BottomPanel(), BorderLayout.SOUTH);
    }

    private void showUsers() {
        this.content_panel.removeAll();
        var users_repr = new Vector<>(LibraryDatabase.getUsers("select * from users"));
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
        LibraryDatabase.addUser(user_data.get(0), user_data.get(1), user_data.get(2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof OptionPanel.OptionButton button) {
            switch (button.getActionType()) {
                case OptionPanel.OptionButton.SEARCH_IN_DATABASE -> this.showOptions(this.search_options);
                case OptionPanel.OptionButton.MODIFY_DATABASE -> this.showOptions(this.modify_options);
                case OptionPanel.OptionButton.SHOW_ACCOUNT -> cardChanged("ACCOUNT");
                case OptionPanel.OptionButton.EXIT -> Main.exit();
                case OptionPanel.OptionButton.SHOW_USERS -> this.showUsers();
                case OptionPanel.OptionButton.ADD_USER -> this.addUser();
                case OptionPanel.OptionButton.ADD_TO_DATABASE -> this.addUserToDatabase();
                default -> {
                }
            }
        }
    }
}

class OptionPanel extends JPanel {
    final private Vector<String> options_text;
    private Vector<OptionButton> options;

    public OptionPanel(final Vector<String> foptions) {
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
            button.setBounds(0, 55 * i, OptionButton.BUTTON_WIDTH, OptionButton.BUTTON_HEIGHT);
            this.options.add(button);
            this.add(button);
            i++;
        }
    }

    public Vector<OptionButton> getOptions() {
        return this.options;
    }

    static class OptionButton extends JButton {
        private int action;

        public final static int MODIFY_DATABASE = 0;
        public final static int SEARCH_IN_DATABASE = 1;
        public final static int SHOW_ACCOUNT = 2;
        public final static int EXIT = 3;
        public final static int SHOW_USERS = 4;
        public final static int ADD_USER = 5;
        public final static int ADD_TO_DATABASE = 6;


        public final static int BUTTON_WIDTH = 250;
        public final static int BUTTON_HEIGHT = 50;

        OptionButton(final String text) {
            super(text);
            this.setBackground(new Color(179, 122, 82));
            this.setForeground(new Color(60,60 ,60));
            this.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
            this.setFocusPainted(false);
            this.setHorizontalAlignment(JButton.CENTER);
            this.setBorder(new LineBorder(Color.BLACK));
        }

        public int getActionType() {
            return this.action;
        }

        public void setActionType(final int new_action) {
            this.action = new_action;
        }
    }
}

class AddingPanel extends JPanel {
    private JTextField id_fild;
    private JTextField name_fild;
    private JTextField surname_fild;
    private OptionPanel.OptionButton add_button;
    public AddingPanel(ActionListener listener) {
        this.setLayout(new GridLayout(4, 1));
        this.id_fild = new JTextField("Id");
        this.add(this.id_fild);
        this.name_fild = new JTextField("Name");
        this.add(this.name_fild);
        this.surname_fild = new JTextField("Surname");
        this.add(this.surname_fild);
        this.add_button = new OptionPanel.OptionButton("Add");
        this.add_button.addActionListener(listener);
        this.add_button.setActionType(OptionPanel.OptionButton.ADD_TO_DATABASE);
        this.add(this.add_button);
        this.setBounds(200, 0, 400, 120);
    }

    public ArrayList<String> getData() {
        ArrayList<String> result = new ArrayList<>();
        result.add(id_fild.getText());
        result.add(name_fild.getText());
        result.add(surname_fild.getText());
        return result;
    }
}

class BottomPanel extends JPanel {
    public BottomPanel() {
        this.setBackground(new Color(94, 94, 94));
        this.setPreferredSize(new Dimension(100, 100));
    }
}