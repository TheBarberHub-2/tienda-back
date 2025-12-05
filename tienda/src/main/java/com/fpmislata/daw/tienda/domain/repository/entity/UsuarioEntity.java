package com.fpmislata.daw.tienda.domain.repository.entity;

import com.fpmislata.daw.tienda.enums.Rol;

public record UsuarioEntity(
        Long id,
        String email,
        String nombre,
        Rol rol) {

    public UsuarioEntity(
            Long id,
            String email,
            String nombre,
            Rol rol) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
    }
}
