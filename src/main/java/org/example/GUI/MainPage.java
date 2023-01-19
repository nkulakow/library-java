package org.example.GUI;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {
    @Getter
    private JPanel content_panel;

    @Getter @Setter
    private JScrollPane table_pane;
    @Getter @Setter
    private JTextField search_field;
    @Getter @Setter
    private ObjectTable search_table;
    @Getter
    private JPanel left_panel;
    public MainPage() {
        super();
        this.setSize(1400, 800);
        this.center();
        this.initLayout();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(340, 450));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void showContent(int mode) {
        if(mode == LibraryGUI.ADMIN) {
            this.initAdminLeft();
            this.initAdminContent();
        } else if (mode == LibraryGUI.USER) {
            this.initUserLeft();
            this.initUserContent();
        }
    }

    public void center() {
        int y, x;
        x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getWidth() /2;
        y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getHeight() / 2;
        this.setBounds(x, y, this.getWidth(), this.getHeight());
    }

    private void initLayout() {
        this.setLayout(new BorderLayout());
    }

    private void initAdminLeft() {
        var left_panel = ComponentDesigner.makeLeftAdminPanel();
        this.add(left_panel, BorderLayout.WEST);
    }

    private void initAdminContent() {
        this.content_panel = ComponentDesigner.makeAdminContentPanel();
        this.content_panel.setPreferredSize(new Dimension(900, 600));
        var scroll_pane = new JScrollPane();
        scroll_pane.add(this.content_panel);
        scroll_pane.setViewportView(this.content_panel);
        scroll_pane.setOpaque(false);
        scroll_pane.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 3, Color.BLACK));
        this.add(scroll_pane, BorderLayout.CENTER);
    }

    private void initUserLeft() {
        this.left_panel = ComponentDesigner.makeLeftUserPanel();
        this.add(this.left_panel, BorderLayout.WEST);
    }

    private void initUserContent() {
        this.content_panel = ComponentDesigner.makeUserContentPanel();
        this.content_panel.setPreferredSize(new Dimension(900, 600));
        var scroll_pane = new JScrollPane();
        scroll_pane.add(this.content_panel);
        scroll_pane.setViewportView(this.content_panel);
        scroll_pane.setOpaque(false);
        scroll_pane.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 3, Color.BLACK));
        this.add(scroll_pane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof ComponentDesigner.OptionButton button) {
            button.getAction_manager().manage();
        }
    }
}
