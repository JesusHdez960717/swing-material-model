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
package com.root101.swing.models.example;

import java.util.Date;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import com.jhw.utils.export.excel.ExcelListWriter;
import com.root101.swing.models.utils.DefaultExportableConfig;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
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
