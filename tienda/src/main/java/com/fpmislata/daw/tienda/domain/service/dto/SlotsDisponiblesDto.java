package com.fpmislata.daw.tienda.domain.service.dto;

import java.util.List;

public record SlotsDisponiblesDto(
        Long peluqueriaId,
        String fecha,
        List<String> horasDisponibles) {
}