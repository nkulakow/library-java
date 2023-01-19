package org.example.GUI;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.security.DigestException;
import java.util.Vector;

public class ComponentDesigner {

    public static final Color BACKGROUND_COLOR = new Color(252, 252, 189);
    public static Font getDefaultFont(int size) {
        return new Font(Font.SERIF, Font.ITALIC, size);
    }
    public static Font getDefaultFont() {
        return getDefaultFont(20);
    }

    public static class OptionButton extends JButton {
        @Getter
        @Setter
        private FrameContentManager action_manager;

        public OptionButton(final String text, Dimension size) {
            super(text);
            this.action_manager = new MockManager();
            this.initVisuals(size);
        }

        private void initVisuals(Dimension size) {
            var default_color = new Color(179, 122, 82);
            var hovered_color = new Color(150, 106, 75);
            var clicked_color = new Color(82, 65, 53);
            var font_color = new Color(60,60 ,60);

            this.setPreferredSize(size);
            this.setBackground(default_color);
            this.setForeground(font_color);
            this.setFont(ComponentDesigner.getDefaultFont());
            this.setFocusPainted(false);
            this.setHorizontalAlignment(JButton.CENTER);
            this.setBorder(new LineBorder(Color.BLACK));

            this.setContentAreaFilled(false);
            this.setOpaque(true);
            this.addChangeListener(e -> {
                if (this.getModel().isPressed()) {
                    this.setBackground(clicked_color);
                } else if (this.getModel().isRollover()) {
                    this.setBackground(hovered_color);
                } else {
                    this.setBackground(default_color);
                }
            });
        }
    }
    public static JButton makeOptionButton(String text, int width, int height) {
        return new ComponentDesigner.OptionButton(text, new Dimension(width, height));
    }

    public static JButton makeOptionButton(String text) {
        return ComponentDesigner.makeOptionButton(text, 150, 50);
    }

    public static JLabel makeDefaultLabel(String text, int font_size) {
        var label = new JLabel(text);
        label.setFont(ComponentDesigner.getDefaultFont(font_size));
        label.setOpaque(false);

        return label;
    }

    public static JLabel makeDefaultLabel(String text) {
        var label = new JLabel(text);
        label.setFont(ComponentDesigner.getDefaultFont());
        label.setOpaque(false);

        return label;
    }

    public static JPanel makeAdminContentPanel() {
        var main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        main_panel.setBackground(Color.ORANGE);

        // content to change data in app etc.
        var content_panel = new JPanel();
        content_panel.setLayout(new BorderLayout());
        content_panel.setBackground(BACKGROUND_COLOR);
        var table = ComponentDesigner.makeUserTable();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(400, 600));
        scrollPane.setViewportView(table);
        content_panel.add(scrollPane, BorderLayout.CENTER);

        // north
        var search_panel = getSpacer(-1, 100);
        search_panel.setLayout(new BorderLayout());
        search_panel.add(ComponentDesigner.makeAdminSearchPanel());
        content_panel.add(search_panel, BorderLayout.NORTH);

        //south
        var adding_panel = getSpacer(-1, 150);
        adding_panel.setLayout(new BorderLayout());
        adding_panel.add(ComponentDesigner.makeAddingPanel());
        content_panel.add(adding_panel, BorderLayout.SOUTH);

        //content panel spacers
        content_panel.add(getSpacer(50, -1), BorderLayout.WEST);
        content_panel.add(getSpacer(50, -1), BorderLayout.EAST);

        // spacer on top
        var spacer = ComponentDesigner.getSpacer(-1, 100 - 3); // - main scroll pane border size
        spacer.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        spacer.setLayout(new BorderLayout());

        //library label
        var library_label = new JLabel("Library");
        library_label.setFont(ComponentDesigner.getDefaultFont(50));
        library_label.setHorizontalAlignment(SwingConstants.CENTER);
        library_label.setVerticalAlignment(SwingConstants.CENTER);
        library_label.setOpaque(false);
        spacer.add(library_label);

