package com.jhw.swing.models.input.icbs;

import com.clean.core.app.services.ExceptionHandler;
import com.jhw.swing.material.components.button._MaterialButtonIconTransparent;
import com.jhw.swing.material.components.combobox.combobox_editable._MaterialComboBoxFiltrable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.material.components.container.panel._PanelTransparent;
import com.jhw.swing.material.components.textfield._MaterialTextFieldIcon;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.material.standards.MaterialShadow;
import com.jhw.swing.models.utils.PersonalizationModel;
import com.jhw.swing.util.PersonalizationMaterial;
import com.jhw.swing.util.interfaces.BindableComponent;
import com.jhw.utils.interfaces.Update;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import com.jhw.swing.util.interfaces.Wrong;
import com.jhw.swing.utils.icons.DerivableIcon;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandzb96@gmail.com)
 */
public abstract class InputComboBoxSelection<T> extends _PanelTransparent implements BindableComponent<T>, Update, Wrong {

    private Color originalIconColor = MaterialColors.BLACK;

    public InputComboBoxSelection(String str) {
        this(str, str);
    }

    public InputComboBoxSelection(String label, String hint) {
        initComponents(label, hint);
        addListeners();

        clearWrong();
    }

    private void initComponents(String label, String hint) {
        buttonIcon = new _MaterialButtonIconTransparent();
        buttonIcon.setRippleColor(MaterialColors.TRANSPARENT);
        
        comboBox = new _MaterialComboBoxFiltrable();
        comboBox.setLabel(label);
        comboBox.setHint(hint);

        buttonNuevo = new _MaterialButtonIconTransparent();
        buttonNuevo.setForeground(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD));
        buttonNuevo.setRippleColor(Color.black);
        buttonNuevo.setIcon(
                PersonalizationHandler.getDerivableIcon(Personalization.KEY_ICON_BUTTON_ADD)
                        .deriveIcon(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD))
                        .deriveIcon(38f));

        this.setLayout(new BorderLayout());
        this.add(comboBox, BorderLayout.CENTER);
        this.add(buttonNuevo, BorderLayout.EAST);
    }

    // Variables declaration - do not modify
    private _MaterialButtonIconTransparent buttonNuevo;
    private _MaterialComboBoxFiltrable<T> comboBox;
    private _MaterialButtonIconTransparent buttonIcon;
    // End of variables declaration

    public void setIcon(ImageIcon icon) {
        if (!PersonalizationHandler.getBoolean(PersonalizationMaterial.KEY_SHOW_ICON_INPUT)) {
            return;
        }
        
        int h = (int) this.comboBox.getPreferredSize().getHeight();
        if (icon instanceof DerivableIcon) {
            buttonIcon.setIcon(((DerivableIcon) icon).deriveIcon(h * _MaterialTextFieldIcon.ICON_SIZE_REDUCTION));
            originalIconColor = ((DerivableIcon) icon).getColor();
        } else {
            buttonIcon.setIcon(icon);
        }
        int w = (int) (h * _MaterialTextFieldIcon.ICON_WIDTH_REDUCTION);
        buttonIcon.setPreferredSize(new Dimension(w, h));
        this.add(buttonIcon, BorderLayout.WEST);
    }

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

        buttonIcon.addActionListener(buttonIconAction());

        comboBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                buttonIcon.setForeground(comboBox.getAccent());
            }

            @Override
            public void focusLost(FocusEvent e) {
                buttonIcon.setForeground(originalIconColor);
            }
        });
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
        buttonIcon.setEnabled(enabled);
    }

    protected abstract void updateComboBox() throws Exception;

    public abstract ActionListener buttonAddAction();

    public ActionListener buttonIconAction() {
        return (ActionEvent e) -> {
        };
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
