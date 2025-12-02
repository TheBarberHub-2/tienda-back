package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class ProductoJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaJpaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "peluqueria_id")
    private PeluqueriaJpaEntity peluqueria;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "duracion", nullable = false)
    private int duracion;

    public ProductoJpaEntity() {
    }

    public ProductoJpaEntity(Long id, CategoriaJpaEntity categoria, PeluqueriaJpaEntity peluqueria, String nombre,
            BigDecimal precio, int duracion) {
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

    public CategoriaJpaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaJpaEntity categoria) {
        this.categoria = categoria;
    }

    public PeluqueriaJpaEntity getPeluqueria() {
        return peluqueria;
    }

    public void setPeluqueria(PeluqueriaJpaEntity peluqueria) {
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
