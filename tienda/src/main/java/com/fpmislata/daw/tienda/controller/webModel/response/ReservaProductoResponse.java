package com.fpmislata.daw.tienda.controller.webModel.response;

import java.math.BigDecimal;

public record ReservaProductoResponse(
        Long id,
        Long productoId,
        String nombreProducto,
        BigDecimal precio,
        Integer duracion) {
}