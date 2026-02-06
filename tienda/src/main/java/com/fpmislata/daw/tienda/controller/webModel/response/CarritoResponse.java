package com.fpmislata.daw.tienda.controller.webModel.response;

import java.math.BigDecimal;
import java.util.List;

public record CarritoResponse(
        List<ProductoSummaryResponse> productos,
        int duracionTotal,
        BigDecimal precioTotal) {
}