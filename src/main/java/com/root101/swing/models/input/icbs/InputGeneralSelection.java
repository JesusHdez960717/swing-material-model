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

import com.jhw.module.util.personalization.core.domain.Personalization;
import com.jhw.module.util.personalization.services.PersonalizationHandler;
import com.root101.swing.material.components.button.MaterialButtonIcon;
import com.root101.swing.material.components.button.MaterialButtonsFactory;
import com.root101.swing.material.components.container.panel._PanelTransparent;
import com.root101.swing.material.effects.Wrong;
import com.root101.swing.models.input.dialogs.DialogInputCBS;
import com.root101.swing.models.input.panels.ModelPanel;
import com.root101.swing.util.interfaces.BindableComponent;
import com.root101.utils.interfaces.Update;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class InputGeneralSelection<T, Y extends JComponent & BindableComponent<T> & Wrong> extends _PanelTransparent implements BindableComponent<T>, Update, Wrong, PropertyChangeListener {

    public InputGeneralSelection(Y component) {
        this.component = component;
        initComponents();
        addListeners();

        clearWrong();
        addPropertyChange();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.add(component);

        buttonNuevo = MaterialButtonsFactory.buildIconTransparent();
        buttonNuevo.setForeground(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD));
        buttonNuevo.setRippleColor(Color.black);
        buttonNuevo.setIcon(
                PersonalizationHandler.getDerivableIcon(Personalization.KEY_ICON_BUTTON_ADD)
                        .deriveIcon(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD))
                        .deriveIcon(38f));
        this.add(buttonNuevo, BorderLayout.EAST);
    }

    private final Y component;
    private MaterialButtonIcon buttonNuevo;

    public Y getComponent() {
        return component;
    }

    private void addListeners() {
        buttonNuevo.addActionListener((java.awt.event.ActionEvent evt) -> {
            new DialogInputCBS(InputGeneralSelection.this, inputPanel());
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        buttonNuevo.setEnabled(enabled);
        component.setEnabled(enabled);
    }

    protected abstract List<T> getList() throws Exception;

    protected abstract void addPropertyChange();

    public abstract ModelPanel<T> inputPanel();

    public void setButtonNuevoVisibility(boolean visible) {
        buttonNuevo.setVisible(visible);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "create":
                internalUpdate();
                break;
            case "edit":
                internalUpdate();
                break;
            case "destroy":
                internalUpdate();
                break;
            case "destroyById":
                internalUpdate();
                break;
        }
    }

    private void internalUpdate() {
        T obj = getObject();
        update();
        if (obj != null) {
            setObject(obj);
        }
    }

    @Override
    public T getObject() {
        return component.getObject();
    }

    @Override
    public void setObject(T t) {
        component.setObject(t);
    }

    @Override
    public void wrong() {
        component.wrong();
    }

    @Override
    public void wrong(String string) {
        component.wrong(string);
    }

    @Override
    public Color getWrongColor() {
        return component.getWrongColor();
    }

    @Override
    public void setWrongColor(Color color) {
        component.setWrongColor(color);
    }

    @Override
    public void clearWrong() {
        component.clearWrong();
    }

    @Override
    public void paintWrong(Graphics grphcs, int i) {
        component.paintWrong(grphcs, i);
    }

}
