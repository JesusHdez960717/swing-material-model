package com.jhw.swing.models.input;

import com.jhw.swing.util.interfaces.Wrong;
import java.util.Map;

/**
 * Interfaz a implementar por los paneles que van a trabajar con la creacion y
 * edicion de modelos, incluye metodos basicos para su trabajo.
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T> Tipo de Modelo que va a trabajar.
 */
public interface ModelablePanel<T> {

    public T getNewModel();

    public T getOldModel();

    public void setOldModel(T model);

    public T onDeleteAction();

    public T onCreateAction();

    public boolean onCancelAction();

    public T onPostCreateAction(T obj);

    public T onPostDeleteAction(T obj);

    public Map<String, Wrong> bindComponentsModel();
}
