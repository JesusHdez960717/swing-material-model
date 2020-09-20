/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.example;

import com.jhw.swing.material.components.combobox.MaterialComboBoxFactory;
import com.jhw.swing.material.components.combobox.MaterialComboBoxIcon;
import com.jhw.swing.material.components.combobox._MaterialComboBoxIcon;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.models.input.dialogs.DialogInputCBS;
import com.jhw.swing.models.input.icbs.InputGeneralSelection;
import java.awt.event.ActionListener;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ICBSGeneralTest extends InputGeneralSelection<CargoModel, MaterialComboBoxIcon<CargoModel>> {

    public ICBSGeneralTest() {
        super(MaterialComboBoxFactory.buildIcon());
        getComponent().setIcon(MaterialIcons.EDIT);
        getComponent().setLabel("ICBS general new");
    }

    @Override
    public void updateComboBox() {
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
