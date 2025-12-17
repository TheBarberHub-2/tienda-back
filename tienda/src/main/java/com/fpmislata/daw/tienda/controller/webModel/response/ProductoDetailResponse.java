package com.fpmislata.daw.tienda.controller.webModel.response;

import java.math.BigDecimal;

public record ProductoDetailResponse(
                Long id,
                CategoriaDetailResponse categoria,
                PeluqueriaSummaryResponse peluqueria,
                String nombre,
                BigDecimal precio,
                int duracion) {

}
