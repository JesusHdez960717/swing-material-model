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

import com.root101.clean.core.app.services.NotificationsGeneralType;
import com.root101.swing.material.components.table.Column;
import com.root101.swing.material.components.table.editors_renders.money.MoneyCellRender;
import com.root101.swing.material.components.table.editors_renders.money.MoneyTableComponent;
import com.root101.swing.models.clean.CleanDetailCRUDDragDrop;
import java.util.Random;
import com.root101.swing.models.input.panels.ModelPanel;
import com.root101.clean.core.app.services.NotificationHandler;
import java.math.BigDecimal;
import java.util.List;
import com.root101.utils.others.DTF;
import java.time.LocalDate;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class CargoDetailViewCleanDrag extends CleanDetailCRUDDragDrop<CargoModel> {

    public CargoDetailViewCleanDrag() {
        super(new Column[]{
            Column.builder().name("nombre").build(),
            Column.builder().name("money").build(),
            Column.builder().name("fecha").build(),
            Column.builder().name("Descripcion").build()
        });

    }

    @Override
    protected void personalize() {

        this.setHeaderText("Clena drag");

        getTable().getColumn("money").setCellRenderer(new MoneyCellRender());

        this.setActionColumnButtonsVisivility(true, false, false);

        this.getTableByPage().setPageVisibility(true);

        this.update();

        this.setExportConfig(new CargoExporter(this));
    }

    @Override
    protected void addPropertyChange() {
        CargoModel.addPropertyChangeListener(this);
    }

    @Override
    protected List<CargoModel> getListUpdate() throws Exception {
        return CargoModel.getCargos();
    }

    @Override
    protected ModelPanel<CargoModel> getModelPanelNew() {
        return new CargoInputView(null);
    }

    @Override
    protected ModelPanel<CargoModel> getModelPanelEdit(CargoModel obj) {
        return new CargoInputView(obj);
    }

    @Override
    public Object[] getRowObject(CargoModel object) {
        return new Object[]{object.getNombreCargo(),
            new MoneyTableComponent(BigDecimal.valueOf(new Random().nextDouble()), "MN"),
            DTF.LOCAL_DATE_FORMATTER.format(LocalDate.now()),
            object.getDescripcion()};
    }

    @Override
    protected CargoModel deleteAction(CargoModel obj) {
        CargoModel.deleteCargo(obj);
        return obj;
    }

    @Override
    protected void viewAction(CargoModel obj) {
        NotificationHandler.showConfirmDialog(NotificationsGeneralType.CONFIRM_ERROR, "no se puede todavia");
    }

    /*@Override
    public void personalizeBuilder(ExcelListWriter.builder builder) {
        builder.updateValuesColumnCellStyle(2, (Workbook t, CellStyle u) -> {
            u.setDataFormat(t.createDataFormat().getFormat("dd-MM-yyyy"));
            return u;
        });
    }*/
}
