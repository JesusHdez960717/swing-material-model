package com.jhw.swing.models.input.icbs;

import com.clean.core.app.services.ExceptionHandler;
import com.jhw.swing.material.components.combobox.MaterialComboBoxDefinition;
import com.jhw.swing.material.components.combobox.MaterialComboBoxFactory;
import com.jhw.swing.material.components.combobox.MaterialComboBoxIcon;
import com.jhw.swing.material.effects.Iconable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandzb96@gmail.com)
 */
public abstract class InputComboBoxSelection<T> extends InputGeneralSelection<T, MaterialComboBoxIcon<T>> implements Iconable, MaterialComboBoxDefinition<T> {

    public InputComboBoxSelection() {
        super(MaterialComboBoxFactory.buildIcon());
        addListeners();
    }

    private void addListeners() {
        addActionListener((ActionEvent e) -> {
            clearWrong();
        });
    }

    public void addActionListener(ActionListener listener) {
        getComponent().getComboBox().addActionListener(listener);
    }

    @Override
    public void update() {
        try {
            setUpList(getList());
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    /**
     * Metodo a reimplementar si se quiere personalizar la manera en que se pone
     * la lista en el combo box
     *
     * @throws Exception
     */
    protected void setUpList(List<T> l) throws Exception {
        getComponent().setModel(l);
    }

    @Override
    public void setModel(ComboBoxModel<T> cbm) {
        getComponent().setModel(cbm);
    }

    @Override
    public void paintLine(Graphics grphcs) {
        getComponent().paintLine(grphcs);
    }

    @Override
    public int getYLine(Graphics grphcs) {
        return getComponent().getYLine(grphcs);
    }

    @Override
    public Color getAccentFloatingLabel() {
        return getComponent().getAccentFloatingLabel();
    }

    @Override
    public void setAccentFloatingLabel(Color color) {
        getComponent().setAccentFloatingLabel(color);
    }

    @Override
    public String getLabel() {
        return getComponent().getLabel();
    }

    @Override
    public void setLabel(String string) {
        getComponent().setLabel(string);
        getComponent().setHint("Seleccione " + string.toLowerCase()+"...");
    }

    @Override
    public String getHint() {
        return getComponent().getHint();
    }

    @Override
    public void setHint(String string) {
        getComponent().setHint(string);
    }

    @Override
    public void paintLabel(Graphics grphcs) {
        getComponent().paintLabel(grphcs);
    }

    @Override
    public void paintHint(Graphics grphcs) {
        getComponent().paintHint(grphcs);
    }

    @Override
    public void setIcon(Icon icon) {
        getComponent().setIcon(icon);
    }

    @Override
    public Icon getIcon() {
        return getComponent().getIcon();
    }

}
