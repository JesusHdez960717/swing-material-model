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
package com.root101.swing.models.input;

import java.util.Map;

/**
 * Interfaz a implementar por los paneles que van a trabajar con la creacion y
 * edicion de modelos, incluye metodos basicos para su trabajo.
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * 
 * @param <T> Tipo de Modelo que va a trabajar.
 */
public interface ModelablePanel<T> {

    public T getNewModel() throws Exception;

    public T getOldModel();

    public void setOldModel(T model);

    public T onDeleteAction();

    public T onCreateAction();

    public boolean onCancelAction();

    public T onPostCreateAction(T obj);

    public T onPostDeleteAction(T obj);

    public Map<String, Object> bindFields();
}
