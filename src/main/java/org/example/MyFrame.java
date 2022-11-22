package org.example;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    String frameName;
    JLabel label = new JLabel();
    ImageIcon image = new ImageIcon("src/main/wiatrak.jpg");
    MyFrame()
    {
        this.frameName = "My beautiful frame";
        this.setAttributes();
        this.setLabel();
        this.setVisible(true); //makes frame
        this.add(this.label);
        this.getIcon();
    }

    void setAttributes()
    {
        this.setSize(600, 500);
        this.setTitle(this.frameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops setVisible command - exit out application
        this.setResizable(true);
        this.setLocation(650,200);
        this.getContentPane().setBackground(new Color(204, 153, 255));
        this.setLayout(null);
    }
    void getIcon() {
        this.setIconImage(image.getImage());
    }
     void setLabel()
     {
         this.label.setText("Bruh, do you even code?");
         this.label.setIcon(image);
         this.label.setHorizontalTextPosition(JLabel.CENTER);
         this.label.setVerticalTextPosition(JLabel.BOTTOM);
         this.label.setForeground(new Color(4, 168, 124, 249));
         this.label.setFont(new Font("MV Boli", Font.PLAIN, 20));
         this.label.setIconTextGap(25);
         this.label.setHorizontalAlignment(JLabel.CENTER);
         this.label.setBounds(100,0,400,400);
     }
}
