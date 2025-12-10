package com.fpmislata.daw.tienda.controller.webModel.request;

import java.math.BigDecimal;

public record ProductoUpdateRequest(
                String nombre,
                BigDecimal precio,
                int duracion) {

}
