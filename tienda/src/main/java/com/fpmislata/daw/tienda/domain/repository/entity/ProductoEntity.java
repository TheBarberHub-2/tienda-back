package com.fpmislata.daw.tienda.domain.repository.entity;

import java.math.BigDecimal;

public record ProductoEntity(
        Long id,
        CategoriaEntity categoria,
        PeluqueriaEntity peluqueria,
        String nombre,
        BigDecimal precio,
        int duracion) {
    public ProductoEntity(
            Long id,
            CategoriaEntity categoria,
            PeluqueriaEntity peluqueria,
            String nombre,
            BigDecimal precio,
            int duracion) {
        this.id = id;
        this.categoria = categoria;
        this.peluqueria = peluqueria;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
    }
}
