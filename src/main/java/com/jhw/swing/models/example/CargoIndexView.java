package com.jhw.swing.models.example;

import com.jhw.swing.models.detail.DialogDetail;
import com.jhw.swing.models.index._MaterialPanelIndex;
import com.jhw.swing.models.input.dialogs.DialogModelInput;
import javax.swing.ImageIcon;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.material.standards.MaterialImages;
import com.jhw.swing.models.input.dialogs.DialogModelMixInput;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class CargoIndexView extends _MaterialPanelIndex {

    public CargoIndexView() {
        this.setMainText("Cargos");
        this.setIcon(MaterialIcons.TEC_GITKRAKEN);
        this.addDetailedText("Administra los cargos ");
        this.addDetailedText("Ejemplo: Jefe de taller, Comercial.");

        this.addButtonAddListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onButtonAddActionPerformed();
            }
        });

        this.addButtonViewListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onButtonViewActionPerformed();
            }
        });
    }

    private void onButtonAddActionPerformed() {
        new DialogModelMixInput<CargoModel>(this, new CargoInputMixView());
    }

    private void onButtonViewActionPerformed() {
        new DialogDetail(this, "Cargos", new CargoDetailView());
    }
}
