package com.fpmislata.daw.tienda.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fpmislata.daw.tienda.exception.BusinessException;

public enum EstadoReserva {
    Pendiente,
    Completada,
    Cancelada;

    @JsonCreator
    public static EstadoReserva fromString(String value) {
        try {
            return EstadoReserva.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Valor de estado de reserva no válido: " + value);
        }
    }
}