package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;

public interface SolicitudService {

    SolicitudDto aprobarSolicitud(long solicitudId);

    SolicitudDto rechazarSolicitud(long solicitudId);

    SolicitudDto confirmarSolicitudPeluqueria(long solicitudId);
}
