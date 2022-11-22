package org.example;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    String frameName;
    int width;
    int height;
    MyFrame(final int f_width, final int f_height)
    {
        this.frameName = "My beautiful frame";
        this.width = f_width;
        this.height = f_height;
        this.setAttributes();
        this.setVisible(true); //makes frame visible
        this.getIcon();
    }

    private void setAttributes()
    {
        this.setSize(this.width, this.height);
        this.setTitle(this.frameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops setVisible command - exit out application
        this.setResizable(true);
        this.setLocation(700,200);
        this.getContentPane().setBackground(new Color(204, 153, 255));
    }
    private void getIcon()
    {
        ImageIcon image = new ImageIcon("src/20220715_052950.jpg");
        this.setIconImage(image.getImage());
    }


}