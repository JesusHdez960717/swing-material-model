/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.root101.swing.models.detail;

import com.root101.clean.core.app.services.NotificationHandler;
import com.root101.clean.core.app.services.NotificationsGeneralType;
import com.root101.clean.core.domain.DomainObject;
import com.root101.swing.material.components.button._MaterialButtonIconTransparent;
import com.root101.swing.material.components.container.panel._MaterialPanel;
import com.root101.swing.material.components.labels.MaterialLabel;
import com.root101.swing.material.components.table.Column;
import com.root101.swing.material.components.table.MaterialTableByPage;
import com.root101.swing.material.components.table.editors_renders.component.*;
import com.root101.swing.material.components.table.editors_renders.header.HeaderCellRender;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.root101.security.SHA;
import com.root101.utils.interfaces.Update;
import com.root101.swing.material.standards.MaterialShadow;
import java.awt.BorderLayout;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.border.EmptyBorder;
import com.root101.swing.material.components.table.TableColumnAdjuster;
import com.root101.utils.refraction.FiltrableRefraction;
import javax.swing.ImageIcon;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class _MaterialPanelDetail<T extends DomainObject> extends _MaterialPanel implements Update {

    private final String modelColumnName = "_" + SHA.hash256(String.valueOf(new SecureRandom().nextLong())) + "_";
    private final String actionsColumnName = "_" + SHA.hash256(String.valueOf(new SecureRandom().nextLong())) + "_";

    private final _MaterialPanelActions.builder builder = new _MaterialPanelActions.builder();
    protected final List<T> list = new ArrayList<>();

    private TableColumnAdjuster adjuster;

    private boolean adjustColumns = false;

    public _MaterialPanelDetail() {
        this(new Column[]{});
    }

    public _MaterialPanelDetail(Column... arr) {
        initComponents();
        setColumns(arr);
        addListeners();
    }

    private void initComponents() {
        this.setBorder(new EmptyBorder(
                MaterialShadow.OFFSET_TOP + 10,
                MaterialShadow.OFFSET_LEFT + 10,
                MaterialShadow.OFFSET_BOTTOM + 10,
                MaterialShadow.OFFSET_RIGHT + 10));

        this.setLayout(new BorderLayout());

        header = new HeaderDetailPanel();
        table = new com.root101.swing.material.components.table._MaterialTableByPage();
        table.setBorder(new EmptyBorder(0, 5, 0, 5));

        this.add(header, BorderLayout.NORTH);
        this.add(table);
    }

    protected HeaderDetailPanel header;
    protected MaterialTableByPage table;

    public void setIcon(ImageIcon icon) {
        header.setIcon(icon);
    }

    public boolean isAdjustColumns() {
        return adjustColumns;
    }

    public void setAdjustColumns(boolean adjustColumns) {
        this.adjustColumns = adjustColumns;
    }

    public String getHeaderText() {
        return header.getHeaderText();
    }

    public void setHeaderText(String text) {
        header.setHeaderText(text);
    }

    public void addButtonNuevoActionListener(ActionListener action) {
        header.addButtonNuevoActionListener(action);
    }

    public void addTableDoubleClickAction(MouseAdapter action) {
        table.addMouseListener(action);
    }

    private void addListeners() {
        header.getButtonAdd().addActionListener((java.awt.event.ActionEvent evt) -> {
            buttonNuevoActionListener();
        });

        table.getTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onTableMouseDoubleClicked(evt);
            }
        });

        header.getSearchField().setSearchActionListener((java.awt.event.ActionEvent evt) -> {
            setCollection(new ArrayList<>(list));
        });

        table.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                onTableKeyTyped(evt);
            }
        });
    }

    private void onTableKeyTyped(KeyEvent evt) {
        header.getSearchField().clear(evt);
    }

    public void setSearchFieldVisibile(boolean vis) {
        header.setSearchFieldVisibile(vis);
    }

    protected abstract void buttonNuevoActionListener();

    protected void tableDoubleCickMouseListener(T obj) {
        editAction(obj);
    }

    public abstract Object[] getRowObject(T obj);

    /**
     * Siempre recive un T obj != null
     *
     * @param obj
     * @return
     */
    protected abstract T deleteAction(T obj);

    /**
     * Siempre recive un T obj != null
     *
     * @param obj
     */
    protected abstract void editAction(T obj);

    /**
     * Siempre recive un T obj != null
     *
     * @param obj
     */
    protected abstract void viewAction(T obj);

    private void deleteActionInternal() {
        try {
            T before = getSelectedElement();
            if (before != null) {
                int oldRow = Math.max(0, Math.min(table.getSelectedRow(), table.getRowCount() - 2));//-1 para ajustar al 0 y -1 por eliminar el ultimo

                if (NotificationHandler.showConfirmDialog(NotificationsGeneralType.CONFIRM_DELETE, before)) {
                    T after = deleteAction(before);
                    if (after != null) {
                        //si se elimina el ultimo deje de editar xq si no lanza excepcion x editar un index que no existe
                        if (table.getTable().getCellEditor() != null) {//es null si se elimina con el click derecho
                            table.getTable().getCellEditor().cancelCellEditing();//cancel para que NO acepte ningun valor parcialmente editado
                        }
                        NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_DELETE, after);
                        update();

                        //para que se mantenga el ultimo seleccionado
                        table.getTable().setRowSelectionInterval(oldRow, oldRow);
                    }
                }

            }
        } catch (Exception ex) {
            System.out.println("Excepcion en el deleteAction " + ex.getMessage());
        }
    }

    public void setColumns(Column... arr) {
        Column columnsReal[] = new Column[arr.length + 2];
        columnsReal[0] = Column.builder().name(modelColumnName).build();
        columnsReal[columnsReal.length - 1] = Column.builder().name(actionsColumnName).editable(true).build();

        for (int i = 0; i < arr.length; i++) {
            columnsReal[i + 1] = arr[i];
        }

        String columnNames[] = new String[arr.length + 2];
        columnNames[0] = modelColumnName;
        columnNames[columnNames.length - 1] = actionsColumnName;
        for (int i = 0; i < arr.length; i++) {
            columnNames[i + 1] = arr[i].getColumnName();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(
                columnNames, 0
        ) {
            Column array[] = columnsReal;

            @Override
            public Class getColumnClass(int columnIndex) {
                return array[columnIndex].getColumnsClass();
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return array[columnIndex].isEditable();
            }
        });

        table.getColumn(modelColumnName).setMinWidth(0);
        table.getColumn(modelColumnName).setPreferredWidth(0);
        table.getColumn(modelColumnName).setMaxWidth(0);

        setActionColumnVisivility(true);

        for (int i = 0; i < arr.length; i++) {
            table.getColumnModel().getColumn(i + 1).setPreferredWidth(arr[i].getPreferedWidth());
        }
        configureTable();
    }

    public T getSelectedElement() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return (T) table.getValueAt(row, 0);
    }

    private void onTableMouseDoubleClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {//double click en la fila
            tableDoubleCickMouseListener(getSelectedElement());
        }
    }

    public List<T> getSelectedList() {
        List<T> answ = new ArrayList<>();
        for (int selectedRow : getTable().getSelectedRows()) {
            answ.add((T) getTable().getValueAt(selectedRow, 0));
        }
        return answ;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (header != null) {
            header.setBackground(bg);
        }
    }

    public void clearTable() {
        DefaultTableModel model = table.getModel();
        model.setRowCount(0);
    }

    public void setCollection(List<T> list) {
        this.list.clear();
        addCollection(list);
    }

    public void addCollection(List<T> list) {
        this.list.addAll(list);
        table.clear();
        for (int i = 0; i < this.list.size(); i++) {
            addRow(this.list.get(i));
        }
        adjustColumns();
    }

    public void addObject(T object) {
        this.list.add(object);
        addRow(object);
    }

    public List<T> getList() {
        return list;
    }

    public void removeRow(int row) {
        table.removeRow(row);
    }

    private void addRow(T object) {
        if (contain(FiltrableRefraction.toFullString(object), header.getSearchText())) {
            table.addRow(getObjectRow(object));
        }
    }

    private Object[] getObjectRow(T object) {
        Object obj[] = getRowObject(object);
        Object row[] = new Object[obj.length + 2];
        row[0] = object;
        row[row.length - 1] = builder.build();
        System.arraycopy(obj, 0, row, 1, obj.length);
        return row;
    }

    private boolean contain(String text, String key) {
        if (key.isEmpty()) {
            return true;
        }
        text = text.toLowerCase();
        StringTokenizer st = new StringTokenizer(key, "+");
        while (st.hasMoreTokens()) {
            String tok = st.nextToken().toLowerCase();
            if (!text.contains(tok)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.modelColumnName);
        hash = 47 * hash + Objects.hashCode(this.header);
        hash = 47 * hash + Objects.hashCode(this.table);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final _MaterialPanelDetail<?> other = (_MaterialPanelDetail<?>) obj;
        if (!Objects.equals(this.modelColumnName, other.modelColumnName)) {
            return false;
        }
        if (!Objects.equals(this.table, other.table)) {
            return false;
        }
        return true;
    }

    private void configureTable() {
        ComponentCellRender action = new ComponentCellRender();
        table.getTable().getColumn(actionsColumnName).setCellRenderer(action);
        table.getTable().getColumn(actionsColumnName).setCellEditor(new ComponentCellEditor(action));

        table.getTable().getColumn(actionsColumnName).setHeaderRenderer(new HeaderCellRender());
        createBuilder();

        adjuster = new TableColumnAdjuster(getTable());
    }

    private void createBuilder() {
        builder.deleteAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteActionInternal();
            }
        });

        builder.editAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    T element = getSelectedElement();
                    if (element != null) {
                        editAction(element);
                    }
                } catch (Exception ex) {
                    System.out.println("Excepcion en el editAction " + ex.getMessage());
                }
            }
        });

        builder.viewAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    T element = getSelectedElement();
                    if (element != null) {
                        viewAction(element);
                    }
                } catch (Exception ex) {
                    System.out.println("Excepcion en el viewAction " + ex.getMessage());
                }
            }
        });
    }

    public void setActionColumnVisivility(boolean b) {
        int size = b ? table.getRowHeight() : 0;
        int width = builder.getComponents() * size;
        table.getColumn(actionsColumnName).setMinWidth(width);
        table.getColumn(actionsColumnName).setPreferredWidth(width);
        table.getColumn(actionsColumnName).setMaxWidth(width);

        //set up popup
        if (b) {
            table.getTable().setComponentPopupMenu(builder.builPopup());
        } else {
            table.getTable().setComponentPopupMenu(null);
        }
    }

    public void setActionColumnButtonsVisivility(boolean delete, boolean edit, boolean view) {
        builder.buttonsVisibility(delete, edit, view);
        setActionColumnVisivility(true);
    }

    public void addOptionElement(Component element) {
        header.addOptionElement(element);
    }

    public void addOptionElement(Action action) {
        _MaterialButtonIconTransparent btn = new _MaterialButtonIconTransparent();
        btn.setAction(action);
        addOptionElement(btn);
    }

    public void addOptionElement(Component element, int index) {
        header.addOptionElement(element, index);
    }

    public void setOptionPanelVisibility(boolean visible) {
        header.setOptionPanelVisibility(visible);
    }

    public void setButtonAddVisibility(boolean visible) {
        header.setButtonAddVisibility(visible);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        header.setEnabled(enabled);
        table.setEnabled(enabled);
    }

    public void addActionExtra(Action c) {
        builder.extra(c);
        setActionColumnVisivility(true);
    }

    public JTable getTable() {
        return table.getTable();
    }

    public String getModelColumnName() {
        return modelColumnName;
    }

    public MaterialLabel getLabelHeader() {
        return header.getLabelHeader();
    }

    public MaterialTableByPage getTableByPage() {
        return table;
    }

    private void adjustColumns() {
        if (adjustColumns) {
            for (int i = getTable().getColumnCount() - 2; i > 0; i--) {
                adjuster.adjustColumn(i);
            }
        }
    }

    public String[] getColumnNames() {
        String[] cols = new String[table.getTable().getColumnCount() - 2];
        for (int i = 0; i < cols.length; i++) {
            cols[i] = table.getTable().getColumnName(i + 1);
        }
        return cols;
    }
}
