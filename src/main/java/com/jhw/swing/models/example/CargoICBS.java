package com.jhw.swing.models.example;

import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.models.input.dialogs.DialogInputCBS;
import com.jhw.swing.models.input.icbs.InputComboBoxSelection;
import com.jhw.swing.models.input.panels.ModelPanel;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com) 5/4/2020
 */
public class CargoICBS extends InputComboBoxSelection<CargoModel> {
    
    public CargoICBS() {
        setLabel("Cargo");
        setHint("Hint de los cojones");
        setIcon(MaterialIcons.EDIT);
    }
    
    @Override
    public List<CargoModel> getList() {
        return CargoModel.getCargos();
    }
    
    @Override
    public ModelPanel<CargoModel> inputPanel() {
        return new CargoInputView(null);
    }
    
}
