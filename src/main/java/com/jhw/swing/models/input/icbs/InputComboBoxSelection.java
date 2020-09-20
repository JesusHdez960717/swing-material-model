package com.jhw.swing.models.input.icbs;

import com.jhw.swing.material.components.combobox.MaterialComboBoxFactory;
import com.jhw.swing.material.components.combobox.MaterialComboBoxIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandzb96@gmail.com)
 */
public abstract class InputComboBoxSelection<T> extends InputGeneralSelection<T, MaterialComboBoxIcon<T>> {

    public InputComboBoxSelection(String str) {
        this(str, str);
    }

    public InputComboBoxSelection(String label, String hint) {
        super(MaterialComboBoxFactory.buildIcon());
        getComponent().setLabel(label);
        getComponent().setHint(hint);
        addListeners();
    }
    
    private void addListeners() {
        getComponent().getComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearWrong();
            }
        });
    }

}
