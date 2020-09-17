package com.jhw.swing.models.example;

import com.clean.core.app.services.Notification;
import com.clean.core.app.services.NotificationsGeneralType;
import com.jhw.swing.material.components.container.layout.VerticalLayoutComponent;
import com.jhw.swing.material.components.container.layout.VerticalLayoutContainer;
import com.jhw.swing.material.components.datepicker.MaterialDatePickerIcon;
import com.jhw.swing.material.components.datepicker.MaterialDatePickersFactory;
import com.jhw.swing.material.components.filechooser.MaterialFileChooser;
import com.jhw.swing.material.components.filechooser.MaterialFileChoosersFactory;
import com.jhw.swing.material.components.textarea.MaterialTextArea;
import com.jhw.swing.material.components.textfield.MaterialFormatedTextFieldIcon;
import com.jhw.swing.material.components.textfield.MaterialTextFactory;
import com.jhw.swing.material.components.textfield.MaterialTextFieldIcon;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.models.input.panels.ModelPanel;
import com.jhw.swing.prepared.textarea.MaterialPreparedTextAreaFactory;
import com.jhw.swing.prepared.textfield.MaterialPreparedTextFactory;
import java.awt.Desktop;
import java.io.File;
import java.util.Random;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com) 5/4/2020
 */
public class CargoInputView extends ModelPanel<CargoModel> {

    public CargoInputView(CargoModel model) {
        super(model);
        initComponents();
        personalize();
        update();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setHeader("Cargo");
        textFieldNombre = MaterialTextFactory.buildIcon();
        textAreaDescripcion = MaterialPreparedTextAreaFactory.buildDescripcion();
        fileChooserPanel = MaterialFileChoosersFactory.buildIcon();
        cargoICBS1 = new com.jhw.swing.models.example.CargoICBS();

        textFieldNombre.setHint("Nombre del cargo");
        textFieldNombre.setLabel("Cargo");
        textFieldNombre.setIcon(MaterialIcons.EDIT);

        money = MaterialPreparedTextFactory.buildFormatedMoneyIcon();
        money.setLabel("money");
        money.setHint("extra hint");

        datePicker = MaterialDatePickersFactory.buildIcon();

        VerticalLayoutContainer.builder v = VerticalLayoutContainer.builder();

        //HorizontalLayoutContainer.builder hlc = HorizontalLayoutContainer.builder((int) cargoICBS1.getPreferredSize().getHeight());
        //hlc.add(HorizontalLayoutComponent.builder(textFieldNombre).build());
        //hlc.add(HorizontalLayoutComponent.builder(cargoICBS1).gapLeft(10).build());
        //v.add(hlc.build());
        v.add(money);
        v.add(cargoICBS1);
        v.add(textFieldNombre);

        v.add(fileChooserPanel);
        v.add(datePicker);
        v.add(VerticalLayoutComponent.builder(textAreaDescripcion).resize(true).build());

        this.setComponent(v.build());
    }

    // Variables declaration - do not modify
    private com.jhw.swing.models.example.CargoICBS cargoICBS1;
    private MaterialDatePickerIcon datePicker;
    private MaterialFormatedTextFieldIcon money;
    private MaterialFileChooser fileChooserPanel;
    private MaterialTextArea textAreaDescripcion;
    private MaterialTextFieldIcon<String> textFieldNombre;
    // End of variables declaration                   

    @Override
    public void update() {
        cargoICBS1.update();
    }

    private void personalize() {
        if (model == null) {
            setHeader("Nuevo Cargo");
        } else {
            setHeader("Editar Cargo");

            textFieldNombre.setObject(getOldModel().getNombreCargo());
            textAreaDescripcion.setObject(getOldModel().getDescripcion());
        }
    }

    @Override
    public CargoModel getNewModel() {
        String nombre = textFieldNombre.getObject();
        String desc = textAreaDescripcion.getObject();
        CargoModel cargoICBS = cargoICBS1.getObject();

        CargoModel cargo;
        if (getOldModel() != null) {
            cargo = getOldModel();
            cargo.setNombreCargo(nombre);
            cargo.setDescripcion(desc);
        } else {
            cargo = new CargoModel(nombre, desc);
        }
        cargo.setIdCargo(new Random().nextInt(10000) + 100);
        return cargo;
    }

    @Override
    public CargoModel onDeleteAction() {
        try {
            CargoModel cargo = getOldModel();
            if (cargo != null && cargo.getIdCargo() != null) {
                CargoModel.deleteCargo(cargo);
                return cargo;
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return null;
    }

    @Override
    public CargoModel onCreateAction() {
        try {
            CargoModel cargo = getNewModel();
            if (getOldModel() == null) {
                CargoModel.addCargo(cargo);
                return cargo;
            } else {
                CargoModel.editCargo(cargo);
                return cargo;
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return null;
    }

    @Override
    public CargoModel onPostCreateAction(CargoModel obj) {
        Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "Post Create ok");
        return super.onPostCreateAction(obj);
    }

    @Override
    public CargoModel onPostDeleteAction(CargoModel obj) {
        Notification.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "Post delete ok");
        return super.onPostDeleteAction(obj);
    }

    @Override
    public boolean onCancelAction() {
        return true;
    }

    private void onButtonOpenFolderActionPerformed() {
        try {
            //hacer esto en metodo aparte general 
            File f = new File("");
            f.mkdir();
            Desktop.getDesktop().open(f);
        } catch (Exception ex) {
        }
    }
}
