/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.clean;

import com.clean.core.app.services.ExceptionHandler;
import com.clean.core.app.usecase.CRUDUseCase;
import com.jhw.swing.models.input.panels.ModelPanel;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public abstract class CleanCRUDInputView<T> extends ModelPanel<T> {

    private final CRUDUseCase<T> uc;

    public CleanCRUDInputView(T model, CRUDUseCase<T> uc) {
        super(model);
        this.uc = uc;
    }

    @Override
    public T onDeleteAction() {
        try {
            T person = getOldModel();
            if (person != null) {
                return uc.destroy(person);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
        return null;
    }

    @Override
    public T onCreateAction() {
        try {
            T object = getNewModel();
            if (getOldModel() == null) {
                return uc.create(object);
            } else {
                return uc.edit(object);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
        return null;
    }

    @Override
    public boolean onCancelAction() {
        return true;
    }

}
