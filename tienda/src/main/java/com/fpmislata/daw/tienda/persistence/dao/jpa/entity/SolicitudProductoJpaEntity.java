package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes_producto")
public class SolicitudProductoJpaEntity implements Serializable {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private SolicitudJpaEntity solicitud;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaJpaEntity categoria;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    public SolicitudProductoJpaEntity() {
    }

    public SolicitudProductoJpaEntity(Long id, SolicitudJpaEntity solicitud, CategoriaJpaEntity categoria,
            String nombre, BigDecimal precio, Integer duracion) {
        this.id = id;
        this.solicitud = solicitud;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SolicitudJpaEntity getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudJpaEntity solicitud) {
        this.solicitud = solicitud;
    }

    public CategoriaJpaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaJpaEntity categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}