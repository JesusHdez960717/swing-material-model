package com.jhw.swing.models.example;

import com.clean.core.app.services.Notification;
import com.clean.core.app.services.NotificationsGeneralType;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.components.table.editors_renders.money.MoneyCellRender;
import com.jhw.swing.material.components.table.editors_renders.money.MoneyTableComponent;
import com.jhw.swing.models.clean.CleanDetailCRUDDragDrop;
import java.util.Random;
import com.jhw.swing.models.input.panels.ModelPanel;
import com.jhw.utils.others.SDF;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
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
            SDF.SDF.format(new Date()),
            object.getDescripcion()};
    }

    @Override
    protected CargoModel deleteAction(CargoModel obj) {
        CargoModel.deleteCargo(obj);
        return obj;
    }

    @Override
    protected void viewAction(CargoModel obj) {
        Notification.showConfirmDialog(NotificationsGeneralType.CONFIRM_ERROR, "no se puede todavia");
    }

    /*@Override
    public void personalizeBuilder(ExcelListWriter.builder builder) {
        builder.updateValuesColumnCellStyle(2, (Workbook t, CellStyle u) -> {
            u.setDataFormat(t.createDataFormat().getFormat("dd-MM-yyyy"));
            return u;
        });
    }*/
}
