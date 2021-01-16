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
import com.root101.swing.material.components.button._MaterialButtonPopup;
import com.root101.swing.material.components.popup.MaterialPopupFactory;
import com.root101.swing.material.injection.Background_Force_Foreground;
import com.root101.swing.material.injection.Foreground_Force_Icon;
import com.root101.swing.material.injection.MaterialSwingInjector;
import com.root101.swing.material.standards.MaterialIcons;
import com.root101.swing.util.AbstractActionUtils;
import com.root101.utils.services.ConverterService;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import com.root101.export.utils.ExportableFull;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 */
@Background_Force_Foreground
@Foreground_Force_Icon
public class MaterialExportButton<T> extends _MaterialButtonPopup {

    public static MaterialExportButton from(ExportableFull exportConfig) {
        MaterialExportButton b = MaterialSwingInjector.getImplementation(MaterialExportButton.class);
        b.setExportConfig(exportConfig);
        return b;
    }

    private ExportableFull<T> exportConfig;

    public MaterialExportButton() {
        personalize();
    }

    public void setActions(List<Action> actions) {
        setComponentPopupMenu(MaterialPopupFactory.buildCircular(actions));
    }

    private void personalize() {
        this.setText("Exportar");
        this.setIconTextGap(10);
        this.setIcon(MaterialIcons.EXPORT);
        this.setToolTipText("Exportar a diferentes formato. Click para desplegar TODAS las opciones de exportación.");
        
        this.setActions(getPopupAllExportSupported());
    }

    public ExportableFull<T> getExportConfig() {
        return exportConfig;
    }

    public void setExportConfig(ExportableFull<T> exportConfig) {
        this.exportConfig = exportConfig;
    }

    private void onExportToExcelAction() {
        try {
            exportConfig.exportExcelBuilder().write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void onExportToExcelSelectedAction() {
        try {
            exportConfig.exportExcelBuilder()
                    .values(
                            ConverterService.convert(
                                    exportConfig.getValuesListSelected(),
                                    exportConfig::getRowObjectExport
                            )
                    )
                    .write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void onExportToJSONAction() {
        try {
            exportConfig.exportJSONBuilder().write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void onExportToJSONSelectedAction() {
        try {
            exportConfig.exportJSONBuilder()
                    .values(
                            ConverterService.convert(
                                    exportConfig.getColumnNamesExport(),
                                    exportConfig.getValuesListSelected(),
                                    exportConfig::getRowObjectExport
                            )
                    )
                    .write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private List<Action> getPopupAllExportSupported() {
        List<Action> actions = new ArrayList<>();

        //accion de exportarlo todo a excel
        actions.add(AbstractActionUtils.from(
                "Todo",
                MaterialIcons.EXCEL.deriveIcon(24f),
                "Exportar toda la lista a Excel",
                (ActionEvent e) -> {
                    onExportToExcelAction();
                }));
        //accion de exportar lo seleccionado a excel
        actions.add(AbstractActionUtils.from(
                "Selec.",
                MaterialIcons.EXCEL.deriveIcon(24f),
                "Exportar los elementos seleccionados a Excel",
                (ActionEvent e) -> {
                    onExportToExcelSelectedAction();
                }));

        //accion de exportarlo todo a JSON
        actions.add(AbstractActionUtils.from("Todo",
                MaterialIcons.TEC_JSON.deriveIcon(24f),
                "Exportar toda la lista a JSON",
                (ActionEvent e) -> {
                    onExportToJSONAction();
                }));
        //accion de exportar lo seleccionado a JSON
        actions.add(AbstractActionUtils.from("Selec.",
                MaterialIcons.TEC_JSON.deriveIcon(24f),
                "Exportar los elementos seleccionados a JSON",
                (ActionEvent e) -> {
                    onExportToJSONSelectedAction();
                }));

        return actions;
    }

}
