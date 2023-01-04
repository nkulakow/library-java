package org.example.GUI;

import org.example.LibraryContextPackage.LibraryContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;


class LogInPage extends Page {
    //some constant common stuff
    final static int width = 800;
    final static int height = 600;

    // private variables
    private JTextField login_field;
    private JPasswordField password_field;
    private JButton login_button;
    private JCheckBox userbox;
    private ButtonGroup checksGroup;
    private JCheckBox adminbox;
    private JLabel prompt;

    private final JPanel panel;

    public LogInPage() {
        super(LogInPage.width, LogInPage.height);
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.setResizable(false);
        this.initContent();
    }

    private void initContent() {
        this.userbox = new JCheckBox("User");
        this.adminbox = new JCheckBox("Admin");
        this.userbox.setBounds(300, 360, 80, 20);
        this.adminbox.setBounds(400, 360, 80, 20);
        this.checksGroup = new ButtonGroup();
        checksGroup.add(userbox);
        checksGroup.add(adminbox);

        this.login_field = new JTextField();
        this.login_field.setBounds(300, 200, 200, 30);
        this.password_field = new JPasswordField();
        this.password_field.setBounds(300, 300, 200, 30);
        this.login_button = new JButton("Log in");
        this.login_button.setBounds(300, 400, 80, 40);
        JLabel login_text = new JLabel("Enter login");
        login_text.setBounds(300, 160, 100, 30);
        JLabel password_text = new JLabel("Enter password");
        password_text.setBounds(300, 260, 150, 30);
        this.prompt = new JLabel("Enter your login and password");
        this.prompt.setBounds(150, 420, 500, 40);
        this.prompt.setFont(new Font(this.prompt.getFont().getName(), Font.PLAIN, 15));

        this.panel.add(this.login_field);
        this.panel.add(this.password_field);
        this.panel.add(this.login_button);
        this.panel.add(this.userbox);
        this.panel.add(this.adminbox);
        this.panel.add(login_text);
        this.panel.add(password_text);
        this.panel.setBackground(new Color(243, 229, 65));
        var bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 500, 800, 100);
        bottomPanel.setBackground(new Color(243, 229, 65));
        bottomPanel.add(this.prompt);
        this.add(bottomPanel);
        this.add(this.panel);

        this.login_button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.login_button) {
            String login = this.login_field.getText();
            String password = String.valueOf(this.password_field.getPassword());
            if (this.userbox.isSelected())
            {
                if(LibraryContext.checkLoggingUsers(login, password)) {
                    //userpage
                }
                else {
                    this.prompt.setText("Incorrect login and/or password");
                }
            }
            else if (this.adminbox.isSelected()) {
                if (LibraryContext.checkLoggingAdmins(login, password)) {
                    LibraryGUI.changeAfterLoggedToAdminSite();
                }
                else {
                    this.prompt.setText("Incorrect login and/or password");
                }
            }
            else {
                this.prompt.setText("Select 'User' or 'Admin'");
            }

        }
    }

    @Override
    public void sendMessageToPrompt(final String mes){
        this.prompt.setText(mes);
    }
}