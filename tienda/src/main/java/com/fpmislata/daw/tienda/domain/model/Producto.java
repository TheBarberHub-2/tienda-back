package com.fpmislata.daw.tienda.domain.model;

import java.math.BigDecimal;

public class Producto {

    private Long id;
    private Categoria categoria;
    private Peluqueria peluqueria;
    private String nombre;
    private BigDecimal precio;
    private int duracion;

    public Producto(Long id, Categoria categoria, Peluqueria peluqueria, String nombre, BigDecimal precio,
            int duracion) {
        this.id = id;
        this.categoria = categoria;
        this.peluqueria = peluqueria;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Peluqueria getPeluqueria() {
        return peluqueria;
    }

    public void setPeluqueria(Peluqueria peluqueria) {
        this.peluqueria = peluqueria;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
