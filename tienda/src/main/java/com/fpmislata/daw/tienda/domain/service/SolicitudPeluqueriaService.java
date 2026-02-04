package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.SolicitudPeluqueriaDto;

public interface SolicitudPeluqueriaService {

    SolicitudPeluqueriaDto crearSolicitudAltaPeluqueria(String token, SolicitudPeluqueriaDto solicitudPeluqueriaDto);
}
