package com.fpmislata.daw.tienda.domain.repository.entity;

public record SolicitudPeluqueriaEntity(
        Long id,
        SolicitudEntity solicitud,
        String municipio,
        String direccion,
        String telefono) {

    public SolicitudPeluqueriaEntity(
            Long id,
            SolicitudEntity solicitud,
            String municipio,
            String direccion,
            String telefono) {

        this.id = id;
        this.solicitud = solicitud;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}