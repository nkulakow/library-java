package org.example.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ObjectTable extends JTable {
    public ObjectTable(String[][] data, String[] column_names) {
        super(new DefaultModel(data, column_names));
        this.initVisuals();
    }

    private void initVisuals() {
        this.setFont(ComponentDesigner.getDefaultFont());
        this.setBackground(Color.ORANGE);
        this.setRowHeight(30);
        this.getColumnModel().getColumn(0).setMaxWidth(50);
        this.getTableHeader().setBackground(new Color(179, 122, 82));
        this.getTableHeader().setFont(new Font(Font.SERIF, Font.ITALIC, 20));
    }

    public static class DefaultModel extends DefaultTableModel {
        public DefaultModel(String[][] data, String[] column_names) {
            super(data, column_names);
            this.setRowCount(100);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
