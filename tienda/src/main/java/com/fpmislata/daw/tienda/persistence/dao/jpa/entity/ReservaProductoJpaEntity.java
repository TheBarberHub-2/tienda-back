package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas_productos")
public class ReservaProductoJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private ReservaJpaEntity reserva;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoJpaEntity producto;

    public ReservaProductoJpaEntity() {
    }

    public ReservaProductoJpaEntity(Long id, ReservaJpaEntity reserva, ProductoJpaEntity producto) {
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

    public ReservaJpaEntity getReserva() {
        return reserva;
    }

    public void setReserva(ReservaJpaEntity reserva) {
        this.reserva = reserva;
    }

    public ProductoJpaEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoJpaEntity producto) {
        this.producto = producto;
    }
}