package com.jhw.swing.models.example;

import com.clean.core.app.services.Notification;
import com.clean.core.app.services.NotificationsGeneralType;
import com.jhw.excel.utils.ExcelListWriter;
import com.jhw.swing.models.input.dialogs.DialogModelInput;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.components.table.editors_renders.money.MoneyCellRender;
import com.jhw.swing.material.components.table.editors_renders.money.MoneyTableComponent;
import java.util.Random;
import com.jhw.swing.models.detail._MaterialPanelDetailDragDrop;
import com.jhw.utils.others.SDF;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class CargoPanel extends _MaterialPanelDetailDragDrop<CargoModel> {

    public CargoPanel() {
        super(new Column[]{
            Column.builder().name("nombre").editable(true).build(),
            Column.builder().name("money").editable(true).build(),
            Column.builder().name("fecha").editable(true).build(),
            Column.builder().name("Descripcion").editable(true).build()
        });

        this.setHeaderText("Modelo de cargo");

        getTable().getTable().getColumn("money").setCellRenderer(new MoneyCellRender());

        this.setActionColumnButtonsVisivility(true, false, false);
        
        this.getTable().setPageVisibility(true);
        
        this.update();

    }

    @Override
    protected void buttonNuevoActionListener() {
        new DialogModelInput<CargoModel>(this, new CargoInputView(null));
    }

    @Override
    public Object[] getRowObject(CargoModel object) {
        return new Object[]{object.getNombreCargo(),
            new MoneyTableComponent(BigDecimal.valueOf(new Random().nextDouble()), "MN"),
            SDF.SDF.format(new Date()),
            object.getDescripcion()};
    }

    @Override
    public void update() {
        setCollection(CargoModel.getCargos());
    }

    @Override
    protected CargoModel deleteAction(CargoModel obj) {
        CargoModel.deleteCargo(obj);
        return obj;
    }

    @Override
    protected void editAction(CargoModel obj) {
        new DialogModelInput(this, new CargoInputView(obj));
    }

    @Override
    protected void viewAction(CargoModel obj) {
        Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_ERROR, "no se puede todavia");
    }

    @Override
    public void personalizeBuilder(ExcelListWriter.builder builder) {
        builder.updateValuesColumnCellStyle(2, (Workbook t, CellStyle u) -> {
            u.setDataFormat(t.createDataFormat().getFormat("dd-MM-yyyy"));
            return u;
        });
    }
}
