package com.fpmislata.daw.tienda.controller.webModel.response;

import java.util.List;

public record PeluqueriaDetailResponse(
                Long id,
                String email,
                String nombre,
                String municipio,
                String direccion,
                String telefono,
                List<ProductoSummaryResponse> productos) {

}
