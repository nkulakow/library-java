package org.example;

import javax.swing.JButton;
import  javax.swing.JTextField;
import  javax.swing.JPasswordField;
import  javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends MyFrame implements ActionListener {
    final String login = "root";
    final String password = "root";

    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JTextField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("userID");
    JLabel userPasswordLabel = new JLabel("password");
    JLabel messageLabel = new JLabel();

    boolean loggedIn;

    LoginPage(int f_width, int f_height){
        super(f_width, f_height);
        this.loggedIn = false;
        this.setTitle("login page");
        this.setLabels();
    }


    void setLabels(){
        userIDLabel.setBounds(150, 150, 75, 25);
        userPasswordLabel.setBounds(150, 200, 75, 25);
        messageLabel.setBounds(200, 300, 400, 25);

        userIDField.setBounds(225,150,200,25);
        userPasswordField.setBounds(225,200,200,25);

        loginButton.setBounds(225,250,100,25);
        loginButton.addActionListener(this);

        resetButton.setBounds(325,250,100,25);
        resetButton.addActionListener(this);

        this.add(userIDLabel);
        this.add(userPasswordLabel);
        this.add(messageLabel);
        this.add(userIDField);
        this.add(userPasswordField);
        this.add(loginButton);
        this.add(resetButton);
    }


    public boolean isLoggedIn(){
        return this.loggedIn;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resetButton){
            userIDField.setText("");
            userPasswordField.setText("");
        }
        if(e.getSource()==loginButton){
            String userID = userIDField.getText();
            String userPassword = userPasswordField.getText();

            if(userID.equals(this.login) && userPassword.equals(this.password))
            {
                this.messageLabel.setText("Logged in");
                this.loggedIn = true;
            }
            else {
                this.messageLabel.setText("Incorrect login and/or password");
                this.loggedIn = false;
            }
        }
    }
}
