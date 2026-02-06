package com.fpmislata.daw.tienda.controller.webModel.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservaResponse(
        Long id,
        Long clienteId,
        Long peluqueriaId,
        String peluqueriaNombre,
        LocalDate fechaReserva,
        LocalTime horaInicio,
        LocalTime horaFinal,
        Double precioTotal,
        String estado,
        List<ReservaProductoResponse> productos) {
}