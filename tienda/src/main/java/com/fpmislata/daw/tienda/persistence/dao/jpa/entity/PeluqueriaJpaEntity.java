package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "peluquerias")
public class PeluqueriaJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuarioJpaEntity usuario;

    @Column(name = "municipio", nullable = false)
    private String municipio;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "iban", nullable = false)
    private String iban;

    @OneToMany(mappedBy = "peluqueria", cascade = CascadeType.ALL)
    private List<ProductoJpaEntity> productos = new ArrayList<>();

    @OneToMany(mappedBy = "peluqueria", cascade = CascadeType.ALL)
    private List<PeluqueriaHorarioJpaEntity> horarios = new ArrayList<>();

    public PeluqueriaJpaEntity() {
    }

    public PeluqueriaJpaEntity(Long id, UsuarioJpaEntity usuario, String municipio, String direccion,
            String telefono, String iban, List<ProductoJpaEntity> productos,
            List<PeluqueriaHorarioJpaEntity> horarios) {
        this.id = id;
        this.usuario = usuario;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
        this.iban = iban;
        this.productos = productos;
        this.horarios = horarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioJpaEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioJpaEntity usuario) {
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<ProductoJpaEntity> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoJpaEntity> productos) {
        this.productos = productos;
    }

    public List<PeluqueriaHorarioJpaEntity> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<PeluqueriaHorarioJpaEntity> horarios) {
        this.horarios = horarios;
    }
}