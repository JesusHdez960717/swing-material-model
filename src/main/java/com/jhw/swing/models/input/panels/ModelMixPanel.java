package com.jhw.swing.models.input.panels;

import com.root101.swing.material.components.button.MaterialButton;
import com.root101.swing.material.components.container.MaterialContainersFactory;
import com.root101.swing.material.components.container.panel.MaterialPanelBorder;
import com.root101.swing.material.components.container.panel._PanelTransparent;
import com.jhw.swing.prepared.button.MaterialButtonAddEdit;
import com.jhw.swing.prepared.button.MaterialPreparedButtonsFactory;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.root101.utils.interfaces.Update;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
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
        panelGeneral = MaterialContainersFactory.buildPanelComponent();
        panelInputView = MaterialContainersFactory.buildPanelComponent();
        buttonAddEdit = MaterialPreparedButtonsFactory.buildAddEdit();
        panelExtra = MaterialContainersFactory.buildPanelTransparent();

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

    private MaterialPanelBorder panelGeneral;
    private MaterialButtonAddEdit buttonAddEdit;
    private JPanel panelExtra;//extra
    private JPanel panelInputView;//model

    private void personalize() {
        this.panelInputView.add(modelPanel);
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
        extra.setPreferredSize(new Dimension(Math.max((int) extra.getPreferredSize().getWidth(), 600), extra.getPreferredSize().height));
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

    public MaterialButton getButtonAddEdit() {
        return buttonAddEdit;
    }

}
