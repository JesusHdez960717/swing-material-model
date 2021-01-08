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
package com.root101.swing.models.TEST.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <T>
 */
public abstract class DefaultAbstractTableModel<T> extends AbstractTableModel {

    protected List<T> items;
    protected final JTable table;
    private DefaultTableRowFilter filter;
    private TableRowSorter<DefaultAbstractTableModel<T>> sorter;
    protected boolean totalRowShowing = false;
    protected int columnTotal = -1;

    public DefaultAbstractTableModel(JTable table) {
        this(new ArrayList<>(), table);
    }

    public DefaultAbstractTableModel(List<T> items, JTable table) {
        this.items = items;
        this.table = table;
        initFilterAndSorter();
    }

    private void initFilterAndSorter() {
        sorter = new TableRowSorter<>(this);
        filter = new DefaultTableRowFilter();
        sorter.setRowFilter(filter);
        table.setRowSorter(sorter);
    }

    /**
     * Shows the rows that contains the string passed by parameter this method
     * is case sensitive
     *
     * @param s the string to search in the table
     */
    public void filterByString(String s) {
        filter.setSearchParam(s);
        sort();

    }

    /**
     * Sorts and filters the rows in the view based on the sort keys of the
     * columns currently being sorted and the filter, if any, associated with
     * this sorter. An empty sortKeys list indicates that the view should
     * unsorted, the same as the model.
     */
    public void sort() {
        sorter.sort();
    }

    @Override
    public int getRowCount() {
        return totalRowShowing ? items.size() + 1 : items.size();
    }

    @Override
    public abstract int getColumnCount();

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    @Override
    public abstract String getColumnName(int column);

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    public T getObjectAt(int rowIndex) {
        return items.get(rowIndex);
    }

    public T getObjectAtSelectedRow() {
        if (table.getSelectedRow() == -1) {
            return null;
        }
        return items.get(table.convertRowIndexToModel(table.getSelectedRow()));
    }

    public T removeObjectAtSelectedRow() {
        if (table.getSelectedRow() == -1) {
            throw new NullPointerException(table.getParent().toString());
        }
        int itemToDelete = table.convertRowIndexToModel(table.getSelectedRow());
        T deleted = items.remove(itemToDelete);
        fireTableRowsDeleted(itemToDelete, itemToDelete);
        return deleted;
    }

    public void addObject(T object) {
        if (items.indexOf(object) != -1) {
            throw new NullPointerException(table.getParent().toString());
        }
        items.add(object);
        fireTableRowsInserted(items.indexOf(object), items.indexOf(object));
    }

    public void removeObject(T object) {
        int index = items.indexOf(object);
        items.remove(object);
        fireTableRowsDeleted(index, index);
    }

    public List<T> getItems() {
        return items;
    }

    public DefaultTableRowFilter getFilter() {
        return filter;
    }

    public void setItems(List<T> items) {
        this.items = items;
        fireTableDataChanged();
    }

    public void addTotalRow(int column) {
        columnTotal = column;
        totalRowShowing = true;
        fireTableDataChanged();
    }

    public void removeTotalRow() {
        totalRowShowing = false;
        columnTotal = -1;
        fireTableDataChanged();
    }

    /**
     * Implementado por default porque no todas las tablas usan la columna del
     * total
     *
     * @return el total a mostrar
     */
    protected float calcularTotal() {
        if (!totalRowShowing || columnTotal == -1) {
            return 0;
        }
        return 0;
    }

    public TableRowSorter<DefaultAbstractTableModel<T>> getSorter() {
        return sorter;
    }

    public class DefaultTableRowFilter extends RowFilter<AbstractTableModel, Integer> {

        private String searchParam;

        public DefaultTableRowFilter() {
            searchParam = "";
        }

        public void setSearchParam(String searchParam) {
            this.searchParam = searchParam;
        }

        @Override
        public boolean include(RowFilter.Entry<? extends AbstractTableModel, ? extends Integer> entry) {
            if (searchParam.isEmpty()) {
                return true;
            }
            for (int i = entry.getValueCount() - 1; i >= 0; i--) {
                if (entry.getStringValue(i).toLowerCase().contains(searchParam.toLowerCase())) {
                    return true;

                }
            }
            return false;
        }

    }

}
