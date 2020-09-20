package com.jhw.swing.models.input.icbs;

import com.clean.core.app.services.ExceptionHandler;
import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.material.components.button.MaterialButtonIcon;
import com.jhw.swing.material.components.button.MaterialButtonsFactory;
import com.jhw.swing.material.components.container.panel._PanelTransparent;
import com.jhw.swing.material.effects.Wrong;
import com.jhw.swing.models.input.dialogs.DialogInputCBS;
import com.jhw.swing.models.input.panels.ModelPanel;
import com.jhw.swing.util.interfaces.BindableComponent;
import com.jhw.utils.interfaces.Update;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandzb96@gmail.com)
 */
public abstract class InputGeneralSelection<T, Y extends JComponent & BindableComponent<T> & Wrong> extends _PanelTransparent implements BindableComponent<T>, Update, Wrong {

    public InputGeneralSelection(Y component) {
        this.component = component;
        initComponents();
        addListeners();

        clearWrong();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.add(component);

        buttonNuevo = MaterialButtonsFactory.buildIconTransparent();
        buttonNuevo.setForeground(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD));
        buttonNuevo.setRippleColor(Color.black);
        buttonNuevo.setIcon(
                PersonalizationHandler.getDerivableIcon(Personalization.KEY_ICON_BUTTON_ADD)
                        .deriveIcon(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_ADD))
                        .deriveIcon(38f));
        this.add(buttonNuevo, BorderLayout.EAST);
    }

    private final Y component;
    private MaterialButtonIcon buttonNuevo;

    public Y getComponent() {
        return component;
    }

    @Override
    public void update() {
        try {
            updateComponent();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void addListeners() {
        buttonNuevo.addActionListener((java.awt.event.ActionEvent evt) -> {
            new DialogInputCBS(InputGeneralSelection.this, inputPanel());
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        buttonNuevo.setEnabled(enabled);
        component.setEnabled(enabled);
    }

    protected abstract void updateComponent() throws Exception;

    public abstract ModelPanel<T> inputPanel();

    public void setButtonNuevoVisibility(boolean visible) {
        buttonNuevo.setVisible(visible);
    }

    @Override
    public T getObject() {
        return component.getObject();
    }

    @Override
    public void setObject(T t) {
        component.setObject(t);
    }

    @Override
    public void wrong() {
        component.wrong();
    }

    @Override
    public void wrong(String string) {
        component.wrong(string);
    }

    @Override
    public Color getWrongColor() {
        return component.getWrongColor();
    }

    @Override
    public void setWrongColor(Color color) {
        component.setWrongColor(color);
    }

    @Override
    public void clearWrong() {
        component.clearWrong();
    }

    @Override
    public void paintWrong(Graphics grphcs, int i) {
        component.paintWrong(grphcs, i);
    }

}
