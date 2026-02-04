package com.fpmislata.daw.tienda.domain.repository.entity;

import java.math.BigDecimal;

public record SolicitudProductoEntity(
        Long id,
        SolicitudEntity solicitud,
        CategoriaEntity categoria,
        String nombre,
        BigDecimal precio,
        Integer duracion) {

    public SolicitudProductoEntity(
            Long id,
            SolicitudEntity solicitud,
            CategoriaEntity categoria,
            String nombre,
            BigDecimal precio,
            Integer duracion) {

        this.id = id;
        this.solicitud = solicitud;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
    }
}