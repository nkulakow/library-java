package org.example;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    String frameName;
    MyFrame()
    {
        this.frameName = "My beautiful frame";
        this.setAttributes();
        this.setVisible(true); //makes frame visible
        this.getIcon();
    }

    void setAttributes()
    {
        this.setSize(420, 420);
        this.setTitle(this.frameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops setVisible command - exit out application
        this.setResizable(false);
        this.setLocation(700,200);
        this.getContentPane().setBackground(new Color(204, 153, 255));
    }
    void getIcon()
    {
        ImageIcon image = new ImageIcon("src/20220715_052950.jpg");
        this.setIconImage(image.getImage());
    }
}
