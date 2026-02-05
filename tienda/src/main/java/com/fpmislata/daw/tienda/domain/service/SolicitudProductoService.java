package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.SolicitudProductoDto;

public interface SolicitudProductoService {

    SolicitudProductoDto getById(long id);

    SolicitudProductoDto crearSolicitudProducto(String token, SolicitudProductoDto solicitudProductoDto);

}
