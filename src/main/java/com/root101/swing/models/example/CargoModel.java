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
package com.root101.swing.models.example;

import com.root101.clean.core.domain.DomainObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class CargoModel extends DomainObject {

    private static final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(0);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public static void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public static void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private static final List<CargoModel> list = new ArrayList<>();

    static {
        crearList();
    }

    public static final void crearList() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add(new CargoModel(i, "nombre " + i, "Descripcion"));
        }
    }

    public static final List<CargoModel> getCargos() {
        return list;
    }

    public static final void addCargo(CargoModel c) {
        list.add(c);
        propertyChangeSupport.firePropertyChange("create", null, c);
    }

    public static final void deleteCargo(CargoModel c) {
        list.remove(c);
        propertyChangeSupport.firePropertyChange("destroy", null, c);
    }

    public static final void editCargo(CargoModel c) {
        list.remove(c);
        list.add(c);
        propertyChangeSupport.firePropertyChange("edit", null, c);
    }

    private static final long serialVersionUID = 1L;

    private Integer idCargo;

    private String nombreCargo;

    private String descripcion;

    //@Transient //para que jpa no lo coja como un parametro 
    private File[] files;

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public CargoModel() {
    }

    public CargoModel(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public CargoModel(String nombreCargo, String descripcion) {
        this.nombreCargo = nombreCargo;
        this.descripcion = descripcion;
    }

    public CargoModel(Integer idCargo, String nombreCargo, String descripcion) {
        this.idCargo = idCargo;
        this.nombreCargo = nombreCargo;
        this.descripcion = descripcion;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargoModel)) {
            return false;
        }
        CargoModel other = (CargoModel) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreCargo;
    }

}
