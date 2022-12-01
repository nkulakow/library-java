package org.example;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;

public class MyFrame extends JFrame{
    String frameName;
    int width;
    int height;
    final ImageIcon image = new ImageIcon("src/main/resources/images/wiatrak.jpg");
    MyFrame(final int f_width, final int f_height)
    {
        this.frameName = "My beautiful frame";
        this.width = f_width;
        this.height = f_height;
        this.setAttributes();
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
        this.setIconImage(this.image.getImage());
    }

    //jak wyczyścić wszystko: this.removeAll();
}
