/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.input.popup_selection;

import com.root101.swing.material.components.button._MaterialInputPopupIcon;
import com.root101.swing.material.effects.Wrong;
import com.root101.swing.util.interfaces.BindableComponent;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ButtonPopupIconICBS<T> extends _MaterialInputPopupIcon implements BindableComponent<T>, Wrong {

    public static ButtonPopupIconICBS from() {
        return new ButtonPopupIconICBS();
    }

    private T selected;

    @Override
    public T getObject() {
        return selected;
    }

    @Override
    public void setObject(T t) {
        this.selected = t;
    }

    @Override
    public void wrong() {
    }

    @Override
    public void wrong(String string) {
    }

    @Override
    public Color getWrongColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWrongColor(Color color) {
    }

    @Override
    public void clearWrong() {
    }

    @Override
    public void paintWrong(Graphics grphcs, int i) {
    }

}
