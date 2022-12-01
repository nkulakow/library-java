package org.example;

import javax.swing.JButton;
import  javax.swing.JFrame;
import  javax.swing.JTextField;
import  javax.swing.JPasswordField;
import  javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {
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

    LoginPage(){

        this.loggedIn = false;

        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);
        messageLabel.setBounds(125, 250, 250, 350);

        userIDField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,23);

        loginButton.setBounds(125,200,100,25);
        loginButton.addActionListener(this);

        resetButton.setBounds(225,200,100,25);
        resetButton.addActionListener(this);



        this.add(userIDLabel);
        this.add(userPasswordLabel);
        this.add(messageLabel);
        this.add(userIDField);
        this.add(userPasswordField);
        this.add(loginButton);
        this.add(resetButton);
        this.setSize(600, 500);
        this.setTitle("login page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops setVisible command - exit out application
        this.setResizable(true);
        this.setLocation(650,200);
        this.getContentPane().setBackground(new Color(204, 153, 255));
        this.setLayout(null);
        this.setVisible(true);
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
            String uspassword = userPasswordField.getText();

            if(userID.equals(this.login) && uspassword.equals(this.password))
            {
                this.messageLabel.setText("Logged in");
                this.loggedIn = true;
            }
            else {
                this.messageLabel.setText("Incorrect login and/or password");
            }
        }
    }
}
