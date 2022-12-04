package org.example.GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

public class MainPage extends Page {
    private OptionPanel options;
    public MainPage () {
        super(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - 55);
        this.init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.initOptions();
        this.initBottom();
        this.initCenter();
    }

    private void initOptions() {
        String[]  option_text = {
                "Search in database",
                "Modify database",
                "Show my account",
        };
        this.options = new OptionPanel(new Vector<>(List.of(option_text)));
        for (var option : this.options.getOptions()) {
            option.addActionListener(this);
        }
        this.add(options, BorderLayout.WEST);
    }

    private void initBottom() {
        this.add(new BottomPanel(), BorderLayout.SOUTH);
    }

    private void initCenter() {
        this.add(new CenterPannel(), BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof OptionPanel.OptionButton button) {
            switch (button.getActionType()) {
                case OptionPanel.OptionButton.SEARCH_IN_DATABASE:
                    System.out.println("Search...");
                    break;
                case OptionPanel.OptionButton.MODIFY_DATABASE:
                    System.out.println("Modify...");
                    break;
                case OptionPanel.OptionButton.SHOW_ACCOUNT:
                    System.out.println("Show...");
                default:
                    break;
            }
        }
    }
}

class OptionPanel extends JPanel {
    final private Vector<String> options_text;
    private Vector<OptionButton> options;

    public OptionPanel(final Vector<String> foptions) {
        this.options_text = foptions;
        this.setLayout(null);
        this.setBackground(Color.ORANGE);
        this.setPreferredSize(new Dimension(250, 400));
        this.initOptions();
    }

    private void initOptions() {
        this.options = new Vector<>();
        int i = 0;
        for(String option : this.options_text) {
            OptionButton button = new OptionButton(option);
            button.setBounds(0, 55 * i, 250, 50);
            this.options.add(button);
            this.add(button);
            i++;
        }
        this.options.get(0).setActionType(OptionButton.SEARCH_IN_DATABASE);
        this.options.get(1).setActionType(OptionButton.MODIFY_DATABASE);
        this.options.get(2).setActionType(OptionButton.SHOW_ACCOUNT);
    }

    public Vector<OptionButton> getOptions() {
        return this.options;
    }

    static class OptionButton extends JButton {
        private int action;

        public final static int MODIFY_DATABASE = 0;
        public final static int SEARCH_IN_DATABASE = 1;
        public final static int SHOW_ACCOUNT = 2;

        OptionButton(final String text) {
            super(text);
            this.setBackground(new Color(179, 122, 82));
            this.setForeground(new Color(60,60 ,60));
            this.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
            this.setFocusPainted(false);
            this.setHorizontalAlignment(JButton.CENTER);
            this.setBorder(new LineBorder(Color.BLACK));
        }

        public int getActionType() {
            return this.action;
        }

        public void setActionType(final int new_action) {
            this.action = new_action;
        }
    }
}

class BottomPanel extends JPanel {
    public BottomPanel() {
        this.setBackground(new Color(94, 94, 94));
        this.setPreferredSize(new Dimension(100, 100));
    }
}

class CenterPannel extends JPanel {
    public CenterPannel() {
        this.setBackground(new Color(233, 221, 235));
        this.setPreferredSize(new Dimension(100, 200));
    }
}