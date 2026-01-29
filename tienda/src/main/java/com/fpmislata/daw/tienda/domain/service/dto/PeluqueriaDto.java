package com.fpmislata.daw.tienda.domain.service.dto;

import java.util.List;

import com.fpmislata.daw.tienda.domain.validation.ValidUsuarioRolPeluqueria;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@ValidUsuarioRolPeluqueria
public record PeluqueriaDto(
        Long id,

        @NotNull(message = "El usuario no puede ser nulo") UsuarioDto usuario,

        String municipio,

        @NotNull(message = "La dirección no puede ser nula") String direccion,

        @NotNull(message = "El teléfono no puede ser nulo") @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "El teléfono debe tener entre 9 y 15 dígitos") String telefono,

        List<ProductoDto> productos,

        List<PeluqueriaHorarioDto> horarios) {
    public PeluqueriaDto(
            Long id,
            UsuarioDto usuario,
            String municipio,
            String direccion,
            String telefono,
            List<ProductoDto> productos,
            List<PeluqueriaHorarioDto> horarios) {
        this.id = id;
        this.usuario = usuario;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
        this.productos = productos == null ? List.of() : List.copyOf(productos);
        this.horarios = horarios == null ? List.of() : List.copyOf(horarios);
    }
}