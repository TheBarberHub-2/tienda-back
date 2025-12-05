package com.fpmislata.daw.tienda.domain.service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductoDto(
        Long id,

        @NotNull(message = "La categoría no puede ser nula") CategoriaDto categoria,

        @NotNull(message = "La peluquería no puede ser nula") PeluqueriaDto peluqueria,

        @NotNull(message = "El nombre no puede ser nulo") @Size(max = 20, message = "El nombre debe tener menos de 20 caracteres") String nombre,

        @NotNull(message = "El precio no puede ser nulo") @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor que 0") BigDecimal precio,

        @NotNull(message = "La duración no puede ser nula") @Min(value = 1, message = "La duración debe ser mayor que 0") int duracion) {
    public ProductoDto(
            Long id,
            CategoriaDto categoria,
            PeluqueriaDto peluqueria,
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