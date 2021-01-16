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
package com.root101.swing.models.detail;

import com.root101.clean.core.app.services.ExceptionHandler;
import com.root101.clean.core.domain.DomainObject;
import com.root101.export.utils.DomainListFileReader;
import com.root101.swing.material.components.filechooser.FileDropHandler;
import com.root101.swing.material.components.table.Column;
import com.root101.swing.models.utils.DefaultExportableConfig;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <T>
 */
public abstract class _MaterialPanelDetailDragDrop<T extends DomainObject> extends _MaterialPanelDetail<T> {

    private final DomainListFileReader<T> reader;

    private MaterialExportButton exportButton;

    public _MaterialPanelDetailDragDrop(DomainListFileReader<T> reader) {
        this(reader, new Column[]{});
    }

    public _MaterialPanelDetailDragDrop(Column... arr) {
        this(null, arr);
    }

    public _MaterialPanelDetailDragDrop(DomainListFileReader<T> reader, Column... arr) {
        super(arr);
        this.reader = reader;
        personalize();
    }

    public void setButtonAddTransferConsumer(DomainListFileReader<T> reader) {
        header.getButtonAdd().setTransferHandler(new FileDropHandler((List<File> t) -> {
            try {
                insertAll(reader.read(t));
            } catch (Exception e) {
                ExceptionHandler.handleException(e);
            }
        }));
        header.getButtonAdd().setToolTipText("Arrastre algún fichero para importar");
    }

    public void insertAll(List<T> newDomains) {
        JOptionPane.showMessageDialog(null, "Importando multiples objetos.\n" + newDomains);
    }

    private void personalize() {
        exportButton = MaterialExportButton.from(new DefaultExportableConfig(this));
        addOptionElement(exportButton);

        //reader para el boton de add
        if (reader != null) {
            setButtonAddTransferConsumer(reader);
        }
    }

    public MaterialExportButton getExportButton() {
        return exportButton;
    }

    public void setExportConfig(DefaultExportableConfig expConfig) {
        this.exportButton.setExportConfig(expConfig);
    }

}
