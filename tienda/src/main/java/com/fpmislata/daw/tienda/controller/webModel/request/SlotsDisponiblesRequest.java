package com.fpmislata.daw.tienda.controller.webModel.request;

import java.time.LocalDate;

public record SlotsDisponiblesRequest(
        Long peluqueriaId,
        LocalDate fecha,
        Integer duracionTotal) {

}
