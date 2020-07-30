package com.jhw.swing.models.input.panels;

import com.jhw.swing.material.components.button._MaterialButton;
import com.jhw.swing.material.components.button._MaterialButtonIconTransparent;
import com.jhw.swing.material.components.button.prepared._buttonAddEdit;
import com.jhw.swing.material.components.container.panel._PanelGradient;
import java.awt.Color;
import com.jhw.utils.interfaces.Update;
import com.jhw.swing.util.interfaces.ModelablePanel;
import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.material.components.container.layout.HorizontalLayoutContainer;
import com.jhw.swing.material.components.container.panel._PanelTransparent;
import com.jhw.swing.material.components.container.panel.prepared._MaterialPanelComponent;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.material.standards.MaterialIcons;
import java.awt.BorderLayout;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T> Tipo de modelos que se va a trabajar.
 */
public class BaseModelInputPanel<T> extends _PanelGradient implements Update, ModelablePanel<T> {

    private final ModelPanel modelPanel;

    public BaseModelInputPanel(ModelPanel modelPanel) {
        initComponents();
        this.modelPanel = modelPanel;
        this.panelModelCore.setComponent(this.modelPanel);
        personalize();
    }

    private void initComponents() {
        panelModelCore = new _MaterialPanelComponent();
        panelButtons = new _PanelTransparent();
        buttonAddEdit = new com.jhw.swing.material.components.button.prepared._buttonAddEdit();
        buttonCancel = new com.jhw.swing.material.components.button._MaterialButton();
        buttonDelete = new com.jhw.swing.material.components.button._MaterialButtonIconTransparent();

        this.setLayout(new BorderLayout());
        this.add(panelModelCore);

        panelButtons.setLayout(new BorderLayout());
        panelButtons.add(buttonDelete, BorderLayout.WEST);
        
        buttonCancel.setText("Cancelar");
        buttonCancel.setPreferredSize(new java.awt.Dimension(125, 50));

        HorizontalLayoutContainer.builder hlc = HorizontalLayoutContainer.builder((int) buttonAddEdit.getPreferredSize().getHeight());
        hlc.add(buttonAddEdit);
        hlc.add(buttonCancel);

        panelButtons.add(hlc.build(), BorderLayout.EAST);

        this.add(panelButtons, BorderLayout.SOUTH);
    }// </editor-fold>                        

    // Variables declaration - do not modify//:variables
    private com.jhw.swing.material.components.button.prepared._buttonAddEdit buttonAddEdit;
    private com.jhw.swing.material.components.button._MaterialButton buttonCancel;
    private com.jhw.swing.material.components.button._MaterialButtonIconTransparent buttonDelete;
    private _PanelTransparent panelButtons;
    private _MaterialPanelComponent panelModelCore;
    // End of variables declaration                   

    private void personalize() {
        buttonAddEdit.isCreated(modelPanel.getOldModel() == null);
        buttonDelete.setIcon(MaterialIcons.DELETE_FOREVER);

        buttonDelete.setVisible(modelPanel.getOldModel() != null);
        buttonDelete.setEnabled(modelPanel.getOldModel() != null);

        this.setPrimaryColor(MaterialColors.WHITE);
        this.setSecundaryColor(MaterialColors.WHITE);

        buttonCancel.setBackground(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BUTTON_CANCEL));

        this.setBackground(PersonalizationHandler.getColor(Personalization.KEY_COLOR_BACKGROUND_PANEL));
    }

    @Override
    public void update() {
        modelPanel.update();
    }

    @Override
    public T getNewModel() {
        return (T) modelPanel.getNewModel();
    }

    @Override
    public T getOldModel() {
        return (T) modelPanel.getOldModel();
    }

    @Override
    public void setOldModel(T model) {
        modelPanel.setOldModel(model);
    }

    @Override
    public T onDeleteAction() {
        return (T) modelPanel.onDeleteAction();
    }

    @Override
    public T onCreateAction() {
        return (T) modelPanel.onCreateAction();
    }

    @Override
    public T onPostCreateAction(T obj) {
        return (T) modelPanel.onPostCreateAction(obj);
    }

    @Override
    public T onPostDeleteAction(T obj) {
        return (T) modelPanel.onPostDeleteAction(obj);
    }

    @Override
    public boolean onCancelAction() {
        return modelPanel.onCancelAction();
    }

    public void setOkColor(Color okColor) {
        buttonAddEdit.setBackground(okColor);
        personalize();
    }

    public void setCancelColor(Color cancelColor) {
        buttonCancel.setBackground(cancelColor);
        personalize();
    }

    public Color getOkColor() {
        return buttonAddEdit.getBackground();
    }

    public Color getCancelColor() {
        return buttonCancel.getBackground();
    }

    public ModelPanel getModelPanel() {
        return modelPanel;
    }

    public _MaterialButton getMaterialButtonCancel() {
        return buttonCancel;
    }

    public _MaterialButton getMaterialButtonOK() {
        return buttonAddEdit;
    }

    public _buttonAddEdit getButtonAddEdit() {
        return buttonAddEdit;
    }

    public _MaterialButton getButtonCancel() {
        return buttonCancel;
    }

    public _MaterialButtonIconTransparent getButtonDelete() {
        return buttonDelete;
    }

    public _MaterialButtonIconTransparent getMaterialButtonDelete() {
        return buttonDelete;
    }

}
