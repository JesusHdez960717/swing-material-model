package com.jhw.swing.models.input.panels;

import com.jhw.swing.material.components.button.prepared._buttonAddEdit;
import com.jhw.swing.material.components.container.panel._PanelTransparent;
import com.jhw.swing.material.components.container.panel.prepared._MaterialPanelComponent;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.jhw.utils.interfaces.Update;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public abstract class ModelMixPanel<T> extends _PanelTransparent {

    private ModelPanel modelPanel;
    private List<Component> extras = new ArrayList<>();

    public ModelMixPanel() {
        initComponents();
    }

    public ModelMixPanel(ModelPanel model, Component... extras) {
        this(model, Arrays.asList(extras));
    }

    public ModelMixPanel(ModelPanel model, List<Component> extras) {
        this.modelPanel = model;
        this.extras = extras;

        initComponents();
        personalize();
    }

    private void initComponents() {

        panelGeneral = new _MaterialPanelComponent();
        panelInputView = new com.jhw.swing.material.components.container.panel._PanelComponent();
        buttonAddEdit = new com.jhw.swing.material.components.button.prepared._buttonAddEdit();
        panelExtra = new com.jhw.swing.material.components.container.panel._PanelTransparent();

        panelExtra.setLayout(new java.awt.GridLayout(0, 1));

        this.setLayout(new BorderLayout());

        panelGeneral.setLayout(new BorderLayout());
        panelGeneral.setBorder(new EmptyBorder(5, 10, 10, 10));

        _PanelTransparent input = new _PanelTransparent();
        input.setLayout(new BorderLayout());
        input.add(panelInputView);
        input.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelGeneral.add(input);

        _PanelTransparent button = new _PanelTransparent();
        button.setBorder(new EmptyBorder(0, 0, 0, 3));
        button.setLayout(new BorderLayout());
        button.add(buttonAddEdit, BorderLayout.EAST);
        panelGeneral.add(button, BorderLayout.SOUTH);

        this.add(panelGeneral);
        this.add(panelExtra, BorderLayout.EAST);
    }// </editor-fold>                        

    // Variables declaration - do not modify//:variables
    private _MaterialPanelComponent panelGeneral;
    private com.jhw.swing.material.components.button.prepared._buttonAddEdit buttonAddEdit;
    private com.jhw.swing.material.components.container.panel._PanelTransparent panelExtra;//extra
    private com.jhw.swing.material.components.container.panel._PanelComponent panelInputView;//model
    // End of variables declaration                   

    private void personalize() {
        this.panelInputView.setComponent(modelPanel);
        panelExtra.removeAll();
        for (Component c : extras) {
            panelExtra.add(c);
        }
        updateAll();
    }

    public void setModelPanel(ModelPanel modelPanel) {
        this.modelPanel = modelPanel;
        personalize();
    }

    public void setExtras(List<Component> extras) {
        this.extras = extras;
        personalize();
    }

    public void addExtra(Component extra) {
        this.extras.add(extra);
        personalize();
    }

    public void updateAll() {
        buttonAddEdit.isCreated(modelPanel.getOldModel() == null);
        modelPanel.update();

        for (Component component : panelExtra.getComponents()) {
            if (component instanceof Update) {
                ((Update) component).update();
            }
        }
    }

    public ModelPanel getModelPanel() {
        return modelPanel;
    }

    public _buttonAddEdit getButtonAddEdit() {
        return buttonAddEdit;
    }
}
