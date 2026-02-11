package com.fpmislata.daw.tienda.domain.repository.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

import com.fpmislata.daw.tienda.enums.EstadoReserva;

public record ReservaEntity(
        Long id,
        UsuarioEntity cliente,
        PeluqueriaEntity peluqueria,
        byte diaSemana,
        LocalDate fechaReserva,
        LocalTime horaInicio,
        LocalTime horaFinal,
        Double precioTotal,
        EstadoReserva estado,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String iban,
        List<ReservaProductoEntity> productos) {

    public ReservaEntity(
            Long id,
            UsuarioEntity cliente,
            PeluqueriaEntity peluqueria,
            byte diaSemana,
            LocalDate fechaReserva,
            LocalTime horaInicio,
            LocalTime horaFinal,
            Double precioTotal,
            EstadoReserva estado,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String iban,
            List<ReservaProductoEntity> productos) {

        this.id = id;
        this.cliente = cliente;
        this.peluqueria = peluqueria;
        this.diaSemana = diaSemana;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.iban = iban;
        this.productos = productos == null ? List.of() : List.copyOf(productos);
    }
}