package org.example.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ObjectTable extends JTable {
    public static final int column_user_name = 1;
    public static final int column_user_surname = 2;
    public static final int column_user_login = 3;
    public static final int column_user_mail = 4;

    public static final int column_book_name = 1;
    public static final int column_book_author_ = 2;
    public static final int column_book_category = 3;

    public ObjectTable(String[][] data, String[] column_names, int data_type) {
        super(new DefaultModel(data, column_names));
        this.initVisuals();
        this.changeSelectionModelListener(data_type);
    }

    private void initVisuals() {
        this.setFont(ComponentDesigner.getDefaultFont());
        this.setBackground(Color.ORANGE);
        this.setRowHeight(30);
        this.getColumnModel().getColumn(0).setMaxWidth(50);
        this.getTableHeader().setBackground(new Color(179, 122, 82));
        this.getTableHeader().setFont(new Font(Font.SERIF, Font.ITALIC, 20));
    }

    public void changeSelectionModelListener(int mode) {
        var model = this.getSelectionModel();
        if (mode == FrameContentManager.USERS)
            model.addListSelectionListener(new UserSelector());
        else
            model.addListSelectionListener(new BookSelector());
    }

    public static class DefaultModel extends DefaultTableModel {
        public DefaultModel(String[][] data, String[] column_names) {
            super(data, column_names);
            this.setRowCount(Math.max(10, data.length));
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
