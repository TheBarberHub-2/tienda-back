package com.fpmislata.daw.tienda.domain.repository.entity;

import java.time.LocalTime;

public record PeluqueriaHorarioEntity(
        Long id,
        PeluqueriaEntity peluqueria,
        Byte diaSemana,
        LocalTime horaApertura,
        LocalTime horaCierre) {
    public PeluqueriaHorarioEntity(Long id, PeluqueriaEntity peluqueria, Byte diaSemana, LocalTime horaApertura,
            LocalTime horaCierre) {
        this.id = id;
        this.peluqueria = peluqueria;
        this.diaSemana = diaSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }
}
