package org.example;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Page extends JFrame implements ActionListener {
    LibraryContext libContext;
    public Page(final int width, final int height, LibraryContext libContext) {
        super();
        this.libContext = libContext;
        int x_pos = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
        int y_pos = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
        this.setBounds(x_pos, y_pos, width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}