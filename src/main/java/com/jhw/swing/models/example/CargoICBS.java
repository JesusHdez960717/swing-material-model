package com.jhw.swing.models.example;

import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.models.input.dialogs.DialogInputCBS;
import com.jhw.swing.models.input.icbs.InputComboBoxSelection;
import java.awt.event.ActionListener;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com) 5/4/2020
 */
public class CargoICBS extends InputComboBoxSelection<CargoModel> {

    public CargoICBS() {
        super("Cargo");
        getComponent().setIcon(MaterialIcons.EDIT);
    }

    @Override
    public void updateComponent() {
        getComponent().setModel(CargoModel.getCargos());
        //getComboBox().decorate();
    }

    @Override
    public ActionListener buttonAddAction() {
        return new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onButtonAddActionPerformed();
            }
        };
    }

    private void onButtonAddActionPerformed() {
        new DialogInputCBS(this, new CargoInputView(null));
    }

}
