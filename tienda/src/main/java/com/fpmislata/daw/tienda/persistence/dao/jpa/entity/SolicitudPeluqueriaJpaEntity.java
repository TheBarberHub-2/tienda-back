package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes_peluqueria")
public class SolicitudPeluqueriaJpaEntity implements Serializable {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private SolicitudJpaEntity solicitud;

    @Column(name = "municipio", nullable = true)
    private String municipio;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    public SolicitudPeluqueriaJpaEntity() {
    }

    public SolicitudPeluqueriaJpaEntity(Long id, SolicitudJpaEntity solicitud, String municipio, String direccion,
            String telefono) {
        this.id = id;
        this.solicitud = solicitud;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
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
}