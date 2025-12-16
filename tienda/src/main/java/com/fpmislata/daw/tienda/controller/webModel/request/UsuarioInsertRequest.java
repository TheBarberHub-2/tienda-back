package com.fpmislata.daw.tienda.controller.webModel.request;

import com.fpmislata.daw.tienda.enums.Rol;

public record UsuarioInsertRequest(
        String email,
        String nombre,
        String contrasenya,
        Rol rol) {

}
