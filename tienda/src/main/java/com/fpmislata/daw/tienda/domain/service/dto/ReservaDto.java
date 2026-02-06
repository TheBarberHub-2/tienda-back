package com.fpmislata.daw.tienda.domain.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

import com.fpmislata.daw.tienda.enums.DiaSemana;
import com.fpmislata.daw.tienda.enums.EstadoReserva;
import com.fpmislata.daw.tienda.domain.validation.ValidReservaHoras;
import com.fpmislata.daw.tienda.domain.validation.ValidReservaTimestamps;

import jakarta.validation.constraints.NotNull;

@ValidReservaHoras
@ValidReservaTimestamps
public record ReservaDto(

        Long id,

        @NotNull(message = "El cliente no puede ser nulo") UsuarioDto cliente,

        @NotNull(message = "La peluquería no puede ser nula") PeluqueriaDto peluqueria,

        @NotNull(message = "El día de la semana no puede ser nulo") DiaSemana diaSemana,

        @NotNull(message = "La fecha de la reserva no puede ser nula") LocalDate fechaReserva,

        @NotNull(message = "La hora de inicio no puede ser nula") LocalTime horaInicio,

        LocalTime horaFinal,

        Double precioTotal,

        @NotNull(message = "El estado no puede ser nulo") EstadoReserva estado,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        List<ReservaProductoDto> productos) {

    public ReservaDto(
            Long id,
            UsuarioDto cliente,
            PeluqueriaDto peluqueria,
            DiaSemana diaSemana,
            LocalDate fechaReserva,
            LocalTime horaInicio,
            LocalTime horaFinal,
            Double precioTotal,
            EstadoReserva estado,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<ReservaProductoDto> productos) {

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
        this.productos = productos == null ? List.of() : List.copyOf(productos);
    }
}