/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.example;

import com.clean.core.domain.DomainObject;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yo
 */
public class CargoModel extends DomainObject {

    private static final List<CargoModel> list = new ArrayList<>();

    static {
        crearList();
    }

    public static final void crearList() {
        list.clear();
        for (int i = 0; i < 5; i++) {
            list.add(new CargoModel(i, "nombre " + i, "Descripcion"));
        }
    }

    public static final List<CargoModel> getCargos() {
        return list;
    }

    public static final void addCargo(CargoModel c) {
        list.add(c);
    }

    public static final void deleteCargo(CargoModel c) {
        list.remove(c);
    }

    public static final void editCargo(CargoModel c) {
        list.remove(c);
        list.add(c);
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
