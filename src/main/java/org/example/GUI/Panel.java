//package org.example.GUI;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import java.awt.*;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.Vector;
//
//class OptionPanel extends JPanel {
//    public static final int MAIN = 0;
//    public static final int NOT_MAIN = 1;
//    final private Vector<String> options_text;
//    private Vector<JButton> options;
//    public final int LENGTH;
//
//    public static final int MARGIN_SIZE = 5;
//
//    public OptionPanel(final Vector<String> foptions, final int option_type) {
//        LENGTH = foptions.size();
//        this.options_text = foptions;
//        this.setLayout(null);
//        this.setBackground(Color.ORANGE);
//        this.setPreferredSize(new Dimension(250, 400));
//        this.initOptions(option_type);
//    }
//
//    private void initOptions(final int option_type) {
//        this.options = new Vector<>();
//        int i = 0;
//        if(option_type == OptionPanel.MAIN) {
//            for(String option : this.options_text) {
//                MainOptionButton button = new MainOptionButton(option);
//                button.setBounds(0, (OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * i, OptionButton.BUTTON_WIDTH, OptionButton.BUTTON_HEIGHT);
//                this.options.add(button);
//                this.add(button);
//                i++;
//            }
//            MainOptionButton but;
//            but = (MainOptionButton) this.options.get(0);
//            but.setAction_type(MainOptionButton.SEARCH_IN_DATABASE);
//            but = (MainOptionButton) this.options.get(1);
//            but.setAction_type(MainOptionButton.MODIFY_DATABASE);
//            but = (MainOptionButton) this.options.get(2);
//            but.setAction_type(MainOptionButton.SHOW_ACCOUNT);
//            but = (MainOptionButton) this.options.get(3);
//            but.setAction_type(MainOptionButton.EXIT);
//        } else {
//            for(String option : this.options_text) {
//                OptionButton button = new OptionButton(option);
//                button.setBounds(0, (OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * i, OptionButton.BUTTON_WIDTH, OptionButton.BUTTON_HEIGHT);
//                this.options.add(button);
//                this.add(button);
//                i++;
//            }
//        }
//    }
//
//    public static void initButtonVisuals(JButton button) {
//        button.setBackground(new Color(179, 122, 82));
//        button.setForeground(new Color(60,60 ,60));
//        button.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
//        button.setFocusPainted(false);
//        button.setHorizontalAlignment(JButton.CENTER);
//        button.setBorder(new LineBorder(Color.BLACK));
//    }
//
//    public Vector<JButton> getOptions() {
//        return this.options;
//    }
//
//    static class OptionButton extends JButton {
//        public final static int BUTTON_WIDTH = 250;
//        public final static int BUTTON_HEIGHT = 50;
//
//        @Getter
//        @Setter
//        private FrameContentManager action_manager;
//
//        OptionButton(final String text) {
//            super(text);
//            OptionPanel.initButtonVisuals(this);
//            this.action_manager = new MockManager();
//        }
//    }
//
//    static class MainOptionButton extends JButton {
//        public static final int SEARCH_IN_DATABASE = 0;
//        public static final int MODIFY_DATABASE = 1;
//        public static final int SHOW_ACCOUNT = 2;
//        public static final int EXIT = 3;
//        @Getter @Setter
//        private int action_type;
//        MainOptionButton(final String text) {
//            super(text);
//            OptionPanel.initButtonVisuals(this);
//        }
//    }
//}
//
//class AddingPanel extends JPanel {
//    private final JTextField name_fild;
//    private final JTextField surname_fild;
//    private final JTextField mail_fild;
//    private final JTextField password_field;
//    private final JTextField login_field;
//
//    public AddingPanel(ActionListener listener) {
//        this.setLayout(new GridLayout(6, 1));
//        this.name_fild = new JTextField("Name");
//        this.add(this.name_fild);
//        this.surname_fild = new JTextField("Surname");
//        this.add(this.surname_fild);
//        this.mail_fild = new JTextField("Mail");
//        this.add(this.mail_fild);
//        this.login_field = new JTextField("Login");
//        this.add(this.login_field);
//        this.password_field = new JTextField("Password");
//        this.add(this.password_field);
//
//        OptionPanel.OptionButton add_button = new OptionPanel.OptionButton("Add");
//        add_button.addActionListener(listener);
//        add_button.setAction_manager(new UserAdder(FrameContentManager.USERS));
//        this.add(add_button);
//
//        this.setBounds(200, 0, 400, 200);
//        this.setBackground(LibraryGUI.GUIData.BACKGROUND_COLOR);
//    }
//
//    public ArrayList<String> getData() {
//        ArrayList<String> result = new ArrayList<>();
//        result.add(this.login_field.getText());
//        result.add(this.password_field.getText());
//        result.add(this.name_fild.getText());
//        result.add(this.surname_fild.getText());
//        result.add(this.mail_fild.getText());
//        return result;
//    }
//}
//
//class BookAddingPanel extends JPanel {
//
//    private final JTextField name;
//
//    private final JTextField category;
//
//    private final JTextField author;
//
//
//    public BookAddingPanel(ActionListener listener) {
//        this.setLayout(new GridLayout(5, 1));
//        this.name = new JTextField("Name");
//        this.add(this.name);
//        this.category = new JTextField("Category");
//        this.add(this.category);
//        this.author = new JTextField("Author");
//        this.add(this.author);
//
//        OptionPanel.OptionButton add_button = new OptionPanel.OptionButton("Add");
//        add_button.addActionListener(listener);
//        add_button.setAction_manager(new BookAdder());
//        this.add(add_button);
//
//        this.add(LibraryGUI.main_page.getPrompt());
//        this.setBounds(200, 0, 400, 200);
//        this.setBackground(LibraryGUI.GUIData.BACKGROUND_COLOR);
//
//        LibraryGUI.main_page.changePrompt("Enter book data");
//    }
//
//    public ArrayList<String> getData() {
//        ArrayList<String> result = new ArrayList<>();
//        result.add(this.name.getText());
//        result.add(this.category.getText());
//        result.add(this.author.getText());
//        return result;
//    }
//}
//
//class BottomPanel extends JPanel {
//    public BottomPanel() {
//        this.setBackground(new Color(94, 94, 94));
//        this.setPreferredSize(new Dimension(100, 100));
//    }
//}