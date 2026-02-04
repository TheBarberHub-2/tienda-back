package com.fpmislata.daw.tienda.controller.webModel.response;

import java.time.LocalTime;

import com.fpmislata.daw.tienda.enums.DiaSemana;

public record PeluqueriaHorarioResponse(
        long id,
        PeluqueriaSummaryResponse peluqueria,
        DiaSemana diaSemana,
        LocalTime horaApertura,
        LocalTime horaCierre) {
}