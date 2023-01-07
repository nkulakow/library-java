package org.example.GUI;

import org.example.LibraryContextPackage.LibraryContext;
import org.example.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

public class MainPage extends Page {
    //content in center
    private JPanel center_panel;
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
                1200,
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
        button.setAction_manager(new Shower());
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
                "Delete book",
                "Add loan",
                "Modify loan",
                "Delete loan"
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
        button.setAction_manager(new MockManager());
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(5);
        button.setAction_manager(new DeleteShower(FrameContentManager.BOOKS));
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(6);
        button.setAction_manager(new MockManager());
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(7);
        button.setAction_manager(new MockManager());
        button = (OptionPanel.OptionButton) this.modify_options.getOptions().get(8);
        button.setAction_manager(new MockManager());
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
        JLabel info = new JLabel(admin.describe());
        info.setBounds(this.content_panel.getWidth() - 400, 0, 800, 30);
        info.setFont(new Font(info.getFont().getName(), info.getFont().getStyle(), 30));

        this.content_panel.removeAll();
        this.content_panel.add(info);
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