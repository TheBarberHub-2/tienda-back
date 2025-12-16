package com.fpmislata.daw.tienda.domain.service.dto;

import com.fpmislata.daw.tienda.enums.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDto(
        Long id,

        @NotNull(message = "El email no puede ser nulo") @Email(message = "El email debe tener un formato válido") String email,

        @NotNull(message = "El nombre no puede ser nulo") @Size(max = 25, message = "El nombre no puede superar los 25 carácteres") String nombre,

        @NotNull(message = "La contraseña no puede ser nula") String contrasenya,

        @NotNull(message = "El rol no puede ser nulo") Rol rol) {

    public UsuarioDto(
            Long id,
            String email,
            String nombre,
            String contrasenya,
            Rol rol) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.contrasenya = contrasenya;
        this.rol = rol;
    }
}