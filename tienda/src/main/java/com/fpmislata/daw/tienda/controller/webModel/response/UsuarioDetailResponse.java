package com.fpmislata.daw.tienda.controller.webModel.response;

import com.fpmislata.daw.tienda.enums.Rol;

public record UsuarioDetailResponse(
        String email,
        String nombre,
        Rol rol) {
}