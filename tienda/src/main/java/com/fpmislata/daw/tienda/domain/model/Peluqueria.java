package com.fpmislata.daw.tienda.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.exception.BusinessException;

public class Peluqueria {

    private Long id;
    private Usuario usuario;
    private String municipio;
    private String direccion;
    private String telefono;
    private List<Producto> productos;

    public Peluqueria(Long id, Usuario usuario, String municipio, String direccion, String telefono,
            List<Producto> productos) {
        this.id = id;
        this.usuario = usuario;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
        this.productos = (productos == null) ? new ArrayList<>() : new ArrayList<>(productos);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto producto) {
        if (this.productos.contains(producto)) {
            throw new BusinessException("El producto ya existe en la peluquería");
        }
        this.productos.add(producto);
    }
}
