package com.fpmislata.daw.tienda.controller.webModel.response;

import java.math.BigDecimal;

public record ProductoSummaryResponse(
        String nombre,
        BigDecimal precio,
        int duracion) {

}
