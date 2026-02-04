package com.fpmislata.daw.tienda.domain.model;

import java.math.BigDecimal;

public class SolicitudProducto {

    private Long id;
    private Solicitud solicitud;
    private Categoria categoria;
    private String nombre;
    private BigDecimal precio;
    private Integer duracion;

    public SolicitudProducto(Long id,
            Solicitud solicitud,
            Categoria categoria,
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
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