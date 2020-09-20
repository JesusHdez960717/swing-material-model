package com.jhw.swing.models.example;

import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.models.example.*;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.material.standards.example.MATERIAL_ICONS_EXAMPLE;
import com.jhw.swing.models.input.dialogs.DialogInputCBS;
import com.jhw.swing.models.input.icbs.InputComboBoxSelection;
import com.jhw.swing.models.input.panels.ModelPanel;
import com.jhw.swing.models.input.popup_selection.InputPopupSelection;
import com.jhw.swing.util.AbstractActionUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com) 5/4/2020
 */
public class CargoICBSPopup extends InputPopupSelection<CargoModel> {

    public static final String TEXT = "Popup";

    public CargoICBSPopup() {
        getComponent().setIcon(MaterialIcons.PEOPLE);
        getComponent().setText(TEXT);
    }

    @Override
    public void updateComponent() {
        setComponent(CargoModel.getCargos());
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
    public ModelPanel<CargoModel> inputPanel() {//input para el agregar
        return new CargoInputView(null);
    }

    @Override
    public void select(CargoModel cargo) {//cambios visuales para cuando se selecciona
        getComponent().getButton().setIcon(MATERIAL_ICONS_EXAMPLE.getRandomIcon());
        getComponent().setText(TEXT + " (" + cargo + ")");
    }
}
