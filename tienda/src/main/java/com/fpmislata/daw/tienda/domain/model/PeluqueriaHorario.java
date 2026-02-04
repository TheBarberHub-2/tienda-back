package com.fpmislata.daw.tienda.domain.model;

import java.time.LocalTime;

import com.fpmislata.daw.tienda.enums.DiaSemana;

public class PeluqueriaHorario {

    private Long id;
    private Peluqueria peluqueria;
    private DiaSemana diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    public PeluqueriaHorario(Long id, Peluqueria peluqueria, DiaSemana diaSemana, LocalTime horaApertura,
            LocalTime horaCierre) {
        this.id = id;
        this.peluqueria = peluqueria;
        this.diaSemana = diaSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Peluqueria getPeluqueria() {
        return peluqueria;
    }

    public void setPeluqueria(Peluqueria peluqueria) {
        this.peluqueria = peluqueria;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }
}
