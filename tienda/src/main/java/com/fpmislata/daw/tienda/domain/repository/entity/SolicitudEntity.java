package com.fpmislata.daw.tienda.domain.repository.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

public record SolicitudEntity(
        Long id,
        UsuarioEntity usuario,
        TipoSolicitud tipo,
        EstadoSolicitud estado,
        LocalDateTime fecha,
        List<SolicitudPeluqueriaEntity> solicitudesPeluqueria,
        List<SolicitudProductoEntity> solicitudesProducto) {

    public SolicitudEntity(
            Long id,
            UsuarioEntity usuario,
            TipoSolicitud tipo,
            EstadoSolicitud estado,
            LocalDateTime fecha,
            List<SolicitudPeluqueriaEntity> solicitudesPeluqueria,
            List<SolicitudProductoEntity> solicitudesProducto) {

        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
        this.solicitudesPeluqueria = solicitudesPeluqueria == null ? List.of() : List.copyOf(solicitudesPeluqueria);
        this.solicitudesProducto = solicitudesProducto == null ? List.of() : List.copyOf(solicitudesProducto);
    }
}