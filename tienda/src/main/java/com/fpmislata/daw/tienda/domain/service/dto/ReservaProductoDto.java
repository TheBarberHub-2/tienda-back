package com.fpmislata.daw.tienda.domain.service.dto;

import jakarta.validation.constraints.NotNull;

public record ReservaProductoDto(

        Long id,

        @NotNull(message = "La reserva no puede ser nula") ReservaDto reserva,

        @NotNull(message = "El producto no puede ser nulo") ProductoDto producto) {

    public ReservaProductoDto(
            Long id,
            ReservaDto reserva,
            ProductoDto producto) {

        this.id = id;
        this.reserva = reserva;
        this.producto = producto;
    }
}