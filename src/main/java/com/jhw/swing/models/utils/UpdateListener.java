/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.utils;

import com.root101.utils.interfaces.Update;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class UpdateListener implements Update, PropertyChangeListener {

    private final Update[] target;

    public UpdateListener(Update... target) {
        this.target = target;
    }

    @Override
    public void update() {
        for (Update upd : target) {
            upd.update();
        }
    }

    public String[] propertiesToListenFor() {
        return new String[]{"create", "edit", "destroy", "destroyById"};
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for (String string : propertiesToListenFor()) {
            if (evt.getPropertyName().equals(string)) {
                update();
            }
        }
    }

}
