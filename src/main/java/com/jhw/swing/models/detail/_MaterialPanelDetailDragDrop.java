/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.detail;

import com.clean.core.app.services.ExceptionHandler;
import com.clean.core.domain.DomainObject;
import com.jhw.excel.utils.DomainListFileReader;
import com.jhw.swing.material.components.button.MaterialButton;
import com.jhw.swing.material.components.button.MaterialButtonsFactory;
import com.jhw.swing.material.components.filechooser.FileDropHandler;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.models.utils.DefaultExportableConfig;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T>
 */
public abstract class _MaterialPanelDetailDragDrop<T extends DomainObject> extends _MaterialPanelDetail<T> {

    private final DomainListFileReader<T> reader;

    private DefaultExportableConfig exportConfig;

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
        MaterialButton exportButton = MaterialButtonsFactory.buildPopup(getPopupAllExportSupported());
        exportButton.setText("Exportar");
        exportButton.setIconTextGap(10);
        exportButton.setIcon(MaterialIcons.EXPORT);
        exportButton.setToolTipText("Exportar a diferentes formato. Click para desplegar TODAS las opciones de exportación.");
        addOptionElement(exportButton);

        //reader para el boton de add
        if (reader != null) {
            setButtonAddTransferConsumer(reader);
        }

        //config por defecto para exportar
        exportConfig = new DefaultExportableConfig(this);
    }

    public DefaultExportableConfig getExportConfig() {
        return exportConfig;
    }

    public void setExportConfig(DefaultExportableConfig expConfig) {
        this.exportConfig = expConfig;
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
                    .values(exportConfig.convert(getSelectedList()))
                    .write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private List<Action> getPopupAllExportSupported() {
        List<Action> actions = new ArrayList<>();
        //accion de exportarlo todo a excel
        actions.add(new AbstractAction("Exportar Todo", MaterialIcons.SELECT_ALL) {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExportToExcelAction();
            }
        });
        //accion de exportar lo seleccionado a excel
        actions.add(new AbstractAction("Exportar Seleccionado", MaterialIcons.FLIP) {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExportToExcelSelectedAction();
            }
        });
        return actions;
    }
}
