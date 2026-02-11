package com.fpmislata.daw.tienda.domain.service;

import java.util.List;

import com.fpmislata.daw.tienda.controller.webModel.request.OrigenPagoTarjetaRequest;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;

public interface SolicitudService {

    SolicitudDto aprobarSolicitud(long solicitudId);

    SolicitudDto rechazarSolicitud(long solicitudId);

    SolicitudDto confirmarSolicitudPeluqueria(String token, long solicitudId, OrigenPagoTarjetaRequest origen);

    List<SolicitudDto> getSolicitudesPendientes();

    List<SolicitudDto> getSolicitudesAprobadasByPeluqueria(String token);
}
