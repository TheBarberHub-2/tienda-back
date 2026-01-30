package com.fpmislata.daw.tienda.controller.webModel.response;

import java.math.BigDecimal;

public record SolicitudProductoDetailResponse(
                Long id,

                SolicitudDetailResponse solicitud,

                String nombre,

                BigDecimal precio,

                Integer duracion) {

}
