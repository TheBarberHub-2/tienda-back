package com.fpmislata.daw.tienda.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private Long peluqueriaId;
    private List<Producto> productos;
    private int duracionTotal;
    private BigDecimal precioTotal;

    public Carrito(Long peluqueriaId, List<Producto> productos, int duracionTotal, BigDecimal precioTotal) {
        this.peluqueriaId = peluqueriaId;
        this.productos = productos == null ? new ArrayList<>() : new ArrayList<>(productos);
        this.duracionTotal = duracionTotal;
        this.precioTotal = precioTotal;
    }

    public Long getPeluqueriaId() {
        return peluqueriaId;
    }

    public void setPeluqueriaId(Long peluqueriaId) {
        this.peluqueriaId = peluqueriaId;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getDuracionTotal() {
        return duracionTotal;
    }

    public void setDuracionTotal(int duracionTotal) {
        this.duracionTotal = duracionTotal;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }
}