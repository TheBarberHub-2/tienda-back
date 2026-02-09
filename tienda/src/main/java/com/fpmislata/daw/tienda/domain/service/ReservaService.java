package com.fpmislata.daw.tienda.domain.service;

import java.util.List;

import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;

public interface ReservaService {
    ReservaDto crearReserva(ReservaDto dto);

    List<ReservaDto> listarReservasCliente(long clienteId);

    List<ReservaDto> listarReservasClientePorEstado(long clienteId, String estado);

    List<ReservaDto> listarReservasPeluqueria(long peluqueriaId);

    List<ReservaDto> listarReservasPeluqueriaPorEstado(long peluqueriaId, String estado);

    ReservaDto cancelarReservaPorCliente(long reservaId, long clienteId);

    ReservaDto cancelarReservaPorPeluqueria(long reservaId, long peluqueriaId);

    ReservaDto confirmarReserva(long reservaId, long peluqueriaId);
}
