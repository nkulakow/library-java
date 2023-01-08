package org.example.GUI;

import org.example.LibraryContextPackage.LibraryContext;
import org.example.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

public class UserPage extends Page {
    //content in center
    private JPanel center_panel;
    private UserOptionPanel show_options;
    private JPanel content_panel;
    private UserOptionPanel current_options;

    public UserPage () {
        super(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        this.init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.current_options = null;
        this.initCenter();
        this.initOptions();
        this.initShowOptions();
        this.initBottom();
    }

    private void initCenter() {
        this.center_panel = new JPanel();
        this.center_panel.setLayout(null);
        this.add(this.center_panel, BorderLayout.CENTER);

        //content panel
        this.content_panel = new JPanel();
        this.content_panel.setBounds(
                UserOptionPanel.OptionButton.BUTTON_WIDTH,
                0,
                1200,
                800);
        this.center_panel.add(this.content_panel);
        this.content_panel.setVisible(true);
    }

    private void initOptions() {
        String[]  option_text = {
                "Order book",
                "Modify my data",
                "Show my penalties",
                "Show my books",
                "Exit"
        };
        UserOptionPanel main_options = new UserOptionPanel(new Vector<>(List.of(option_text)), UserOptionPanel.MAIN);
        for (var option : main_options.getOptions()) {
            option.addActionListener(this);
        }
        this.add(main_options, BorderLayout.WEST);
    }

    private void initShowOptions() {
        String[] search_options_text = {
                "Show ordered",
                "Show borrowed",
        };
        UserOptionPanel srch_options = new UserOptionPanel(new Vector<>(List.of(search_options_text)), UserOptionPanel.NOT_MAIN);
        for (var option : srch_options.getOptions())
            option.addActionListener(this);
        this.show_options = srch_options;
        this.show_options.setBackground(this.getBackground());
        this.center_panel.add(this.show_options, BorderLayout.CENTER);
        int panel_height =( UserOptionPanel.OptionButton.BUTTON_HEIGHT + UserOptionPanel.MARGIN_SIZE) * this.show_options.LENGTH - UserOptionPanel.MARGIN_SIZE;
        this.show_options.setBounds(0, 0, UserOptionPanel.OptionButton.BUTTON_WIDTH, panel_height);
        this.show_options.setVisible(false);

        UserOptionPanel.OptionButton button;
        button = (UserOptionPanel.OptionButton) this.show_options.getOptions().get(0);
        button.setAction_manager(new Shower(FrameContentManager.ORDERED_BOOKS));
        button = (UserOptionPanel.OptionButton) this.show_options.getOptions().get(1);
        button.setAction_manager(new ReturnChooser());
    }

    private void Modify() {
        if(this.current_options != null) {
            this.current_options.setVisible(false);
        }
        UsersModificationApplier.last_modified_id = LibraryContext.getCurrentUser().getUserId();

        var name_field      = new JTextField(LibraryContext.getCurrentUser().getName());
        var surname_field   = new JTextField(LibraryContext.getCurrentUser().getSurname());
        var mail_field      = new JTextField(LibraryContext.getCurrentUser().getMail());
        var login_field     = new JTextField(LibraryContext.getCurrentUser().getLogin());
        var password_field  = new JTextField(LibraryContext.getCurrentUser().getPassword());
        var panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBounds(content_panel.getSize().width / 2 - 300, 0, 600, 30 * 3);
        panel.add(name_field);
        panel.add(surname_field);
        panel.add(mail_field);
        panel.add(login_field);
        panel.add(password_field);

        var button = new UserOptionPanel.OptionButton("Confirm");
        button.setBounds(content_panel.getSize().width / 2 - 300,  panel.getHeight(), 150, 30);
        button.addActionListener(LibraryGUI.user_page);
        button.setAction_manager(new UsersModificationApplier(FrameContentManager.USER_USER_MOD));

        var prompt = new JLabel();
        prompt.setBounds(content_panel.getSize().width / 2 - 150,  panel.getHeight() + button.getHeight(), 300, 30);

        content_panel.removeAll();
        content_panel.setLayout(null);
        content_panel.add(panel);
        content_panel.add(button);
        content_panel.add(prompt);
        content_panel.validate();
        content_panel.repaint();
    }

    private void showOptions(UserOptionPanel options) {
        this.content_panel.removeAll();
        if(this.current_options != null) {
            this.current_options.setVisible(false);
        }
        this.current_options = options;
        this.current_options.setVisible(true);
        this.current_options.setBackground(Color.ORANGE);
        this.center_panel.validate();
    }

    private void showPenalties() {
        this.content_panel.removeAll();
        if(this.current_options != null) {
            this.current_options.setVisible(false);
        }
        var user = LibraryContext.getCurrentUser().describe();
        var infos = new Vector<String>();
        infos.add(user);
        try{
        var penalties = LibraryContext.showPenalties();
        for (var pen: penalties.keySet()){
            infos.add(pen + " " + penalties.get(pen));
        }}
        catch (java.lang.NullPointerException e){
            infos.add("No penalties");
        }
        JList<String> list = new JList<>(infos);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        list.setBounds(content_panel.getSize().width / 2 - 300, 0, 600, 30 * list.getModel().getSize());

        this.content_panel.add(list);
        this.content_panel.validate();
        this.content_panel.repaint();
    }


    private void SearchBook(){
        if(this.current_options != null) {
            this.current_options.setVisible(false);
        }
        content_panel.removeAll();
        content_panel.setLayout(null);

        var search_field = new JTextField();
        search_field.setBounds(content_panel.getSize().width / 2 - 150, 0, 300, 30);

        var button = new UserOptionPanel.OptionButton("Search");
        button.addActionListener(LibraryGUI.user_page);
        button.setAction_manager(new OrderChooser());
        button.setBounds(search_field.getX(), 30, 150, 30);

        content_panel.add(search_field);
        content_panel.add(button);
        content_panel.validate();
        content_panel.repaint();
    }

    private void initBottom() {
        this.add(new BottomPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof UserOptionPanel.OptionButton button) {
            button.getAction_manager().manage(this.content_panel);
        } else if (e.getSource() instanceof UserOptionPanel.MainOptionButton button) {
            switch (button.getAction_type()) {
                case UserOptionPanel.MainOptionButton.SEARCH_IN_DATABASE -> this.SearchBook();
                case UserOptionPanel.MainOptionButton.MODIFY_DATABASE -> this.Modify();
                case UserOptionPanel.MainOptionButton.SHOW_ACCOUNT -> this.showPenalties();
                case UserOptionPanel.MainOptionButton.SHOW_BOOKS -> this.showOptions(this.show_options);
                case UserOptionPanel.MainOptionButton.EXIT -> Main.exit();
                default -> {

                }
            }
        }
    }
}
