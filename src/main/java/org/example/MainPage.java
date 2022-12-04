package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class MainPage extends Page {
    private JPanel options;
    public MainPage () {
        super(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
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
        this.options = new OptionPanel(new Vector<String>(List.of(option_text)));
        this.add(options, BorderLayout.WEST);
    }

    private void initBottom() {
        this.add(new BottomPanel(), BorderLayout.SOUTH);
    }

    private void initCenter() {
        this.add(new CenterPannel(), BorderLayout.CENTER);
    }
}

class OptionPanel extends JPanel {
    private Vector<String> options_text;
    private Vector<JButton> options;

    public OptionPanel(final Vector<String> foptions) {
        this.options_text = foptions;
        this.options = new Vector<JButton>();

        for(String option : this.options_text) {
            var button = new JButton(option);
            button.setBackground(new Color(179, 122, 82));
            button.setForeground(new Color(60,60 ,60));
            button.setFont(new Font(Font.SERIF, Font.ITALIC, 30));
            button.setFocusPainted(false);
            button.setHorizontalAlignment(JButton.CENTER);
            this.options.add(button);
            this.add(button);
        }
        this.setLayout(new GridLayout(options.size(), 1));
    }
}

class BottomPanel extends JPanel {
    public BottomPanel() {
        this.setBackground(new Color(94, 94, 94));
        this.setPreferredSize(new Dimension(100, 200));
    }
}

class CenterPannel extends JPanel {
    public CenterPannel() {
        this.setBackground(new Color(233, 221, 235));
        this.setPreferredSize(new Dimension(100, 200));
    }
}