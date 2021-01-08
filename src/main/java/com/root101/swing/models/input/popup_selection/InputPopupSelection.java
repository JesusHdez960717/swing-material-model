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

import com.root101.clean.core.app.services.ExceptionHandler;
import com.root101.swing.models.input.icbs.InputGeneralSelection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class InputPopupSelection<T> extends InputGeneralSelection<T, ButtonPopupIconICBS<T>> {

    public InputPopupSelection() {
        super(ButtonPopupIconICBS.from());
    }

    @Override
    public void update() {
        try {
            setComponent(getList());
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    /**
     * Método a reimplementar si se quiere personalizar la manera en la que se
     * ponen los componentes en el popup
     *
     * @param components
     */
    protected void setComponent(List<T> components) {
        List<Action> l = new ArrayList<>(components.size());
        for (T c : components) {
            Action act = convert(c);
            l.add(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setObject(c);
                    act.actionPerformed(e);
                }

                @Override
                public Object getValue(String key) {
                    return act.getValue(key);
                }
            });
        }
        getComponent().setActions(l);
    }

    protected abstract Action convert(T obj);

    protected abstract void select(T t);

    @Override
    public void setObject(T t) {
        super.setObject(t);
        select(t);
    }
}
