package com.fpmislata.daw.tienda.domain.service.dto;

import java.time.LocalTime;

import com.fpmislata.daw.tienda.enums.DiaSemana;

import jakarta.validation.constraints.NotNull;

public record PeluqueriaHorarioDto(
        Long id,

        @NotNull(message = "La peluquería no puede ser nula") PeluqueriaDto peluqueria,

        @NotNull(message = "El día no puede ser nulo") DiaSemana diaSemana,

        @NotNull(message = "La hora de apertura no puede ser nula") LocalTime horaApertura,

        @NotNull(message = "La hora de cierre no puede ser nula") LocalTime horaCierre) {
    public PeluqueriaHorarioDto(Long id, PeluqueriaDto peluqueria, DiaSemana diaSemana, LocalTime horaApertura,
            LocalTime horaCierre) {
        this.id = id;
        this.peluqueria = peluqueria;
        this.diaSemana = diaSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }
}
