package com.fpmislata.daw.tienda.controller.webModel.request;

import java.math.BigDecimal;

public record SolicitudProductoRequest(
                long categoriaId,

                String nombre,

                BigDecimal precio,

                Integer duracion) {

}
