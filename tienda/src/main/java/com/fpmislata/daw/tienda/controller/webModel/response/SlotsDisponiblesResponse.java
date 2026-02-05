package com.fpmislata.daw.tienda.controller.webModel.response;

import java.util.List;

public record SlotsDisponiblesResponse(
        Long peluqueriaId,
        String fecha,
        List<String> horasDisponibles) {

}
