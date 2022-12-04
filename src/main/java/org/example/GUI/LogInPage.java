package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

class LogInPage extends Page {
    //some constant common stuff
    final static int width = 800;
    final static int height = 600;

    // private variables
    private JTextField login_field;
    private JPasswordField password_field;
    private JButton login_button;
    private JLabel prompt;

    private static final String LOGIN = "root";
    private static final char[] PASSWORD = new char[] {'r', 'o', 'o', 't'};

    private final JPanel panel;

    public LogInPage() {
        super(LogInPage.width, LogInPage.height);
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.setResizable(false);
        this.initContent();
    }

    private void initContent() {
        this.login_field = new JTextField();
        this.login_field.setBounds(300, 200, 200, 30);
        this.password_field = new JPasswordField();
        this.password_field.setBounds(300, 300, 200, 30);
        this.login_button = new JButton("Log in");
        this.login_button.setBounds(300, 350, 80, 40);
        JLabel login_text = new JLabel("Enter login");
        login_text.setBounds(300, 160, 100, 30);
        JLabel password_text = new JLabel("Enter password");
        password_text.setBounds(300, 260, 150, 30);
        this.prompt = new JLabel("Enter Your login and password.");
        this.prompt.setBounds(150, 420, 500, 40);
        this.prompt.setFont(new Font(this.prompt.getFont().getName(), Font.PLAIN, 20));

        this.panel.add(this.login_field);
        this.panel.add(this.password_field);
        this.panel.add(this.login_button);
        this.panel.add(login_text);
        this.panel.add(password_text);
        this.panel.setBackground(new Color(204, 153, 255));
        var bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 500, 800, 100);
        bottomPanel.setBackground(new Color(204, 153, 255));
        bottomPanel.add(this.prompt);
        this.add(bottomPanel);
        this.add(this.panel);

        this.login_button.addActionListener(this);
    }

    private boolean checkLogging() {
        return this.login_field.getText().equals(LogInPage.LOGIN) && Arrays.equals(this.password_field.getPassword(), LogInPage.PASSWORD);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("bly");
        if (e.getSource() == this.login_button) {
            if(this.checkLogging()) {
                this.prompt.setText("Logged in");
                LibraryGUI.changeAfterLogged();
            }
            else {
                this.prompt.setText("Incorrect login and/or password");
            }
        }
    }
}