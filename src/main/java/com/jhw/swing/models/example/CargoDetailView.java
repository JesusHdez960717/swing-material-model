package com.jhw.swing.models.example;

import com.clean.core.app.services.NotificationsGeneralType;
import com.clean.core.app.services.Notification;
import com.jhw.swing.models.detail._MaterialPanelDetail;
import com.jhw.swing.models.input.dialogs.DialogModelInput;
import com.jhw.swing.material.components.button._MaterialButtonIconTransparent;
import com.jhw.swing.material.components.container.panel._PanelGradient;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.components.table.editors_renders.component.ComponentCellRender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import com.jhw.swing.material.standards.MaterialIcons;
import java.awt.Color;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class CargoDetailView extends _MaterialPanelDetail<CargoModel> {


    public CargoDetailView() {
        setColumns(new Column[]{
            Column.builder().name("Color").build(),
            Column.builder().name("nombre").editable(true).build(),
            Column.builder().name("Descripcion").editable(true).build()
        });

        this.setHeaderText("Modelo de cargo");
        getTable().getColumn("Color").setCellRenderer(new ComponentCellRender(false));

        //this.setActionColumnVisivility(true);
        this.setActionColumnButtonsVisivility(true, false, false);
        addActionsExtra();

        this.getTableByPage().setPageVisibility(true);

        //this.update();
        //addOptionsElements();
        //this.setOptionPanelVisibility(false);
    }

    @Override
    protected void buttonNuevoActionListener() {
        new DialogModelInput<CargoModel>(this, new CargoInputView(null)).setResizable(true);
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
        Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_ERROR, "no se puede todavia");
    }

    private void addOptionsElements() {
        _MaterialButtonIconTransparent btn1 = new _MaterialButtonIconTransparent();
        btn1.setIcon(MaterialIcons.ADD_CIRCLE);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        });
        this.addOptionElement(btn1);

        _MaterialButtonIconTransparent btn2 = new _MaterialButtonIconTransparent();
        btn2.setIcon(MaterialIcons.ADD_CIRCLE);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        });
        this.addOptionElement(btn2);

        _MaterialButtonIconTransparent btn3 = new _MaterialButtonIconTransparent();
        btn3.setIcon(MaterialIcons.ADD_CIRCLE);
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        });
        this.addOptionElement(btn3);

    }

    private void addActionsExtra() {
        Action btn1 = new AbstractAction("NOTIFY", MaterialIcons.ADD) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihi");
            }
        };
        this.addActionExtra(btn1);
        this.addActionExtra(btn1);
        this.addActionExtra(btn1);
        this.addActionExtra(btn1);
    }

}
