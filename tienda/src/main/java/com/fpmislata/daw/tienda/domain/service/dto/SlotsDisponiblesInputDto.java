package com.fpmislata.daw.tienda.domain.service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SlotsDisponiblesInputDto(
        @NotNull(message = "La peluquería no puede ser nula") Long peluqueriaId,

        @NotNull(message = "La fecha no puede ser nula") LocalDate fecha,

        @NotNull(message = "La duración no puede ser nula") @Min(value = 1, message = "La duración debe ser mayor que 0") Integer duracionTotal) {

}
