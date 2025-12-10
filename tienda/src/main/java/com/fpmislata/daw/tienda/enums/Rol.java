package com.fpmislata.daw.tienda.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fpmislata.daw.tienda.exception.BusinessException;

public enum Rol {
    Admin,
    Peluqueria,
    Cliente;

    @JsonCreator
    public static Rol fromString(String value) {
        try {
            return Rol.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Valor de rol no válido: " + value);
        }
    }
}
