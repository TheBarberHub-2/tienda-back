package com.fpmislata.daw.tienda.domain.repository.entity;

import com.fpmislata.daw.tienda.enums.Rol;

public record UsuarioEntity(
        Long id,
        String email,
        String nombre,
        String contrasenya,
        Rol rol) {

    public UsuarioEntity(
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
