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

import com.root101.clean.core.app.services.NotificationsGeneralType;
import com.root101.clean.core.app.services.NotificationHandler;
import com.root101.swing.models.input.dialogs.DialogModelInput;
import com.root101.swing.material.components.button._MaterialButtonIconTransparent;
import com.root101.swing.material.components.container.panel._PanelGradient;
import com.root101.swing.material.components.table.Column;
import com.root101.swing.material.components.table.editors_renders.component.ComponentCellRender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import com.root101.swing.material.standards.MaterialIcons;
import com.root101.swing.models.detail._MaterialPanelDetailDragDrop;
import java.awt.Color;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class CargoDetailViewDragDrop extends _MaterialPanelDetailDragDrop<CargoModel> {

    public CargoDetailViewDragDrop() {
        setColumns(new Column[]{
            Column.builder().name("Color").build(),
            Column.builder().name("nombre").editable(true).build(),
            Column.builder().name("Descripcion").editable(true).build()
        });

        this.setHeaderText("Modelo de cargo(drag drop)");
        getTable().getColumn("Color").setCellRenderer(new ComponentCellRender(false));

        //this.setActionColumnVisivility(true);
        this.setActionColumnButtonsVisivility(true, false, false);
        addActionsExtra();

        this.getTableByPage().setPageVisibility(true);

        this.update();
        //addOptionsElements();
        //this.setOptionPanelVisibility(false);
    }

    @Override
    protected void buttonNuevoActionListener() {
        DialogModelInput.from(new CargoInputView(null));
        //new DialogModelInput<CargoModel>(this, new CargoInputView(null)).setResizable(true);
    }

    @Override
    public Object[] getRowObject(CargoModel object) {
        return new Object[]{getColorPanel(), object.getNombreCargo(), object.getDescripcion()};
    }

    private JPanel getColorPanel() {
        _PanelGradient panel = new _PanelGradient();
        Random rdm = new Random();
        java.awt.Color c = new Color(rdm.nextInt());
        panel.setBackground(c);
        return panel;
    }

    @Override
    public void update() {
        setCollection(CargoModel.getCargos());
    }

    @Override
    protected CargoModel deleteAction(CargoModel obj) {
        CargoModel.deleteCargo(obj);
        return obj;
    }

    @Override
    protected void editAction(CargoModel obj) {
        new DialogModelInput(this, new CargoInputView(obj));
    }

    @Override
    protected void viewAction(CargoModel obj) {
        NotificationHandler.showConfirmDialog(NotificationsGeneralType.CONFIRM_ERROR, "no se puede todavia");
    }

    private void addOptionsElements() {
        _MaterialButtonIconTransparent btn1 = new _MaterialButtonIconTransparent();
        btn1.setIcon(MaterialIcons.ADD_CIRCLE);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        });
        this.addOptionElement(btn1);

        _MaterialButtonIconTransparent btn2 = new _MaterialButtonIconTransparent();
        btn2.setIcon(MaterialIcons.ADD_CIRCLE);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        });
        this.addOptionElement(btn2);

        _MaterialButtonIconTransparent btn3 = new _MaterialButtonIconTransparent();
        btn3.setIcon(MaterialIcons.ADD_CIRCLE);
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        });
        this.addOptionElement(btn3);

    }

    private void addActionsExtra() {
        Action btn1 = new AbstractAction("NOTIFY", MaterialIcons.ADD) {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        };
        this.addActionExtra(btn1);
        this.addActionExtra(btn1);
        this.addActionExtra(btn1);
        this.addActionExtra(btn1);
    }

}
