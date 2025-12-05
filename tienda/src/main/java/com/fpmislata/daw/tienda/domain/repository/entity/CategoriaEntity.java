package com.fpmislata.daw.tienda.domain.repository.entity;

public record CategoriaEntity(
        Long id,
        String nombre,
        String descripcion) {
    public CategoriaEntity(
            Long id,
            String nombre,
            String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

}