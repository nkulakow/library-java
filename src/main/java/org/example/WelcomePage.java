package org.example;

import  javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class WelcomePage extends MyFrame{
    JLabel label = new JLabel();

    WelcomePage(int f_width, int f_height) {
        super(f_width, f_height);
        this.add(this.label);
        this.setLabel();
    }


    private void setLabel()
    {
        this.label.setText("Bruh, do you even code?");
        this.label.setIcon(image);
        this.label.setHorizontalTextPosition(JLabel.CENTER);
        this.label.setVerticalTextPosition(JLabel.BOTTOM);
        this.label.setForeground(new Color(4, 168, 124, 249));
        this.label.setFont(new Font("Sherif", Font.PLAIN, 20));
        this.label.setIconTextGap(25);
        this.label.setHorizontalAlignment(JLabel.CENTER);
        this.label.setBounds(100,0,400,400);
    }
}
