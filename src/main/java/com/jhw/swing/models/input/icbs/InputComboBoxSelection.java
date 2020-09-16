package com.jhw.swing.models.input.icbs;

import com.clean.core.app.services.ExceptionHandler;
import com.jhw.swing.material.components.button._MaterialButtonIconTransparent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jhw.swing.material.components.combobox._MaterialComboBoxIcon;
import com.jhw.utils.interfaces.Update;
import java.awt.BorderLayout;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandzb96@gmail.com)
 */
public abstract class InputComboBoxSelection<T> extends _MaterialComboBoxIcon<T> implements Update {

    public InputComboBoxSelection(String str) {
        this(str, str);
    }

    public InputComboBoxSelection(String label, String hint) {
        initComponents(label, hint);
        addListeners();

        clearWrong();
    }

    private void initComponents(String label, String hint) {
        this.setLabel(label);
        this.setHint(hint);
        this.add(buttonNuevo, BorderLayout.EAST);
    }

    private _MaterialButtonIconTransparent buttonNuevo;

    @Override
    public void update() {
        try {
            updateComboBox();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void addListeners() {
        buttonNuevo.addActionListener(buttonAddAction());

        this.getComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearWrong();
            }
        });

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        buttonNuevo.setEnabled(enabled);
    }

    protected abstract void updateComboBox() throws Exception;

    public abstract ActionListener buttonAddAction();

    public void setButtonNuevoVisibility(boolean visible) {
        buttonNuevo.setVisible(visible);
    }

}
