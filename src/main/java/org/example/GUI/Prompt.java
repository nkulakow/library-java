package org.example.GUI;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Prompt extends JFrame implements ActionListener {
    public static final Dimension size = new Dimension(400, 200);
    @Getter @Setter
    private JLabel label;
    private SpringLayout layout;

    public Prompt() {
        super();
        this.setSize(Prompt.size);
        this.setResizable(false);
        this.getContentPane().setBackground(ComponentDesigner.BACKGROUND_COLOR);
        this.layout = new SpringLayout();
        this.setLayout(this.layout);
        this.initLabel();
        this.initButton();
        this.center();
    }

    private void initLabel() {
        this.label = ComponentDesigner.makeDefaultLabel("", 25);
        this.add(this.label);
        this.label.setVerticalAlignment(SwingConstants.CENTER);
        this.label.setHorizontalAlignment(SwingConstants.CENTER);

        this.layout.putConstraint(SpringLayout.NORTH, this.label, 10, SpringLayout.NORTH, this.getContentPane());
        this.layout.putConstraint(SpringLayout.SOUTH, this.label, 50, SpringLayout.NORTH, this.getContentPane());
        this.layout.putConstraint(SpringLayout.WEST, this.label, 0, SpringLayout.WEST, this.getContentPane());
        this.layout.putConstraint(SpringLayout.EAST, this.label, 0, SpringLayout.EAST, this.getContentPane());
    }

    private void initButton() {
        ComponentDesigner.OptionButton button = ComponentDesigner.makeOptionButton("OK");
        button.addActionListener(this);
        this.add(button);

        this.layout.putConstraint(SpringLayout.NORTH, button, -70, SpringLayout.SOUTH, this.getContentPane());
        this.layout.putConstraint(SpringLayout.SOUTH, button, -20, SpringLayout.SOUTH, this.getContentPane());
        this.layout.putConstraint(SpringLayout.WEST, button, -75, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
        this.layout.putConstraint(SpringLayout.EAST, button, 75, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
    }

    public void center() {
        int y, x;
        x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getWidth() /2;
        y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getHeight() / 2;
        this.setBounds(x, y, this.getWidth(), this.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}
