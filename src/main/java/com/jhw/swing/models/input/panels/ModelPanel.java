package com.jhw.swing.models.input.panels;

import com.jhw.swing.util.interfaces.ModelablePanel;
import com.jhw.swing.material.components.container.panel._PanelTransparent;
import com.jhw.swing.material.components.labels._MaterialLabel;
import com.jhw.swing.material.standards.MaterialFontRoboto;
import com.jhw.utils.interfaces.Update;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

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

        labelHeader = new com.jhw.swing.material.components.labels._MaterialLabel();
        labelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelHeader.setText("Header");
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

}
