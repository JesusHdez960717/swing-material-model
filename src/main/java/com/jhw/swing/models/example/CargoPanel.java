package com.jhw.swing.models.example;

import com.clean.core.app.services.Notification;
import com.clean.core.app.services.NotificationsGeneralType;
import com.jhw.swing.models.detail._MaterialPanelDetailMini;
import com.jhw.swing.models.input.dialogs.DialogModelInput;
import com.jhw.swing.material.components.button._MaterialButtonIconTransparent;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.components.table.editors_renders.money.MoneyCellRender;
import com.jhw.swing.material.components.table.editors_renders.money.MoneyTableComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import com.jhw.swing.material.standards.MaterialIcons;
import java.math.BigDecimal;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class CargoPanel extends _MaterialPanelDetailMini<CargoModel> {

    public CargoPanel() {
        super(new Column[]{
            Column.builder().name("nombre").editable(true).build(),
            Column.builder().name("money").editable(true).build(),
            Column.builder().name("Descripcion").editable(true).build()
        });

        this.setHeaderText("Modelo de cargo");

        getTable().getTable().getColumn("money").setCellRenderer(new MoneyCellRender());

        this.setActionColumnVisivility(false);
        //this.setActionColumnButtonsVisivility(true, false, false);

        this.update();
        this.update();
        this.update();
        this.update();
        this.update();

        //addOptionsElements();
        this.setOptionPanelVisibility(false);
    }

    @Override
    protected void buttonNuevoActionListener() {
        new DialogModelInput<CargoModel>(this, new CargoInputView(null));
    }

    @Override
    public Object[] getRowObject(CargoModel object) {
        return new Object[]{object.getNombreCargo(),
            new MoneyTableComponent(BigDecimal.valueOf(new Random().nextDouble()), "MN"),
            object.getDescripcion()};
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
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihihihihi");
            }
        });
        this.addOptionElement(btn1);

        _MaterialButtonIconTransparent btn2 = new _MaterialButtonIconTransparent();
        btn2.setIcon(MaterialIcons.ADD_CIRCLE);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihihihihi");
            }
        });
        this.addOptionElement(btn2);

        _MaterialButtonIconTransparent btn3 = new _MaterialButtonIconTransparent();
        btn3.setIcon(MaterialIcons.ADD_CIRCLE);
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "hihihihihihi");
            }
        });
        this.addOptionElement(btn3);
    }

}