        main_panel.add(spacer, BorderLayout.NORTH);
        main_panel.add(content_panel, BorderLayout.CENTER);

        return main_panel;
    }

    public static JComponent makeBorderedComponent(JComponent component, int top, int left, int down, int right) {
        component.setBackground(ComponentDesigner.BACKGROUND_COLOR);
        component.setFont(ComponentDesigner.getDefaultFont());
        component.setBorder(BorderFactory.createMatteBorder(top, left, down, right, Color.BLACK));

        return component;
    }

    private static JPanel makeAdminSearchPanel() {
        var search_panel = new JPanel();
        var layout = new SpringLayout();
        search_panel.setLayout(layout);
        search_panel.setOpaque(false);

        var search_button = ComponentDesigner.makeOptionButton("Search");
        var modify_button = ComponentDesigner.makeOptionButton("Modify");
        var remove_button = ComponentDesigner.makeOptionButton("Remove");

        var search_field = ComponentDesigner.makeBorderedComponent(new JTextField("Search..."), 3, 3, 3, 3);

        int gap_vertical = 30; int button_size = 150; int field_size = 300; int gap_horizontal = 30;

        layout.putConstraint(SpringLayout.NORTH, remove_button, gap_vertical, SpringLayout.NORTH, search_panel);
        layout.putConstraint(SpringLayout.SOUTH, remove_button, -gap_vertical, SpringLayout.SOUTH, search_panel);
        layout.putConstraint(SpringLayout.WEST, remove_button, -(button_size + 50), SpringLayout.EAST, search_panel);
        layout.putConstraint(SpringLayout.EAST, remove_button, -50, SpringLayout.EAST, search_panel);

        layout.putConstraint(SpringLayout.NORTH, modify_button, gap_vertical, SpringLayout.NORTH, search_panel);
        layout.putConstraint(SpringLayout.SOUTH, modify_button, -gap_vertical, SpringLayout.SOUTH, search_panel);
        layout.putConstraint(SpringLayout.WEST, modify_button, -(button_size + gap_horizontal), SpringLayout.WEST, remove_button);
        layout.putConstraint(SpringLayout.EAST, modify_button, -30, SpringLayout.WEST, remove_button);

        layout.putConstraint(SpringLayout.NORTH, search_field, gap_vertical, SpringLayout.NORTH, search_panel);
        layout.putConstraint(SpringLayout.SOUTH, search_field, -gap_vertical, SpringLayout.SOUTH, search_panel);
        layout.putConstraint(SpringLayout.WEST, search_field, -(field_size + gap_horizontal), SpringLayout.WEST, modify_button);
        layout.putConstraint(SpringLayout.EAST, search_field, -gap_horizontal, SpringLayout.WEST, modify_button);

        layout.putConstraint(SpringLayout.NORTH, search_button, gap_vertical, SpringLayout.NORTH, search_panel);
        layout.putConstraint(SpringLayout.SOUTH, search_button, -gap_vertical, SpringLayout.SOUTH, search_panel);
        layout.putConstraint(SpringLayout.WEST, search_button, -(button_size + gap_horizontal), SpringLayout.WEST, search_field);
        layout.putConstraint(SpringLayout.EAST, search_button, -gap_horizontal, SpringLayout.WEST, search_field);

        search_panel.add(search_button);
        search_panel.add(search_field);
        search_panel.add(modify_button);
        search_panel.add(remove_button);

        return search_panel;
    }

    private static JPanel makeUserSearchPanel() {
        var search_panel = new JPanel();
        var layout = new SpringLayout();
        search_panel.setLayout(layout);
        search_panel.setOpaque(false);

        var search_button = ComponentDesigner.makeOptionButton("Search");

        var search_field = ComponentDesigner.makeBorderedComponent(new JTextField("Search..."), 3, 3, 3, 3);

        int gap_vertical = 30; int button_size = 150; int field_size = 300; int gap_horizontal = 30;

        layout.putConstraint(SpringLayout.NORTH, search_field, gap_vertical, SpringLayout.NORTH, search_panel);
        layout.putConstraint(SpringLayout.SOUTH, search_field, -gap_vertical, SpringLayout.SOUTH, search_panel);
        layout.putConstraint(SpringLayout.WEST, search_field, -50 - field_size, SpringLayout.EAST, search_panel);
        layout.putConstraint(SpringLayout.EAST, search_field, -50, SpringLayout.EAST, search_panel);

        layout.putConstraint(SpringLayout.NORTH, search_button, gap_vertical, SpringLayout.NORTH, search_panel);
        layout.putConstraint(SpringLayout.SOUTH, search_button, -gap_vertical, SpringLayout.SOUTH, search_panel);
        layout.putConstraint(SpringLayout.WEST, search_button, -(gap_horizontal + button_size), SpringLayout.WEST, search_field);
        layout.putConstraint(SpringLayout.EAST, search_button, -gap_horizontal, SpringLayout.WEST, search_field);

        search_panel.add(search_button);
        search_panel.add(search_field);

        return search_panel;
    }

    private static JPanel makeOrderPanel() {
        var order_panel = new JPanel();
        var layout = new SpringLayout();
        order_panel.setLayout(layout);
        order_panel.setOpaque(false);

        var months_field = ComponentDesigner.makeBorderedComponent(new JTextField("Months..."), 3, 3, 3, 3);

        var months_label = new JLabel("Enter months number:");
        months_label.setFont(ComponentDesigner.getDefaultFont(15));
        months_label.setOpaque(false);

        var months_panel = new JPanel();
        months_panel.setOpaque(false);
        months_panel.setLayout(new GridLayout(2, 1));
        months_panel.add(months_label);
        months_panel.add(months_field);

        var order_button = ComponentDesigner.makeOptionButton("Order");

        int panel_size = 300 + 6; int button_size = 150; int upper_gap = 30; int bottom_gap = 60; int gap = 30;

        layout.putConstraint(SpringLayout.NORTH, months_panel, 10, SpringLayout.NORTH, order_panel);
        layout.putConstraint(SpringLayout.SOUTH, months_panel, -bottom_gap, SpringLayout.SOUTH, order_panel);
        layout.putConstraint(SpringLayout.EAST, months_panel, -50, SpringLayout.EAST, order_panel);
        layout.putConstraint(SpringLayout.WEST, months_panel, -50 - panel_size, SpringLayout.EAST, order_panel);

        layout.putConstraint(SpringLayout.NORTH, order_button, upper_gap, SpringLayout.NORTH, order_panel);
        layout.putConstraint(SpringLayout.SOUTH, order_button, -bottom_gap, SpringLayout.SOUTH, order_panel);
        layout.putConstraint(SpringLayout.EAST, order_button, -gap, SpringLayout.WEST, months_panel);
        layout.putConstraint(SpringLayout.WEST, order_button, -(gap + panel_size), SpringLayout.WEST, months_panel);

        order_panel.add(order_button);
        order_panel.add(months_panel);

        return order_panel;
    }

    private static JPanel makeAddingPanel() {
        var adding_panel = new JPanel();
        var layout = new FlowLayout(FlowLayout.TRAILING, 30, 5);
        adding_panel.setLayout(layout);
        adding_panel.setOpaque(false);

        var field_size = new Dimension(200, 40);
        var prompt_size = new Dimension(200, 15);
        var panel_size = new Dimension(field_size.width, field_size.height + prompt_size.height + 10);
        int font_size = 15;

        var name_field = ComponentDesigner.makeBorderedComponent(new JTextField("Name"), 3, 3, 3, 3);
        name_field.setPreferredSize(field_size);
        var surname_field = ComponentDesigner.makeBorderedComponent(new JTextField("Surname"), 3, 3, 3, 3);
        surname_field.setPreferredSize(field_size);
        var login_field = ComponentDesigner.makeBorderedComponent(new JTextField("Login"), 3, 3, 3, 3);
        login_field.setPreferredSize(field_size);
        var password_field = ComponentDesigner.makeBorderedComponent(new JPasswordField(), 3, 3, 3, 3);
        password_field.setPreferredSize(field_size);
        var mail_field = ComponentDesigner.makeBorderedComponent(new JTextField("Mail"), 3, 3, 3, 3);
        mail_field.setPreferredSize(field_size);
        var add_button = ComponentDesigner.makeOptionButton("Add", 150, field_size.height);

        var name_panel = new JPanel();
        name_panel.setPreferredSize(panel_size);
        var name_prompt = new JLabel("Name:"); name_prompt.setOpaque(false);
        name_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); name_prompt.setPreferredSize(prompt_size);
        name_panel.add(name_prompt); name_panel.add(name_field); name_panel.setOpaque(false);

        var surname_panel = new JPanel();
        surname_panel.setPreferredSize(panel_size);
        var surname_prompt = new JLabel("Surname:"); surname_prompt.setOpaque(false);
        surname_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); surname_prompt.setPreferredSize(prompt_size);
        surname_panel.add(surname_prompt); surname_panel.add(surname_field); surname_panel.setOpaque(false);

        var login_panel = new JPanel();
        login_panel.setPreferredSize(panel_size);
        var login_prompt = new JLabel("Login:"); login_prompt.setOpaque(false);
        login_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); login_prompt.setPreferredSize(prompt_size);
        login_panel.add(login_prompt); login_panel.add(login_field); login_panel.setOpaque(false);

        var password_panel = new JPanel();
        password_panel.setPreferredSize(panel_size);
        var password_prompt = new JLabel("Password:"); password_prompt.setOpaque(false);
        password_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); password_prompt.setPreferredSize(prompt_size);
        password_panel.add(password_prompt); password_panel.add(password_field); password_panel.setOpaque(false);

        var mail_panel = new JPanel();
        mail_panel.setPreferredSize(panel_size);
        var mail_prompt = new JLabel("Mail:"); mail_prompt.setOpaque(false);
        mail_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); mail_prompt.setPreferredSize(prompt_size);
        mail_panel.add(mail_prompt); mail_panel.add(mail_field); mail_panel.setOpaque(false);

        var button_panel = new JPanel();
        button_panel.setPreferredSize(panel_size);
        var button_prompt = new JLabel(); button_prompt.setOpaque(false);
        button_prompt.setPreferredSize(prompt_size);
        button_panel.add(button_prompt); button_panel.add(add_button); button_panel.setOpaque(false);

        adding_panel.add(button_panel);
        adding_panel.add(name_panel);
        adding_panel.add(surname_panel);
        adding_panel.add(login_panel);
        adding_panel.add(password_panel);
        adding_panel.add(mail_panel);

        return adding_panel;
    }

    public static JTable makeUserTable() {
        String[] column_names = {"Id", "Name", "Surname"};
        String[][] data = {
                {"1", "Jan", "Kowalski"},
                {"2", "Antoni", "Nowak"}
        };
        return new ObjectTable(data, column_names);
    }

    public static JTable makeBookTable() {
        String[] column_names = {"Id", "Name", "Author"};
        String[][] data = {
                {"1", "The Witcher", "Andrzej Sapkowski"},
                {"2", "The Lord of the Rings", "John Ronald Reuel Tolkien"}
        };
        return new ObjectTable(data, column_names);
    }

    public static JPanel makeLeftAdminPanel() {
        // main panel for left options
        var left_desktop = new JPanel();
        left_desktop.setBackground(Color.ORANGE);
        left_desktop.setPreferredSize(new Dimension(340, -1));
        left_desktop.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.BLACK));

        //label WELCOME on top
        var welcome_label = new JLabel("Welcome!");
        welcome_label.setFont(ComponentDesigner.getDefaultFont(50));
        welcome_label.setHorizontalAlignment(SwingConstants.CENTER);
        welcome_label.setVerticalAlignment(SwingConstants.CENTER);
        welcome_label.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
        welcome_label.setOpaque(false);

        //label MANAGE below
        var manage_label = new JLabel("Manage:");
        manage_label.setFont(ComponentDesigner.getDefaultFont());
        manage_label.setFont(ComponentDesigner.getDefaultFont(50));
        manage_label.setHorizontalAlignment(SwingConstants.CENTER);
        manage_label.setVerticalAlignment(SwingConstants.CENTER);
        manage_label.setOpaque(false);

        //buttons
        var users_button = ComponentDesigner.makeOptionButton("Users", 140, 60);
        var books_button = ComponentDesigner.makeOptionButton("Books", 140, 60);
        var exit_button = ComponentDesigner.makeOptionButton("EXIT", 300, 60);

        //left options panel layout
        var layout = new SpringLayout();
        left_desktop.setLayout(layout);

        layout.putConstraint(SpringLayout.NORTH, welcome_label, 0, SpringLayout.NORTH, left_desktop);
        layout.putConstraint(SpringLayout.WEST, welcome_label, 0, SpringLayout.WEST, left_desktop);
        layout.putConstraint(SpringLayout.EAST, welcome_label, 0, SpringLayout.EAST, left_desktop);
        layout.putConstraint(SpringLayout.SOUTH, welcome_label, 100, SpringLayout.NORTH, left_desktop);

        layout.putConstraint(SpringLayout.NORTH, manage_label, 20, SpringLayout.SOUTH, welcome_label);
        layout.putConstraint(SpringLayout.WEST, manage_label, 10, SpringLayout.WEST, left_desktop);
        layout.putConstraint(SpringLayout.EAST, manage_label, 10, SpringLayout.EAST, left_desktop);
        layout.putConstraint(SpringLayout.SOUTH, manage_label, 100 + 20, SpringLayout.SOUTH, welcome_label);

        layout.putConstraint(SpringLayout.NORTH, users_button, 20, SpringLayout.SOUTH, manage_label);
        layout.putConstraint(SpringLayout.WEST, users_button, 10, SpringLayout.WEST, left_desktop);

        layout.putConstraint(SpringLayout.NORTH, books_button, 20, SpringLayout.SOUTH, manage_label);
        layout.putConstraint(SpringLayout.WEST, books_button, 20, SpringLayout.EAST, users_button);
        layout.putConstraint(SpringLayout.EAST, books_button, -10, SpringLayout.EAST, left_desktop);

        layout.putConstraint(SpringLayout.SOUTH, exit_button, -20, SpringLayout.SOUTH, left_desktop);
        layout.putConstraint(SpringLayout.WEST, exit_button, 10, SpringLayout.WEST, left_desktop);
        layout.putConstraint(SpringLayout.EAST, exit_button, -10, SpringLayout.EAST, left_desktop);

        left_desktop.add(welcome_label);
        left_desktop.add(manage_label);
        left_desktop.add(users_button);
        left_desktop.add(books_button);
        left_desktop.add(exit_button);

        return left_desktop;
    }

    public static JPanel makeInfoPanel() {
        int gap = 10;
        var field_size = new Dimension(300 - 6, 40);
        var font_size = 15;
        var prompt_size = new Dimension(field_size.width, 15);
        var panel_size = new Dimension(field_size.width, prompt_size.height + field_size.height + 25);
        var info_panel_size = new Dimension(field_size.width, 6 * (panel_size.height + gap));

        var info_panel = new JPanel();
        info_panel.setOpaque(false);
        info_panel.setPreferredSize(info_panel_size);
        var layout = new GridLayout(7, 1);
        layout.setVgap(gap);
        info_panel.setLayout(layout);

        var label = new JLabel("Your data:"); label.setFont(ComponentDesigner.getDefaultFont(40));
        label.setOpaque(false);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        var name_field = ComponentDesigner.makeBorderedComponent(new JTextField("Name"), 3, 3, 3, 3);
        name_field.setPreferredSize(field_size);
        var surname_field = ComponentDesigner.makeBorderedComponent(new JTextField("Surname"), 3, 3, 3, 3);
        surname_field.setPreferredSize(field_size);
        var login_field = ComponentDesigner.makeBorderedComponent(new JTextField("Login"), 3, 3, 3, 3);
        login_field.setPreferredSize(field_size);
        var password_field = ComponentDesigner.makeBorderedComponent(new JPasswordField(), 3, 3, 3, 3);
        password_field.setPreferredSize(field_size);
        var mail_field = ComponentDesigner.makeBorderedComponent(new JTextField("Mail"), 3, 3, 3, 3);
        mail_field.setPreferredSize(field_size);
        var button = ComponentDesigner.makeOptionButton("Confirm");

        var name_panel = new JPanel();
        name_panel.setPreferredSize(panel_size);
        var name_prompt = new JLabel("Name:"); name_prompt.setOpaque(false);
        name_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); name_prompt.setPreferredSize(prompt_size);
        name_panel.add(name_prompt); name_panel.add(name_field); name_panel.setOpaque(false);

        var surname_panel = new JPanel();
        surname_panel.setPreferredSize(panel_size);
        var surname_prompt = new JLabel("Surname:"); surname_prompt.setOpaque(false);
        surname_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); surname_prompt.setPreferredSize(prompt_size);
        surname_panel.add(surname_prompt); surname_panel.add(surname_field); surname_panel.setOpaque(false);

        var login_panel = new JPanel();
        login_panel.setPreferredSize(panel_size);
        var login_prompt = new JLabel("Login:"); login_prompt.setOpaque(false);
        login_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); login_prompt.setPreferredSize(prompt_size);
        login_panel.add(login_prompt); login_panel.add(login_field); login_panel.setOpaque(false);

        var password_panel = new JPanel();
        password_panel.setPreferredSize(panel_size);
        var password_prompt = new JLabel("Password:"); password_prompt.setOpaque(false);
        password_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); password_prompt.setPreferredSize(prompt_size);
        password_panel.add(password_prompt); password_panel.add(password_field); password_panel.setOpaque(false);

        var mail_panel = new JPanel();
        mail_panel.setPreferredSize(panel_size);
        var mail_prompt = new JLabel("Mail:"); mail_prompt.setOpaque(false);
        mail_prompt.setFont(ComponentDesigner.getDefaultFont(font_size)); mail_prompt.setPreferredSize(prompt_size);
        mail_panel.add(mail_prompt); mail_panel.add(mail_field); mail_panel.setOpaque(false);

        info_panel.add(label);
        info_panel.add(name_panel);
        info_panel.add(surname_panel);
        info_panel.add(login_panel);
        info_panel.add(password_panel);
        info_panel.add(mail_panel);
        info_panel.add(button);

        return info_panel;
    }

    public static JPanel makeLeftUserPanel() {
        // main panel for left options
        var left_desktop = new JPanel();
        left_desktop.setBackground(Color.ORANGE);
        left_desktop.setPreferredSize(new Dimension(340, -1));
        left_desktop.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.BLACK));

        //label WELCOME on top
        var welcome_label = new JLabel("Welcome!");
        welcome_label.setFont(ComponentDesigner.getDefaultFont(50));
        welcome_label.setHorizontalAlignment(SwingConstants.CENTER);
        welcome_label.setVerticalAlignment(SwingConstants.CENTER);
        welcome_label.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
        welcome_label.setOpaque(false);

        //buttons
        var account_button = ComponentDesigner.makeOptionButton("My account", 140, 100);
        var library_button = ComponentDesigner.makeOptionButton("Library", 140, 100);
        var exit_button = ComponentDesigner.makeOptionButton("EXIT", 300, 60);

        //user info
        var info_panel = ComponentDesigner.makeInfoPanel();

        //left options panel layout
        var layout = new SpringLayout();
        left_desktop.setLayout(layout);

        layout.putConstraint(SpringLayout.NORTH, welcome_label, 0, SpringLayout.NORTH, left_desktop);
        layout.putConstraint(SpringLayout.WEST, welcome_label, 0, SpringLayout.WEST, left_desktop);
        layout.putConstraint(SpringLayout.EAST, welcome_label, 0, SpringLayout.EAST, left_desktop);
        layout.putConstraint(SpringLayout.SOUTH, welcome_label, 100, SpringLayout.NORTH, left_desktop);

        layout.putConstraint(SpringLayout.SOUTH, exit_button, -20, SpringLayout.SOUTH, left_desktop);
        layout.putConstraint(SpringLayout.WEST, exit_button, 10, SpringLayout.WEST, left_desktop);
        layout.putConstraint(SpringLayout.EAST, exit_button, -10, SpringLayout.EAST, left_desktop);

        layout.putConstraint(SpringLayout.SOUTH, account_button, -60, SpringLayout.NORTH, exit_button);
        layout.putConstraint(SpringLayout.WEST, account_button, 10, SpringLayout.WEST, left_desktop);

        layout.putConstraint(SpringLayout.SOUTH, library_button, -60, SpringLayout.NORTH, exit_button);
        layout.putConstraint(SpringLayout.WEST, library_button, 20, SpringLayout.EAST, account_button);
        layout.putConstraint(SpringLayout.EAST, library_button, -10, SpringLayout.EAST, left_desktop);

        layout.putConstraint(SpringLayout.NORTH, info_panel, 20, SpringLayout.SOUTH, welcome_label);
        layout.putConstraint(SpringLayout.WEST, info_panel, 20, SpringLayout.WEST, left_desktop);
        layout.putConstraint(SpringLayout.EAST, info_panel, -20, SpringLayout.EAST, left_desktop);

        left_desktop.add(welcome_label);
        left_desktop.add(account_button);
        left_desktop.add(library_button);
        left_desktop.add(exit_button);
        left_desktop.add(info_panel);

        return left_desktop;
    }

    public static JPanel makeUserContentPanel() {
        var main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        main_panel.setBackground(Color.ORANGE);

        // content to change data in app etc.
        var content_panel = new JPanel();
        content_panel.setLayout(new BorderLayout());
        content_panel.setBackground(BACKGROUND_COLOR);
        var table = ComponentDesigner.makeBookTable();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(400, 600));
        scrollPane.setViewportView(table);
        content_panel.add(scrollPane, BorderLayout.CENTER);

        // north
        var search_panel = getSpacer(-1, 100);
        search_panel.setLayout(new BorderLayout());
        search_panel.add(ComponentDesigner.makeUserSearchPanel());
        content_panel.add(search_panel, BorderLayout.NORTH);

        //south
        var adding_panel = getSpacer(-1, 150);
        adding_panel.setLayout(new BorderLayout());
        adding_panel.add(ComponentDesigner.makeOrderPanel());
        content_panel.add(adding_panel, BorderLayout.SOUTH);

        //content panel spacers
        content_panel.add(getSpacer(50, -1), BorderLayout.WEST);
        content_panel.add(getSpacer(50, -1), BorderLayout.EAST);

        // spacer on top
        var spacer = ComponentDesigner.getSpacer(-1, 100 - 3); // - main scroll pane border size
        spacer.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        spacer.setLayout(new BorderLayout());

        //library label
        var library_label = new JLabel("Library");
        library_label.setFont(ComponentDesigner.getDefaultFont(50));
        library_label.setHorizontalAlignment(SwingConstants.CENTER);
        library_label.setVerticalAlignment(SwingConstants.CENTER);
        library_label.setOpaque(false);
        spacer.add(library_label);

        main_panel.add(spacer, BorderLayout.NORTH);
        main_panel.add(content_panel, BorderLayout.CENTER);

        return main_panel;
    }

    public static JPanel getSpacer(int width, int height) {
        var space = new JPanel(null);
        space.setPreferredSize(new Dimension(width, height));
        space.setOpaque(false);
        return space;
    }
}
