/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.clean;

import com.clean.core.app.services.ExceptionHandler;
import com.clean.core.domain.DomainObject;
import com.jhw.excel.utils.DomainListFileReader;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.models.detail._MaterialPanelDetailDragDrop;
import com.jhw.swing.models.input.dialogs.DialogModelInput;
import com.jhw.swing.models.input.panels.ModelPanel;
import com.jhw.swing.models.utils.UpdateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public abstract class CleanDetailCRUDDragDrop<T extends DomainObject> extends _MaterialPanelDetailDragDrop<T> implements PropertyChangeListener {

    private final UpdateListener updList = new UpdateListener(this);

    public CleanDetailCRUDDragDrop(DomainListFileReader<T> reader) {
        super(reader);
        addPropertyChange();
        personalize();
    }

    public CleanDetailCRUDDragDrop(Column... arr) {
        super(arr);
        addPropertyChange();
        personalize();
    }

    public CleanDetailCRUDDragDrop(DomainListFileReader<T> reader, Column... arr) {
        super(reader, arr);
        addPropertyChange();
        personalize();
    }

    protected abstract void personalize();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        updList.propertyChange(evt);
    }

    protected abstract void addPropertyChange();

    @Override
    public void update() {
        try {
            setCollection(getListUpdate());
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    protected abstract List<T> getListUpdate() throws Exception;

    @Override
    protected void buttonNuevoActionListener() {
        DialogModelInput.from(getModelPanelNew());
    }

    protected abstract ModelPanel<T> getModelPanelNew();

    @Override
    protected void editAction(T obj) {
        DialogModelInput.from(getModelPanelEdit(obj));
    }

    @Override
    protected void viewAction(T obj) {
    }

    protected abstract ModelPanel<T> getModelPanelEdit(T obj);
}
