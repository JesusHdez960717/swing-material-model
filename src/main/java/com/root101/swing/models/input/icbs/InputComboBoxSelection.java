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
package com.root101.swing.models.input.icbs;

import com.root101.clean.core.app.services.ExceptionHandler;
import com.root101.swing.material.components.combobox.MaterialComboBoxDefinition;
import com.root101.swing.material.components.combobox.MaterialComboBoxFactory;
import com.root101.swing.material.components.combobox.MaterialComboBoxIcon;
import com.root101.swing.material.effects.Iconable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class InputComboBoxSelection<T> extends InputGeneralSelection<T, MaterialComboBoxIcon<T>> implements Iconable, MaterialComboBoxDefinition<T> {

    public InputComboBoxSelection() {
        super(MaterialComboBoxFactory.buildIcon());
        addListeners();
    }

    private void addListeners() {
        addActionListener((ActionEvent e) -> {
            clearWrong();
        });
    }

    public void addActionListener(ActionListener listener) {
        getComponent().getComboBox().addActionListener(listener);
    }

    @Override
    public void update() {
        setUpList(getList());
    }

    @Override
    public void addElement(T element) {
        getComponent().getComboBox().addElement(element);
    }

    /**
     * Metodo a reimplementar si se quiere personalizar la manera en que se pone
     * la lista en el combo box
     *
     * @param l
     * @throws RuntimeException
     */
    protected void setUpList(List<T> l) throws RuntimeException {
        getComponent().setModel(l);
    }

    @Override
    public void setModel(ComboBoxModel<T> cbm) {
        getComponent().setModel(cbm);
    }

    @Override
    public void paintLine(Graphics grphcs) {
        getComponent().paintLine(grphcs);
    }

    @Override
    public int getYLine(Graphics grphcs) {
        return getComponent().getYLine(grphcs);
    }

    @Override
    public Color getAccentFloatingLabel() {
        return getComponent().getAccentFloatingLabel();
    }

    @Override
    public void setAccentFloatingLabel(Color color) {
        getComponent().setAccentFloatingLabel(color);
    }

    @Override
    public String getLabel() {
        return getComponent().getLabel();
    }

    @Override
    public void setLabel(String string) {
        getComponent().setLabel(string);
        getComponent().setHint("Seleccione " + string.toLowerCase() + "...");
    }

    @Override
    public String getHint() {
        return getComponent().getHint();
    }

    @Override
    public void setHint(String string) {
        getComponent().setHint(string);
    }

    @Override
    public void paintLabel(Graphics grphcs) {
        getComponent().paintLabel(grphcs);
    }

    @Override
    public void paintHint(Graphics grphcs) {
        getComponent().paintHint(grphcs);
    }

    @Override
    public void setIcon(Icon icon) {
        getComponent().setIcon(icon);
    }

    @Override
    public Icon getIcon() {
        return getComponent().getIcon();
    }

}
