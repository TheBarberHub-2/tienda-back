package com.fpmislata.daw.tienda.domain.repository.entity;

import java.time.LocalDateTime;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

public record SolicitudEntity(
        Long id,
        UsuarioEntity usuario,
        TipoSolicitud tipo,
        EstadoSolicitud estado,
        LocalDateTime fecha) {

    public SolicitudEntity(
            Long id,
            UsuarioEntity usuario,
            TipoSolicitud tipo,
            EstadoSolicitud estado,
            LocalDateTime fecha) {

        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
    }
}