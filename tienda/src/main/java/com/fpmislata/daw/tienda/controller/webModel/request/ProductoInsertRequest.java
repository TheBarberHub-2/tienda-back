package com.fpmislata.daw.tienda.controller.webModel.request;

import java.math.BigDecimal;

public record ProductoInsertRequest(
                long categoriaId,
                long peluqueriaId,
                String nombre,
                BigDecimal precio,
                int duracion) {

}
