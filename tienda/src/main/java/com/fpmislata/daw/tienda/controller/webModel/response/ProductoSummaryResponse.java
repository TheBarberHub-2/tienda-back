package com.fpmislata.daw.tienda.controller.webModel.response;

import java.math.BigDecimal;

public record ProductoSummaryResponse(
                Long id,
                String nombre,
                BigDecimal precio,
                int duracion) {

}
