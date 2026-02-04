package com.fpmislata.daw.tienda.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fpmislata.daw.tienda.exception.BusinessException;

public enum EstadoSolicitud {
    Pendiente,
    Aprobada,
    Rechazada,
    Confirmada;

    @JsonCreator
    public static EstadoSolicitud fromString(String value) {
        try {
            return EstadoSolicitud.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Valor de estado de solicitud no válido: " + value);
        }
    }
}