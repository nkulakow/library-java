package org.example.GUI;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Page extends JFrame implements ActionListener {
    public Page(final int width, final int height) {
        super();
        int x_pos = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
        int y_pos = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
        this.setBounds(x_pos, y_pos, width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("src/main/resources/images/winnie.jpeg");
        this.setIconImage(image.getImage());
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}