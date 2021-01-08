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
package com.root101.swing.models.input.popup_selection;

import com.root101.swing.material.components.button._MaterialInputPopupIcon;
import com.root101.swing.material.effects.Wrong;
import com.root101.swing.util.interfaces.BindableComponent;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
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
