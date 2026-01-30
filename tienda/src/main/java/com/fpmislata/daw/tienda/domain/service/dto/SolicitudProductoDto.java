package com.fpmislata.daw.tienda.domain.service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolicitudProductoDto(
        Long id,

        @NotNull(message = "La solicitud no puede ser nula") SolicitudDto solicitud,

        @NotNull(message = "La categoría no puede ser nula") CategoriaDto categoria,

        @NotNull(message = "El nombre no puede ser nulo") @Size(max = 20, message = "El nombre debe tener menos de 20 caracteres") String nombre,

        @NotNull(message = "El precio no puede ser nulo") @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor que 0") BigDecimal precio,

        @NotNull(message = "La duración no puede ser nula") @Min(value = 1, message = "La duración debe ser mayor que 0") Integer duracion) {

    public SolicitudProductoDto(
            Long id,
            SolicitudDto solicitud,
            CategoriaDto categoria,
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