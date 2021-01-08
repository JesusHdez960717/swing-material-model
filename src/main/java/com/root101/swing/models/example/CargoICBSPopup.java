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
package com.root101.swing.models.example;

import com.root101.swing.material.standards.MaterialIcons;
import com.root101.swing.material.standards.example.MATERIAL_ICONS_EXAMPLE;
import com.root101.swing.models.input.panels.ModelPanel;
import com.root101.swing.models.input.popup_selection.InputPopupSelection;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class CargoICBSPopup extends InputPopupSelection<CargoModel> {

    public static final String TEXT = "Popup";

    public CargoICBSPopup() {
        getComponent().setIcon(MaterialIcons.PEOPLE);
        getComponent().setText(TEXT);
    }

    @Override
    public List<CargoModel> getList() {
        return CargoModel.getCargos();
    }

    @Override
    protected Action convert(CargoModel obj) {
        //return AbstractActionUtils.from(obj.getNombreCargo().split(" ")[1], MaterialIcons.ADD);
        return new AbstractAction(obj.getNombreCargo().split(" ")[1], MaterialIcons.ADD) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Seleccionado " + obj);
            }
        };
    }

    @Override
    protected void addPropertyChange() {
        CargoModel.addPropertyChangeListener(this);
    }

    @Override
    public ModelPanel<CargoModel> inputPanel() {//input para el agregar
        return new CargoInputView(null);
    }

    @Override
    public void select(CargoModel cargo) {//cambios visuales para cuando se selecciona
        getComponent().getButton().setIcon(MATERIAL_ICONS_EXAMPLE.getRandomIcon());
        getComponent().setText(TEXT + " (" + cargo + ")");
    }
}
