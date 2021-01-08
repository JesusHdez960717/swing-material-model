package com.jhw.swing.models.input.popup_selection;

import com.root101.clean.core.app.services.ExceptionHandler;
import com.jhw.swing.models.input.icbs.InputGeneralSelection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandzb96@gmail.com)
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
