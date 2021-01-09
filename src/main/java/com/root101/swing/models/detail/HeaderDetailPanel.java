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

import com.root101.module.util.personalization.services.PersonalizationHandler;
import com.root101.swing.material.components.button.MaterialButton;
import com.root101.swing.material.components.container.MaterialContainersFactory;
import com.root101.swing.material.components.container.panel._PanelTransparent;
import com.root101.swing.material.components.labels.MaterialLabel;
import com.root101.swing.material.components.labels.MaterialLabelsFactory;
import com.root101.swing.material.components.searchfield.MaterialSearchField;
import com.root101.swing.material.components.searchfield._MaterialSearchField;
import com.root101.swing.material.standards.MaterialFontRoboto;
import com.root101.swing.models.utils.PersonalizationModel;
import com.root101.swing.prepared.button.MaterialButtonAddEdit;
import com.root101.swing.prepared.button.MaterialPreparedButtonsFactory;
import com.root101.swing.derivable_icons.DerivableIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class HeaderDetailPanel extends _PanelTransparent {

    public HeaderDetailPanel() {
        initComponents();
    }

    private void initComponents() {
        panelOptionsExtra = MaterialContainersFactory.buildPanelTransparent();

        panelOptionsExtra.setLayout(new java.awt.GridLayout(1, 0));

        this.setLayout(new BorderLayout());

        //search field
        searchField = _MaterialSearchField.from();

        //actions
        _PanelTransparent header = new _PanelTransparent();
        header.setBorder(new EmptyBorder(0, 5, 0, 0));
        header.setLayout(new BorderLayout());

        labelHeader = MaterialLabelsFactory.build();
        labelHeader.setIconTextGap(10);
        labelHeader.setFont(MaterialFontRoboto.BOLD.deriveFont(24f));
        header.add(labelHeader, BorderLayout.WEST);

        buttonAdd = MaterialPreparedButtonsFactory.buildAddEdit();
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

    private MaterialLabel labelHeader;
    private MaterialSearchField searchField;
    private MaterialButtonAddEdit buttonAdd;
    private JPanel panelOptionsExtra;

    public String getSearchText() {
        return searchField.getSearchField().getText();
    }

    public void setSearchActionListener(ActionListener searchAction) {
        searchField.setSearchActionListener(searchAction);
    }

    public void setHeaderText(String text) {
        this.labelHeader.setText(text);
    }

    public String getHeaderText() {
        return this.labelHeader.getText();
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
        addOptionElement(element, 0/*, panelOptionsExtra.getComponentCount()*/);
    }

    public void addOptionElement(Component element, int index) {
        int heigth = (int) panelOptionsExtra.getSize().getHeight();
        int width = heigth * panelOptionsExtra.getComponentCount() + 1;
        element.setPreferredSize(new Dimension((int) element.getPreferredSize().getWidth(), heigth));
        panelOptionsExtra.setSize(width, heigth);
        panelOptionsExtra.add(element, index);
    }

    public void setOptionPanelVisibility(boolean visible) {
        buttonAdd.setVisible(visible);
        panelOptionsExtra.setVisible(visible);
    }

    public void setButtonAddVisibility(boolean visible) {
        buttonAdd.setVisible(visible);
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

    public MaterialButton getButtonAdd() {
        return buttonAdd;
    }

    public MaterialLabel getLabelHeader() {
        return labelHeader;
    }

    public void setLabelHeader(MaterialLabel labelHeader) {
        this.labelHeader = labelHeader;
    }

    public JPanel getPanelOptionsExtra() {
        return panelOptionsExtra;
    }

    public void setPanelOptionsExtra(JPanel panelOptionsExtra) {
        this.panelOptionsExtra = panelOptionsExtra;
    }

    public MaterialSearchField getSearchField() {
        return searchField;
    }

    public void setSearchField(MaterialSearchField searchField) {
        this.searchField = searchField;
    }

    public void setIcon(ImageIcon icon) {
        if (!PersonalizationHandler.getBoolean(PersonalizationModel.KEY_SHOW_ICON_DETAIL)) {
            return;
        }
        if (icon instanceof DerivableIcon) {
            labelHeader.setIcon(((DerivableIcon) icon).deriveIcon(1.25f * labelHeader.getFont().getSize2D()));
        } else {
            labelHeader.setIcon(icon);
        }
    }

}
