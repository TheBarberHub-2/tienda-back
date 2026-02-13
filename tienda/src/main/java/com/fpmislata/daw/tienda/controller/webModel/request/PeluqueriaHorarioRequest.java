package com.fpmislata.daw.tienda.controller.webModel.request;

import java.time.LocalTime;

import com.fpmislata.daw.tienda.enums.DiaSemana;

public record PeluqueriaHorarioRequest(
                DiaSemana diaSemana,
                LocalTime horaApertura,
                LocalTime horaCierre) {

}
