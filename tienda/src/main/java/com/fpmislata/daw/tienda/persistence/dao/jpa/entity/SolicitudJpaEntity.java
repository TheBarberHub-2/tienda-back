package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes")
public class SolicitudJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioJpaEntity usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoSolicitud tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoSolicitud estado = EstadoSolicitud.Pendiente;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    public SolicitudJpaEntity() {
    }

    public SolicitudJpaEntity(Long id, UsuarioJpaEntity usuario, TipoSolicitud tipo, EstadoSolicitud estado,
            LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
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

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public void setTipo(TipoSolicitud tipo) {
        this.tipo = tipo;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}