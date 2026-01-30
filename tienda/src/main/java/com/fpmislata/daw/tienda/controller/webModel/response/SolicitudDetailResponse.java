package com.fpmislata.daw.tienda.controller.webModel.response;

import java.util.List;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

public record SolicitudDetailResponse(
                Long id,

                UsuarioDetailResponse usuario,

                TipoSolicitud tipo,

                EstadoSolicitud estado,

                List<SolicitudPeluqueriaDetailResponse> solicitudesPeluqueria,

                List<SolicitudProductoDetailResponse> solicitudesProducto) {

}
