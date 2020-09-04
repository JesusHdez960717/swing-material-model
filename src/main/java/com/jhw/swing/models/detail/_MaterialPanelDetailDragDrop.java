/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.detail;

import com.clean.core.app.services.ExceptionHandler;
import com.clean.core.domain.DomainObject;
import com.jhw.excel.utils.DomainListFileReader;
import com.jhw.excel.utils.ExcelListWriter;
import com.jhw.files.utils.PersonalizationFiles;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.material.components.button._MaterialButtonIconTransparent;
import com.jhw.swing.material.components.filechooser.FileDropHandler;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.util.AbstractActionUtils;
import com.jhw.utils.services.ConverterService;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T>
 */
public abstract class _MaterialPanelDetailDragDrop<T extends DomainObject> extends _MaterialPanelDetail<T> {

    private final DomainListFileReader<T> reader;

    public _MaterialPanelDetailDragDrop(DomainListFileReader<T> reader) {
        this(reader, new Column[]{});
    }

    public _MaterialPanelDetailDragDrop(DomainListFileReader<T> reader, Column... arr) {
        super(arr);
        this.reader = reader;
        personalize();
    }

    public _MaterialPanelDetailDragDrop(Column... arr) {
        this(null, arr);
    }

    public void setButtonAddTransferConsumer(DomainListFileReader<T> reader) {
        header.getButtonAdd().setTransferHandler(new FileDropHandler((List<File> t) -> {
            try {
                insertAll(reader.read(t));
            } catch (Exception e) {
                ExceptionHandler.handleException(e);
            }
        }));
    }

    public void insertAll(List<T> newDomains) {
        for (T newDomain : newDomains) {
        }
    }

    public Object[] getRowObjectExport(T object) {
        return getRowObject(object);
    }

    public String[] getColumnNamesExport() {
        return getColumnNames();
    }

    private void personalize() {
        Action excelAction = new AbstractAction("Exportar (Excel por defecto). Click derecho para desplegar TODAS las opciones de exportación.", MaterialIcons.EXCEL.deriveIcon(24f)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExportToExcelAction();
            }
        };
        excelAction.putValue(_MaterialButtonIconTransparent.KEY_ACTION_POPUP, getPopupAllExportSupported());
        addOptionElement(excelAction);
        if (reader != null) {
            setButtonAddTransferConsumer(reader);
        }
    }

    protected ExcelListWriter.builder exportBuilder() {
        return ExcelListWriter.builder();
    }

    protected void personalizeBuilder(ExcelListWriter.builder builder) {
    }

    private void onExportToExcelAction() {
        try {
            ExcelListWriter.builder b = exportBuilder()
                    .folder(new File(PersonalizationHandler.getString(PersonalizationFiles.KEY_TEMP_FOLDER)))
                    .fileName(header.getHeaderText())
                    .setColumns(this::getColumnNamesExport)
                    .values(ConverterService.convert(list, this::getRowObjectExport));

            //personaliza con las cosas que quiera
            personalizeBuilder(b);

            b.write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void onExportToExcelSelectedAction() {
        try {
            ExcelListWriter.builder b = exportBuilder()
                    .folder(new File(PersonalizationHandler.getString(PersonalizationFiles.KEY_TEMP_FOLDER)))
                    .fileName(header.getHeaderText())
                    .setColumns(this::getColumnNamesExport)
                    .values(ConverterService.convert(getSelectedList(), this::getRowObjectExport));

            //personaliza con las cosas que quiera
            personalizeBuilder(b);

            b.write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private JPopupMenu getPopupAllExportSupported() {
        JPopupMenu menu = new JPopupMenu("Exportar a:");

        //crea el menu de excel
        JMenuItem menuExcel = new JMenu(AbstractActionUtils.from("Excel", MaterialIcons.EXCEL));
        //accion de exportarlo todo a excel
        JMenuItem excelAll = new JMenuItem(new AbstractAction("Exportar Todo", MaterialIcons.SELECT_ALL) {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExportToExcelAction();
            }
        });
        menuExcel.add(excelAll);
        //accion de exportar lo seleccionado a excel
        JMenuItem excelSelected = new JMenuItem(new AbstractAction("Exportar Seleccionado", MaterialIcons.FLIP) {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExportToExcelSelectedAction();
            }
        });
        menuExcel.add(excelSelected);
        //agregado el menu de excel al menu general
        menu.add(menuExcel);

        return menu;
    }
}
