package com.fpmislata.daw.tienda.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

public class Solicitud {

    private Long id;
    private Usuario usuario;
    private TipoSolicitud tipo;
    private EstadoSolicitud estado;
    private LocalDateTime fecha;
    private List<SolicitudPeluqueria> solicitudesPeluqueria;
    private List<SolicitudProducto> solicitudesProducto;

    public Solicitud(Long id,
            Usuario usuario,
            TipoSolicitud tipo,
            EstadoSolicitud estado,
            LocalDateTime fecha,
            List<SolicitudPeluqueria> solicitudesPeluqueria,
            List<SolicitudProducto> solicitudesProducto) {

        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
        this.solicitudesPeluqueria = solicitudesPeluqueria == null ? new ArrayList<>()
                : new ArrayList<>(solicitudesPeluqueria);
        this.solicitudesProducto = solicitudesProducto == null ? new ArrayList<>()
                : new ArrayList<>(solicitudesProducto);
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

    public List<SolicitudPeluqueria> getSolicitudesPeluqueria() {
        return solicitudesPeluqueria;
    }

    public void setSolicitudesPeluqueria(List<SolicitudPeluqueria> solicitudesPeluqueria) {
        this.solicitudesPeluqueria = solicitudesPeluqueria;
    }

    public List<SolicitudProducto> getSolicitudesProducto() {
        return solicitudesProducto;
    }

    public void setSolicitudesProducto(List<SolicitudProducto> solicitudesProducto) {
        this.solicitudesProducto = solicitudesProducto;
    }
}