/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.detail;

import com.jhw.swing.material.components.button.prepared._buttonAddEdit;
import com.jhw.swing.material.components.container.panel._PanelTransparent;
import com.jhw.swing.material.components.labels._MaterialLabel;
import com.jhw.swing.material.components.searchfield._MaterialSearchField;
import com.jhw.swing.material.standards.MaterialFontRoboto;
import com.jhw.swing.utils.icons.DerivableIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class HeaderDetailPanel extends _PanelTransparent {

    public HeaderDetailPanel() {
        initComponents();
    }

    private void initComponents() {
        labelHeader = new com.jhw.swing.material.components.labels._MaterialLabel();
        searchField = new com.jhw.swing.material.components.searchfield._MaterialSearchField();
        buttonAdd = new com.jhw.swing.material.components.button.prepared._buttonAddEdit();
        panelOptionsExtra = new com.jhw.swing.material.components.container.panel._PanelTransparent();

        panelOptionsExtra.setLayout(new java.awt.GridLayout(1, 0));

        this.setLayout(new BorderLayout());

        //search field
        searchField = new _MaterialSearchField();

        //actions
        _PanelTransparent header = new _PanelTransparent();
        header.setBorder(new EmptyBorder(0, 5, 0, 0));
        header.setLayout(new BorderLayout());
        labelHeader = new _MaterialLabel();
        labelHeader.setIconTextGap(10);
        labelHeader.setFont(MaterialFontRoboto.BOLD.deriveFont(24f));
        header.add(labelHeader, BorderLayout.WEST);

        buttonAdd.isCreated(true);

        _PanelTransparent buttonActions = new _PanelTransparent();
        buttonActions.setLayout(new BorderLayout());
        buttonActions.add(buttonAdd, BorderLayout.EAST);
        buttonActions.add(panelOptionsExtra, BorderLayout.WEST);

        header.add(buttonActions, BorderLayout.EAST);

        //header + search
        _PanelTransparent header2 = new _PanelTransparent();
        header2.setLayout(new BorderLayout());
        header2.add(header, BorderLayout.NORTH);
        header2.add(searchField);

        this.add(header2, BorderLayout.NORTH);
    }

    private com.jhw.swing.material.components.button.prepared._buttonAddEdit buttonAdd;
    private com.jhw.swing.material.components.labels._MaterialLabel labelHeader;
    private com.jhw.swing.material.components.container.panel._PanelTransparent panelOptionsExtra;
    private com.jhw.swing.material.components.searchfield._MaterialSearchField searchField;

    public String getSearchText() {
        return searchField.getSearchField().getText();
    }

    public void setSearchActionListener(ActionListener searchAction) {
        searchField.setSearchActionListener(searchAction);
    }

    public void setHeaderText(String text) {
        this.labelHeader.setText(text);
    }

    public void addButtonNuevoActionListener(ActionListener action) {
        buttonAdd.addActionListener(action);
    }

    public void setSearchFieldVisibile(boolean vis) {
        searchField.setVisible(vis);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (labelHeader != null) {//este if tiene que estar porque se llama al background en el super sin inicializar los componentes y la primera vel el label es null y lanza NullPointerException
            this.labelHeader.setForeground(getForeground());
        }
    }

    public void addOptionElement(Component element) {
        addOptionElement(element, panelOptionsExtra.getComponentCount());
    }

    public void addOptionElement(Component element, int index) {
        int heigth = (int) panelOptionsExtra.getSize().getHeight();
        int width = heigth * panelOptionsExtra.getComponentCount() + 1;
        panelOptionsExtra.setSize(width, heigth);
        panelOptionsExtra.add(element);
    }

    public void setOptionPanelVisibility(boolean visible) {
        buttonAdd.setVisible(visible);
        panelOptionsExtra.setVisible(visible);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        buttonAdd.setEnabled(enabled);
        for (Component component : panelOptionsExtra.getComponents()) {
            component.setEnabled(enabled);
        }
        searchField.setEnabled(enabled);
        this.labelHeader.setEnabled(enabled);
    }

    public _buttonAddEdit getButtonAdd() {
        return buttonAdd;
    }

    public void setButtonAdd(_buttonAddEdit buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    public _MaterialLabel getLabelHeader() {
        return labelHeader;
    }

    public void setLabelHeader(_MaterialLabel labelHeader) {
        this.labelHeader = labelHeader;
    }

    public _PanelTransparent getPanelOptionsExtra() {
        return panelOptionsExtra;
    }

    public void setPanelOptionsExtra(_PanelTransparent panelOptionsExtra) {
        this.panelOptionsExtra = panelOptionsExtra;
    }

    public _MaterialSearchField getSearchField() {
        return searchField;
    }

    public void setSearchField(_MaterialSearchField searchField) {
        this.searchField = searchField;
    }

    public void setIcon(ImageIcon icon) {
        if (icon instanceof DerivableIcon) {
            labelHeader.setIcon(((DerivableIcon) icon).deriveIcon(1.25f * labelHeader.getFont().getSize2D()));
        } else {
            labelHeader.setIcon(icon);
        }
    }

}
