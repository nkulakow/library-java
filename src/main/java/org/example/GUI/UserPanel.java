package org.example.GUI;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Vector;

class UserOptionPanel extends JPanel {
    public static final int MAIN = 0;
    public static final int NOT_MAIN = 1;
    final private Vector<String> options_text;
    private Vector<JButton> options;
    public final int LENGTH;

    public static final int MARGIN_SIZE = 5;

    public UserOptionPanel(final Vector<String> foptions, final int option_type) {
        LENGTH = foptions.size();
        this.options_text = foptions;
        this.setLayout(null);
        this.setBackground(Color.ORANGE);
        this.setPreferredSize(new Dimension(250, 400));
        this.initOptions(option_type);
    }

    private void initOptions(final int option_type) {
        this.options = new Vector<>();
        int i = 0;
        if(option_type == OptionPanel.MAIN) {
            for(String option : this.options_text) {
                MainOptionButton button = new MainOptionButton(option);
                button.setBounds(0, (OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * i, OptionButton.BUTTON_WIDTH, OptionButton.BUTTON_HEIGHT);
                this.options.add(button);
                this.add(button);
                i++;
            }
            MainOptionButton but;
            but = (MainOptionButton) this.options.get(0);
            but.setAction_type(MainOptionButton.SEARCH_IN_DATABASE);
            but = (MainOptionButton) this.options.get(1);
            but.setAction_type(MainOptionButton.MODIFY_DATABASE);
            but = (MainOptionButton) this.options.get(2);
            but.setAction_type(MainOptionButton.SHOW_ACCOUNT);
            but = (MainOptionButton) this.options.get(3);
            but.setAction_type(MainOptionButton.SHOW_BOOKS);
            but = (MainOptionButton) this.options.get(4);
            but.setAction_type(MainOptionButton.EXIT);
        } else {
            for(String option : this.options_text) {
                OptionButton button = new OptionButton(option);
                button.setBounds(0, (OptionButton.BUTTON_HEIGHT + OptionPanel.MARGIN_SIZE) * i, OptionButton.BUTTON_WIDTH, OptionButton.BUTTON_HEIGHT);
                this.options.add(button);
                this.add(button);
                i++;
            }
        }
    }

    public Vector<JButton> getOptions() {
        return this.options;
    }

    static class OptionButton extends JButton {
        public final static int BUTTON_WIDTH = 250;
        public final static int BUTTON_HEIGHT = 50;

        @Getter
        @Setter
        private FrameContentManager action_manager;

        OptionButton(final String text) {
            super(text);
            OptionPanel.initButtonVisuals(this);
            this.action_manager = new MockManager();
        }
    }

    static class MainOptionButton extends JButton {
        public static final int SEARCH_IN_DATABASE = 0;
        public static final int MODIFY_DATABASE = 1;
        public static final int SHOW_ACCOUNT = 2;
        public static final int SHOW_BOOKS = 3;
        public static final int EXIT = 4;
        @Getter @Setter
        private int action_type;
        MainOptionButton(final String text) {
            super(text);
            OptionPanel.initButtonVisuals(this);
        }
    }
}




