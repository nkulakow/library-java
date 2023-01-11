package org.example.GUI;

import org.example.LibraryContextPackage.LibraryContext;
import org.example.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Base64;
import java.util.List;
import java.util.Vector;

public class MainPage extends Page {
    //content in center
    private JPanel center_panel;
    private OptionPanel search_options;
    private OptionPanel modify_options;
    private JPanel content_panel;
    private OptionPanel current_options;
    private JLabel prompt;

    public MainPage () {
        super(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        this.init();
    }

    public JLabel getPrompt() {
        return prompt;
    }
    public void changePrompt(final String text) {
        this.prompt.setText(text);
        this.prompt.setSize(prompt.getFont().getSize() * prompt.getText().length(), prompt.getHeight());
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.current_options = null;
        this.prompt = new JLabel();
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
                1200,
                800);
        this.center_panel.add(this.content_panel);
        this.content_panel.setVisible(true);
        this.content_panel.setBackground(LibraryGUI.GUIData.BACKGROUND_COLOR);
        this.center_panel.setBackground(LibraryGUI.GUIData.BACKGROUND_COLOR);
    }

    private void initOptions() {
        String[]  option_text = {
                "Search in database",
                "Modify database",
                "Show my account",
                "Exit"
        };
        OptionPanel main_options = new OptionPanel(new Vector<>(List.of(option_text)), OptionPanel.MAIN);
        for (var option : main_options.getOptions()) {
            option.addActionListener(this);
        }
        this.add(main_options, BorderLayout.WEST);
    }

    private void initSearchOptions() {
        String[] search_options_text = {
                "Show users",
                "Search for user",
                "Search for book"
        };
        OptionPanel srch_options = new OptionPanel(new Vector<>(List.of(search_options_text)), OptionPanel.NOT_MAIN);
        for (var option : srch_options.getOptions())
            option.addActionListener(this);
        this.search_options = srch_options;
        this.search_options.setBackground(this.getBackground());
        this.center_panel.add(this.search_options, BorderLayout.CENTER);
        int panel_height =( OptionPanel.OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * this.search_options.LENGTH - OptionPanel.MARGIN_SIZE;
        this.search_options.setBounds(0, 0, OptionPanel.OptionButton.BUTTON_WIDTH, panel_height);
        this.search_options.setVisible(false);

        OptionPanel.OptionButton button;
        button = (OptionPanel.OptionButton) this.search_options.getOptions().get(0);
        button.setAction_manager(new Shower(FrameContentManager.USERS));
        button = (OptionPanel.OptionButton) this.search_options.getOptions().get(1);
        button.setAction_manager(new SearchShower(FrameContentManager.USERS));
        button = (OptionPanel.OptionButton) this.search_options.getOptions().get(2);
        button.setAction_manager(new SearchShower(FrameContentManager.BOOKS));
    }

    private void initModifyOptions() {
        String[] modify_options_text = {
                "Add user",
                "Modify user",
                "Delete user",
                "Add book",
                "Modify book",
                "Delete book"
        };
        OptionPanel mod_options = new OptionPanel(new Vector<>(List.of(modify_options_text)), OptionPanel.NOT_MAIN);
        for (var option : mod_options.getOptions())
            option.addActionListener(this);
        this.modify_options = mod_options;
        this.modify_options.setBackground(this.getBackground());
        this.center_panel.add(this.modify_options, BorderLayout.CENTER);
        int panel_height =( OptionPanel.OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * this.modify_options.LENGTH - OptionPanel.MARGIN_SIZE;
        this.modify_options.setBounds(0, 0, OptionPanel.OptionButton.BUTTON_WIDTH, panel_height);
        this.modify_options.setVisible(false);


        OptionPanel.OptionButton button;
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(0);
        button.setAction_manager(new UserAddingShower(FrameContentManager.USERS));
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(1);
        button.setAction_manager(new ModifyShower(FrameContentManager.USERS));
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(2);
        button.setAction_manager(new DeleteShower(FrameContentManager.USERS));
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(3);
        button.setAction_manager(new BookAddingShower());
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(4);
        button.setAction_manager(new ModifyShower(FrameContentManager.BOOKS));
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(5);
        button.setAction_manager(new DeleteShower(FrameContentManager.BOOKS));
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

    private void showAccount() {
        var admin = LibraryContext.getCurrentAdmin();

        var name_field = new JTextField(admin.getName());
        name_field.setBounds(this.content_panel.getWidth() / 2 - 150, 0, 300, 40);
        var surname_field = new JTextField(admin.getSurname());
        surname_field.setBounds(this.content_panel.getWidth() / 2 - 150, 40, 300, 40);
        var mail_field = new JTextField(admin.getMail());
        mail_field.setBounds(this.content_panel.getWidth() / 2 - 150, 80, 300, 40);
        var login_field = new JTextField(admin.getLogin());
        login_field.setBounds(this.content_panel.getWidth() / 2 - 150, 120, 300, 40);
        byte[] bytePasswd = Base64.getDecoder().decode(admin.getPassword());
        var password = new String(bytePasswd);
        var password_field = new JPasswordField(password);
        password_field.setBounds(this.content_panel.getWidth() / 2 - 150, 160, 300, 40);
        var button = new OptionPanel.OptionButton("Confirm");
        button.setBounds(name_field.getX(), 200, 150, 40);
        button.addActionListener(this);
        button.setAction_manager(new AdminModificationApplier());
        this.prompt.setText("");
        this.prompt.setBounds(name_field.getX(), 240, 300, 40);

        this.content_panel.removeAll();
        this.content_panel.setLayout(null);
        this.content_panel.add(name_field);
        this.content_panel.add(surname_field);
        this.content_panel.add(mail_field);
        this.content_panel.add(login_field);
        this.content_panel.add(password_field);
        this.content_panel.add(button);
        this.content_panel.add(this.prompt);
        this.content_panel.validate();
        this.content_panel.repaint();
    }

    private void initBottom() {
        this.add(new BottomPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof OptionPanel.OptionButton button) {
            button.getAction_manager().manage(this.content_panel);
        } else if (e.getSource() instanceof OptionPanel.MainOptionButton button) {
            switch (button.getAction_type()) {
                case OptionPanel.MainOptionButton.SEARCH_IN_DATABASE -> this.showOptions(this.search_options);
                case OptionPanel.MainOptionButton.MODIFY_DATABASE -> this.showOptions(this.modify_options);
                case OptionPanel.MainOptionButton.SHOW_ACCOUNT -> this.showAccount();
                case OptionPanel.MainOptionButton.EXIT -> Main.exit();
                default -> {

                }
            }
        }
    }
}