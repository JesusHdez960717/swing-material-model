package com.jhw.swing.models.example;

import com.clean.core.app.services.Notification;
import com.clean.core.app.services.NotificationsGeneralType;
import com.jhw.swing.material.components.container.layout.HorizontalLayoutComponent;
import com.jhw.swing.material.components.container.layout.HorizontalLayoutContainer;
import com.jhw.swing.material.components.container.layout.VerticalLayoutContainer;
import com.jhw.swing.material.components.textarea.prepared._MaterialTextAreaDescripcion;
import com.jhw.swing.material.standards.MaterialFontRoboto;
import com.jhw.swing.models.input.panels.ModelPanel;
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
        addListeners();
        update();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setHeader("Cargo");
        textFieldNombre = new com.jhw.swing.material.components.textfield.validated._MaterialTextFieldStringNotEmpty();
        textAreaDescripcion = new com.jhw.swing.material.components.textarea.prepared._MaterialTextAreaDescripcion();
        fileChooserPanel = new com.jhw.swing.material.components.filechooser._MaterialFileChooserPanel();
        cargoICBS1 = new com.jhw.swing.models.example.CargoICBS();

        textFieldNombre.setHint("Nombre del cargo");
        textFieldNombre.setLabel("Cargo");

        VerticalLayoutContainer.builder v = VerticalLayoutContainer.builder();

        //HorizontalLayoutContainer.builder hlc = HorizontalLayoutContainer.builder((int) cargoICBS1.getPreferredSize().getHeight());
        //hlc.add(HorizontalLayoutComponent.builder(textFieldNombre).build());
        //hlc.add(HorizontalLayoutComponent.builder(cargoICBS1).gapLeft(10).build());
        //v.add(hlc.build());
        v.add(textFieldNombre);
        v.add(cargoICBS1);

        v.add(fileChooserPanel);
        v.add(textAreaDescripcion);

        this.setComponent(v.build());
    }

    // Variables declaration - do not modify
    private com.jhw.swing.models.example.CargoICBS cargoICBS1;
    private com.jhw.swing.material.components.filechooser._MaterialFileChooserPanel fileChooserPanel;
    private com.jhw.swing.material.components.textarea.prepared._MaterialTextAreaDescripcion textAreaDescripcion;
    private com.jhw.swing.material.components.textfield.validated._MaterialTextFieldStringNotEmpty textFieldNombre;
    // End of variables declaration                   

    @Override
    public void update() {
        personalize();

        cargoICBS1.actualizarComboBox();
    }

    private void personalize() {
        if (model == null) {
            setHeader("Nuevo Cargo");
        } else {
            setHeader("Editar Cargo");

            textFieldNombre.setString(getOldModel().getNombreCargo());
            textAreaDescripcion.setText(getOldModel().getDescripcion());
        }
    }

    @Override
    public CargoModel getNewModel() {
        String nombre = textFieldNombre.getString();
        String desc = textAreaDescripcion.getText();
        CargoModel cargoICBS = cargoICBS1.getSelectedItem();

        CargoModel cargo;
        if (getOldModel() != null) {
            cargo = getOldModel();
            cargo.setNombreCargo(nombre);
            cargo.setDescripcion(desc);
        } else {
            cargo = new CargoModel(nombre, desc);
        }
        cargo.setIdCargo(new Random().nextInt(10000) + 100);
        cargo.setFiles(fileChooserPanel.getSelectedFiles());
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

    private void addListeners() {
        fileChooserPanel.addButtonOpenFolderAction(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onButtonOpenFolderActionPerformed();
            }
        });
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
