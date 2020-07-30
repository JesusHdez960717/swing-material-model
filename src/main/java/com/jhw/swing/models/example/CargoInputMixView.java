/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.example;

import com.jhw.swing.models.input.panels.ModelMixPanel;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class CargoInputMixView extends ModelMixPanel {

    public CargoInputMixView() {
        CargoInputView cttoIV = new CargoInputView(null);
        this.setModelPanel(cttoIV);

        CargoDetailView ccdv = new CargoDetailView();
        this.addExtra(ccdv);

        CargoDetailView sdv = new CargoDetailView();
        this.addExtra(sdv);
    }

}
