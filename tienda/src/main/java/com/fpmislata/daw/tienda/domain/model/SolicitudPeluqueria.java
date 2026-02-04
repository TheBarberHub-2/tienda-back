package com.fpmislata.daw.tienda.domain.model;

public class SolicitudPeluqueria {

    private Long id;
    private Solicitud solicitud;
    private String municipio;
    private String direccion;
    private String telefono;

    public SolicitudPeluqueria(Long id,
            Solicitud solicitud,
            String municipio,
            String direccion,
            String telefono) {

        this.id = id;
        this.solicitud = solicitud;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}