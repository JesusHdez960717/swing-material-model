package com.jhw.swing.models.input.icbs;

import com.clean.core.app.services.ExceptionHandler;
import com.clean.core.exceptions.ValidationException;
import com.jhw.swing.material.components.button._MaterialButtonIconTransparent;
import com.jhw.swing.material.components.combobox.combobox_editable._MaterialComboBoxFiltrable;
import com.jhw.swing.material.components.container.panel._PanelComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.util.interfaces.BindableComponent;
import com.jhw.utils.interfaces.Update;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import com.jhw.swing.util.interfaces.Wrong;
import com.jhw.swing.utils.icons.DerivableIcon;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandzb96@gmail.com)
 */
public abstract class InputComboBoxSelection<T> extends _PanelComponent implements BindableComponent<T>, Update, Wrong {

    public InputComboBoxSelection(String str) {
        this(str, str);
    }

    public InputComboBoxSelection(String label, String hint) {
        initComponents(label, hint);
        personalize();

        this.getComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearWrong();
            }
        });
        clearWrong();
    }

    @SuppressWarnings("unchecked")
    private void initComponents(String label, String hint) {
        comboBox = new _MaterialComboBoxFiltrable();
        comboBox.setLabel(label);
        comboBox.setHint(hint);

        int h = (int) this.comboBox.getPreferredSize().getHeight();

        buttonNuevo = new _MaterialButtonIconTransparent();
        buttonNuevo.setForeground(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD));
        buttonNuevo.setRippleColor(Color.black);
        buttonNuevo.setIcon(
                PersonalizationHandler.getObject(Personalization.KEY_ICON_BUTTON_ADD, DerivableIcon.class)
                        .deriveIcon(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD))
                        .deriveIcon(h * .6f));

        this.setLayout(new BorderLayout());
        this.add(comboBox, BorderLayout.CENTER);
        this.add(buttonNuevo, BorderLayout.EAST);
    }

    // Variables declaration - do not modify
    private _MaterialButtonIconTransparent buttonNuevo;
    private _MaterialComboBoxFiltrable<T> comboBox;
    // End of variables declaration

    @Override
    public void update() {
        try {
            updateComboBox();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void personalize() {
        setAddButtonListener();
    }

    public T getSelectedItem() {
        return comboBox.getSelectedItem();
    }

    @Override
    public void wrong() {
        comboBox.wrong();
    }

    @Override
    public void wrong(String wrongText) {
        comboBox.wrong(wrongText);
    }

    public void clearWrong() {
        comboBox.clearWrong();
    }

    @Override
    public void setEnabled(boolean enabled) {
        comboBox.setEnabled(enabled);
        buttonNuevo.setEnabled(enabled);
    }

    protected abstract void updateComboBox() throws Exception;

    public abstract ActionListener buttonAddAction();

    protected void setAddButtonListener() {
        buttonNuevo.addActionListener(buttonAddAction());
    }

    public void setSelectedItem(T item) {
        comboBox.setSelectedItem(item);
    }

    public void setModel(List<T> model) {
        comboBox.setModel(model);
        //comboBox.decorate();
    }

    public void setButtonNuevoVisibility(boolean visible) {
        buttonNuevo.setVisible(visible);
    }

    public _MaterialButtonIconTransparent getButtonNuevo() {
        return buttonNuevo;
    }

    public _MaterialComboBoxFiltrable<T> getComboBox() {
        return comboBox;
    }

    public String getHint() {
        return comboBox.getHint();
    }

    public void setHint(String hint) {
        comboBox.setHint(hint);
    }

    public String getLabel() {
        return comboBox.getLabel();
    }

    public void setLabel(String label) {
        comboBox.setLabel(label);
    }

    @Override
    public T getObject() {
        return (T) getSelectedItem();
    }

    @Override
    public void setObject(T object) {
        setSelectedItem(object);
    }
}
