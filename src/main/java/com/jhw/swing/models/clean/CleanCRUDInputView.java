/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.clean;

import com.clean.core.app.services.ExceptionHandler;
import com.clean.core.app.usecase.CRUDUseCase;
import com.clean.core.exceptions.ValidationException;
import com.clean.core.utils.validation.ValidationMessage;
import com.jhw.swing.models.input.panels.ModelPanel;
import com.jhw.swing.util.interfaces.Wrong;
import com.jhw.utils.jackson.JACKSON;
import java.lang.reflect.Field;
import java.util.Map;
import com.jhw.swing.util.interfaces.BindableComponent;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public abstract class CleanCRUDInputView<T> extends ModelPanel<T> {

    private final CRUDUseCase<T> uc;
    private final Class<? extends T> clazz;

    public CleanCRUDInputView(T model, CRUDUseCase<T> uc) {
        this(model, uc, null);
    }

    public CleanCRUDInputView(T model, CRUDUseCase<T> uc, Class<? extends T> clazz) {
        super(model);
        this.clazz = clazz;
        this.uc = uc;
    }

    @Override
    public T getNewModel() throws Exception {
        if (clazz == null) {
            throw new NullPointerException("No se puede crear automáticamente un nuevo objeto sin clase asignada");
        }
        T newObject;
        if (getOldModel() == null) {//create
            newObject = clazz.newInstance();
        } else {//edit
            newObject = getOldModel();
        }

        Map<String, Object> bindMap = bindFields();
        for (String fieldName : bindMap.keySet()) {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(newObject, getValue(f.getType(), bindMap.get(fieldName)));
        }
        return newObject;
    }

    private <T> T getValue(Class<T> fieldType, Object componentBinded) throws Exception {
        try {
            return JACKSON.convert(((BindableComponent) componentBinded).getObject(), fieldType);
        } catch (Exception e) {
            if (componentBinded instanceof Wrong) {
                ((Wrong) componentBinded).wrong("Valor incorrecto para este campo");
            }
            throw e;
        }
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
        } catch (ValidationException valExc) {
            bindErrors(valExc);
            ExceptionHandler.handleException(valExc);
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
        return null;
    }

    @Override
    public boolean onCancelAction() {
        return true;
    }

    private void bindErrors(ValidationException valExc) {
        Map<String, Object> bindMap = bindFields();
        for (ValidationMessage error : valExc.getValidationErrors().getMessages()) {
            if (bindMap.containsKey(error.getSource()) && bindMap.get(error.getSource()) instanceof Wrong) {
                ((Wrong) bindMap.get(error.getSource())).wrong(error.getMessage());
            }
        }
    }

}
