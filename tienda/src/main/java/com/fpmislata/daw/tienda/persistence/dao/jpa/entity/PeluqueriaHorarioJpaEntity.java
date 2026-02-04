package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "peluquerias_horarios")
public class PeluqueriaHorarioJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "peluqueria_id", nullable = false)
    private PeluqueriaJpaEntity peluqueria;

    @Column(name = "dia_semana", nullable = false)
    private Byte diaSemana;

    @Column(name = "hora_apertura", nullable = false)
    private LocalTime horaApertura;

    @Column(name = "hora_cierre", nullable = false)
    private LocalTime horaCierre;

    public PeluqueriaHorarioJpaEntity() {
    }

    public PeluqueriaHorarioJpaEntity(Long id, PeluqueriaJpaEntity peluqueria, Byte diaSemana,
            LocalTime horaApertura, LocalTime horaCierre) {
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

    public PeluqueriaJpaEntity getPeluqueria() {
        return peluqueria;
    }

    public void setPeluqueria(PeluqueriaJpaEntity peluqueria) {
        this.peluqueria = peluqueria;
    }

    public Byte getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Byte diaSemana) {
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