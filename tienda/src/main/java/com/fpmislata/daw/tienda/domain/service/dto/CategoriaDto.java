package com.fpmislata.daw.tienda.domain.service.dto;

import jakarta.validation.constraints.NotNull;

public record CategoriaDto(
        Long id,

        @NotNull(message = "El nombre no puede ser nulo") String nombre,

        String descripcion) {
    public CategoriaDto(
            Long id,
            String nombre,
            String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

}