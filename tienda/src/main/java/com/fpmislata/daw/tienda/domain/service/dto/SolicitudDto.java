package com.fpmislata.daw.tienda.domain.service.dto;

import java.time.LocalDateTime;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

import jakarta.validation.constraints.NotNull;

public record SolicitudDto(
        Long id,

        @NotNull(message = "El usuario no puede ser nulo") UsuarioDto usuario,

        @NotNull(message = "El tipo de solicitud no puede ser nulo") TipoSolicitud tipo,

        @NotNull(message = "El estado de la solicitud no puede ser nulo") EstadoSolicitud estado,

        LocalDateTime fecha) {

    public SolicitudDto(
            Long id,
            UsuarioDto usuario,
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