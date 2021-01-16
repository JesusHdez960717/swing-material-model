/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.root101.swing.models.clean;

import com.root101.clean.core.app.services.ExceptionHandler;
import com.root101.clean.core.domain.DomainObject;
import com.root101.export.utils.DomainListFileReader;
import com.root101.swing.material.components.table.Column;
import com.root101.swing.models.detail._MaterialPanelDetailDragDrop;
import com.root101.swing.models.input.dialogs.DialogModelInput;
import com.root101.swing.models.input.panels.ModelPanel;
import com.root101.swing.models.utils.UpdateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
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
        setCollection(getListUpdate());
    }

    protected abstract List<T> getListUpdate() throws RuntimeException;

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
