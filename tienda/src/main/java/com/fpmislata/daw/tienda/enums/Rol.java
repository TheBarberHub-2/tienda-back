package com.fpmislata.daw.tienda.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Rol {
    Admin,
    Peluqueria,
    Cliente;

    @JsonCreator
    public static Rol fromString(String value) {
        try {
            return Rol.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Valor de rol no válido: " + value);
        }
    }
}
