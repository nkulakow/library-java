package org.example.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ObjectTable extends JTable {
    public ObjectTable(String[][] data, String[] column_names) {
        super(new DefaultModel(data, column_names));
        this.initVisuals();
        this.initSelectionModel();
    }

    private void initVisuals() {
        this.setFont(ComponentDesigner.getDefaultFont());
        this.setBackground(Color.ORANGE);
        this.setRowHeight(30);
        this.getColumnModel().getColumn(0).setMaxWidth(50);
        this.getTableHeader().setBackground(new Color(179, 122, 82));
        this.getTableHeader().setFont(new Font(Font.SERIF, Font.ITALIC, 20));
    }

    private void initSelectionModel() {
        var model = this.getSelectionModel();
        model.addListSelectionListener(new UserSelector());
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
