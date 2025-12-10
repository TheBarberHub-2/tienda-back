package com.fpmislata.daw.tienda.controller.webModel.response;

import java.util.List;

public record PeluqueriaDetailResponse(
        String email,
        String nombre,
        String municipio,
        String direccion,
        String telefono,
        List<ProductoSummaryResponse> productos) {

}
