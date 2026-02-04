package com.fpmislata.daw.tienda.controller.webModel.response;

public record SolicitudPeluqueriaDetailResponse(
        Long id,

        SolicitudDetailResponse solicitud,

        String municipio,

        String direccion,

        String telefono) {

}
