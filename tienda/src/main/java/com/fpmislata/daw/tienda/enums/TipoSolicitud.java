package com.fpmislata.daw.tienda.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fpmislata.daw.tienda.exception.BusinessException;

public enum TipoSolicitud {
    Peluqueria,
    Producto;

    @JsonCreator
    public static TipoSolicitud fromString(String value) {
        try {
            return TipoSolicitud.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Valor de tipo de solicitud no válido: " + value);
        }
    }
}