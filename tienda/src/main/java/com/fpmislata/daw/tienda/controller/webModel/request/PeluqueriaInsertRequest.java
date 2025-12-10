package com.fpmislata.daw.tienda.controller.webModel.request;

public record PeluqueriaInsertRequest(
        long usuarioId,
        String municipio,
        String direccion,
        String telefono) {

}
