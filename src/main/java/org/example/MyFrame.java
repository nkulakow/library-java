package org.example;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    String frameName;
    JLabel label = new JLabel();
    ImageIcon image = new ImageIcon("src/main/resources/images/wiatrak.jpg");
    int width;
    int height;
    MyFrame(final int f_width, final int f_height)
    {
        this.frameName = "My beautiful frame";
        this.width = f_width;
        this.height = f_height;
        this.setAttributes();
        this.setLabel();
        this.setVisible(true); //makes frame
        this.add(this.label);
        this.getIcon();
    }

    private void setAttributes()
    {
        this.setSize(this.width, this.height);
        this.setTitle(this.frameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops setVisible command - exit out application
        this.setResizable(true);
        this.setLocation(650,200);
        this.getContentPane().setBackground(new Color(204, 153, 255));
        this.setLayout(null);
    }
    private void getIcon()
    {
        ImageIcon image = new ImageIcon("src/20220715_052950.jpg");
        this.setIconImage(image.getImage());
    }
     private void setLabel()
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
