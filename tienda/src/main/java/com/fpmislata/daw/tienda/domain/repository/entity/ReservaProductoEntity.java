package com.fpmislata.daw.tienda.domain.repository.entity;

public record ReservaProductoEntity(
        Long id,
        ReservaEntity reserva,
        ProductoEntity producto) {

    public ReservaProductoEntity(
            Long id,
            ReservaEntity reserva,
            ProductoEntity producto) {

        this.id = id;
        this.reserva = reserva;
        this.producto = producto;
    }
}