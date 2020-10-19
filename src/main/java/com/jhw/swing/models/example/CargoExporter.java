/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.example;

import java.util.Date;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import com.jhw.utils.export.excel.ExcelListWriter;
import com.jhw.swing.models.utils.DefaultExportableConfig;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class CargoExporter extends DefaultExportableConfig<CargoModel> {

    public CargoExporter(CargoDetailViewCleanDrag detail) {
        super(detail);
    }

    @Override
    public String[] getColumnNamesExport() {
        return new String[]{"NOMBRE", "DESCRIPCIÓN", "FECHA"};
    }

    @Override
    public Object[] getRowObjectExport(CargoModel t) {
        return new Object[]{t.getNombreCargo(), t.getDescripcion(), new Date()};
    }

    @Override
    public void personalizeBuilder(ExcelListWriter.builder bldr) {
        bldr.updateValuesColumnCellStyle(2, (Workbook t, CellStyle u) -> {
            u.setDataFormat(t.createDataFormat().getFormat("dd-MMM-yyyy"));
            return u;
        });
    }

}
