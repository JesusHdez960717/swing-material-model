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
package com.root101.swing.models.clean;

import com.root101.clean.core.app.services.ExceptionHandler;
import com.root101.clean.core.app.usecase.CRUDUseCase;
import com.root101.clean.core.exceptions.ValidationException;
import com.root101.clean.core.utils.validation.Validable;
import com.root101.clean.core.utils.validation.ValidationMessage;
import com.root101.swing.material.effects.Wrong;
import com.root101.swing.models.input.panels.ModelPanel;
import java.lang.reflect.Field;
import java.util.Map;
import com.root101.swing.util.interfaces.BindableComponent;
import com.root101.utils.interfaces.Update;
import com.root101.utils.services.ConverterService;
import java.util.HashMap;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class CleanCRUDInputView<T> extends ModelPanel<T> implements BindableComponent<T> {

    private String headerNew;
    private String headerEdit;

    private final CRUDUseCase<T> uc;
    private final Class<? extends T> clazz;

    private T base;

    public CleanCRUDInputView(T model, CRUDUseCase<T> uc) {
        this(model, uc, null);
    }

    public CleanCRUDInputView(T model, CRUDUseCase<T> uc, Class<? extends T> clazz) {
        super(model);
        this.clazz = clazz;
        this.uc = uc;
    }

    public T getBase() {
        return base;
    }

    public void setBase(T base) {
        this.base = base;
    }

    @Override
    public T getNewModel() throws Exception {
        if (clazz == null) {
            throw new Exception("No se puede crear automáticamente un nuevo objeto sin clase asignada");
        }
        T newObject;
        if (getOldModel() == null) {//create
            if (base != null) {//si hay base la cojo
                newObject = base;
            } else {
                newObject = clazz.newInstance();
            }
        } else {//edit
            newObject = getOldModel();
        }

        Map<String, Object> bindMap = bindFields();
        for (String fieldName : bindMap.keySet()) {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(newObject, getValue(f.getType(), bindMap.get(fieldName)));
        }
        if (doValidate() && newObject instanceof Validable) {
            ((Validable) newObject).validate();
        }
        return newObject;
    }

    protected boolean doValidate() {
        return false;
    }

    private <T> T getValue(Class<T> fieldType, Object componentBinded) throws Exception {
        try {
            return ConverterService.convert(((BindableComponent) componentBinded).getObject(), fieldType);
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

    protected void setHeader(String headerNew, String headerEdit) {
        this.headerNew = headerNew;
        this.headerEdit = headerEdit;
    }

    public String getHeaderNew() {
        return headerNew;
    }

    public String getHeaderEdit() {
        return headerEdit;
    }

    @Override
    public void update() {
        Map<String, Object> bindMap = bindFields();

        //update all fields
        for (String fieldName : bindMap.keySet()) {
            Object component = bindMap.get(fieldName);
            if (component instanceof Update) {//update primero y luego pongo el valor
                ((Update) component).update();
            }
        }

        if (getOldModel() != null) {
            update(bindMap, getOldModel());
        } else if (base != null) {
            update(bindMap, base);
        }

        if (getOldModel() == null) {
            setHeader(getHeaderNew());
        } else {
            setHeader(getHeaderEdit());

        }
    }

    private void update(Map<String, Object> bindMap, T obj) {
        for (String fieldName : bindMap.keySet()) {
            try {
                Object component = bindMap.get(fieldName);
                if (component instanceof BindableComponent) {
                    Field f = clazz.getDeclaredField(fieldName);
                    f.setAccessible(true);
                    Object val = f.get(obj);
                    if (val != null) {
                        ((BindableComponent) component).setObject(val);
                    }
                }
            } catch (Exception e) {
                ExceptionHandler.handleException(e);
            }
        }
    }

    @Override
    public Map<String, Object> bindFields() {
        return new HashMap<>();
    }

    @Override
    public T getObject() {
        try {
            return getNewModel();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
        return null;
    }

    @Override
    public void setObject(T t) {
        setOldModel(t);
        update();
    }

}
