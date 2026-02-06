package com.fpmislata.daw.tienda.domain.model;

public class ReservaProducto {

    private Long id;
    private Reserva reserva;
    private Producto producto;

    public ReservaProducto(Long id, Reserva reserva, Producto producto) {
        this.id = id;
        this.reserva = reserva;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}