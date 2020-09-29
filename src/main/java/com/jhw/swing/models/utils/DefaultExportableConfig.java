/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.utils;

import com.clean.core.domain.DomainObject;
import com.jhw.export.excel.ExcelListWriter;
import com.jhw.export.excel.ExportableConfigExcel;
import com.jhw.export.utils.ExportableConfig;
import com.jhw.files.utils.PersonalizationFiles;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.models.detail._MaterialPanelDetailDragDrop;
import com.jhw.utils.services.ConverterService;
import java.io.File;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DefaultExportableConfig<T extends DomainObject> implements ExportableConfig<T>, ExportableConfigExcel<T> {

    private final _MaterialPanelDetailDragDrop detail;

    public DefaultExportableConfig(_MaterialPanelDetailDragDrop detail) {
        this.detail = detail;
    }

    public final List<Object[]> convert(List<T> list) {
        return ConverterService.convert(list, this::getRowObjectExport);
    }

    @Override
    public List<T> getValuesList() {
        return detail.getList();
    }

    @Override
    public Object[] getRowObjectExport(T t) {
        return detail.getRowObject(t);
    }

    @Override
    public String[] getColumnNamesExport() {
        return detail.getColumnNames();
    }

    @Override
    public File getFolder() {
        return new File(PersonalizationHandler.getString(PersonalizationFiles.KEY_TEMP_FOLDER));
    }

    @Override
    public String getFileName() {
        return detail.getHeaderText();
    }

    //-----------------<EXCEL>-----------------
    @Override
    public ExcelListWriter.builder exportExcelBuilder() {
        return ExcelListWriter.builder().config(this);
    }

    @Override
    public void personalizeBuilder(ExcelListWriter.builder bldr) {
    }
    //-----------------</EXCEL>-----------------
}
