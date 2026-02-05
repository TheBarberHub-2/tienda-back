package com.fpmislata.daw.tienda.domain.service.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public record CarritoDto(

        @NotNull(message = "La peluquería no puede ser nula") Long peluqueriaId,

        @NotEmpty(message = "Debe seleccionar al menos un producto") List<ProductoDto> productos,

        @NotNull(message = "La duración total no puede ser nula") @Min(value = 1, message = "La duración total debe ser mayor que 0") Integer duracionTotal,

        @NotNull(message = "El precio total no puede ser nulo") @DecimalMin(value = "0.01", message = "El precio total debe ser mayor que 0") BigDecimal precioTotal) {

    public CarritoDto(
            Long peluqueriaId,
            List<ProductoDto> productos,
            Integer duracionTotal,
            BigDecimal precioTotal) {

        this.peluqueriaId = peluqueriaId;
        this.productos = productos == null ? List.of() : List.copyOf(productos);
        this.duracionTotal = duracionTotal;
        this.precioTotal = precioTotal;
    }
}