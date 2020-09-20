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
import com.jhw.swing.models.input.panels.ModelPanel;
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
    public void updateComponent() {
        getComponent().setModel(CargoModel.getCargos());
        setObject(CargoModel.getCargos().get(0));
    }

    @Override
    public ModelPanel<CargoModel> inputPanel() {
        return new CargoInputView(null);
    }

}
