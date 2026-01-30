package com.fpmislata.daw.tienda.domain.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SolicitudPeluqueriaDto(
        Long id,

        @NotNull(message = "La solicitud no puede ser nula") SolicitudDto solicitud,

        String municipio,

        @NotNull(message = "La dirección no puede ser nula") String direccion,

        @NotNull(message = "El teléfono no puede ser nulo") @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "El teléfono debe tener entre 9 y 15 dígitos") String telefono) {

    public SolicitudPeluqueriaDto(
            Long id,
            SolicitudDto solicitud,
            String municipio,
            String direccion,
            String telefono) {

        this.id = id;
        this.solicitud = solicitud;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}