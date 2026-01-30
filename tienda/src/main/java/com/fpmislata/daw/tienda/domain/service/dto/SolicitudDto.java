package com.fpmislata.daw.tienda.domain.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

import jakarta.validation.constraints.NotNull;

public record SolicitudDto(
        Long id,

        @NotNull(message = "El usuario no puede ser nulo") UsuarioDto usuario,

        @NotNull(message = "El tipo de solicitud no puede ser nulo") TipoSolicitud tipo,

        @NotNull(message = "El estado de la solicitud no puede ser nulo") EstadoSolicitud estado,

        LocalDateTime fecha,

        List<SolicitudPeluqueriaDto> solicitudesPeluqueria,
        List<SolicitudProductoDto> solicitudesProducto) {

    public SolicitudDto(
            Long id,
            UsuarioDto usuario,
            TipoSolicitud tipo,
            EstadoSolicitud estado,
            LocalDateTime fecha,
            List<SolicitudPeluqueriaDto> solicitudesPeluqueria,
            List<SolicitudProductoDto> solicitudesProducto) {

        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
        this.solicitudesPeluqueria = solicitudesPeluqueria == null ? List.of() : List.copyOf(solicitudesPeluqueria);
        this.solicitudesProducto = solicitudesProducto == null ? List.of() : List.copyOf(solicitudesProducto);
    }
}