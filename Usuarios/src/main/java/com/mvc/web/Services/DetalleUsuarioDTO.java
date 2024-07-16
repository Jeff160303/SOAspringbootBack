package com.mvc.web.Services;

public class DetalleUsuarioDTO {
    private int idDetalleUsuarios;
    private String dni;
    private String direccion;
    private String codigoPostal;

    public int getIdDetalleUsuarios() {
        return idDetalleUsuarios;
    }

    public void setIdDetalleUsuarios(int idDetalleUsuarios) {
        this.idDetalleUsuarios = idDetalleUsuarios;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}