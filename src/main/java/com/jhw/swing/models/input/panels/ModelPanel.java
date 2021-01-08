package com.jhw.swing.models.input.panels;

import com.jhw.swing.models.input.ModelablePanel;
import com.root101.swing.material.components.container.panel._PanelTransparent;
import com.root101.swing.material.components.labels._MaterialLabel;
import com.root101.swing.material.standards.MaterialFontRoboto;
import com.root101.utils.interfaces.Update;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T> Tipo de modelos que se va a trabajar.
 */
public abstract class ModelPanel<T> extends _PanelTransparent implements Update, ModelablePanel<T> {

    protected T model;

    public ModelPanel(T model) {
        initComponents();
        this.model = model;
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        labelHeader = new com.root101.swing.material.components.labels._MaterialLabel();
        labelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelHeader.setText("");
        labelHeader.setFont(MaterialFontRoboto.BOLD.deriveFont(24f));

        this.add(labelHeader, BorderLayout.NORTH);
    }// </editor-fold>                        

    // Variables declaration - do not modify
    private _MaterialLabel labelHeader;
    // End of variables declaration

    protected void setComponent(Component component) {
        this.add(component);
    }

    protected void setHeader(String header) {
        this.labelHeader.setText(header);
    }

    @Override
    public T getOldModel() {
        return model;
    }

    @Override
    public void setOldModel(T model) {
        this.model = model;
    }

    @Override
    public T onPostDeleteAction(T obj) {
        return obj;
    }

    @Override
    public T onPostCreateAction(T obj) {
        return obj;
    }

    @Override
    public void update() {
    }

    @Override
    public Map<String, Object> bindFields() {
        return new HashMap<>();
    }

}
