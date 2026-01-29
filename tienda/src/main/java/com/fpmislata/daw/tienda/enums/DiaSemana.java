package com.fpmislata.daw.tienda.enums;

import com.fpmislata.daw.tienda.exception.BusinessException;

public enum DiaSemana {
    LUNES((byte) 1),
    MARTES((byte) 2),
    MIERCOLES((byte) 3),
    JUEVES((byte) 4),
    VIERNES((byte) 5),
    SABADO((byte) 6),
    DOMINGO((byte) 7);

    private final byte value;

    DiaSemana(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static DiaSemana fromValue(byte value) {
        for (DiaSemana d : values()) {
            if (d.value == value) {
                return d;
            }
        }
        throw new BusinessException("Invalid day value: " + value);
    }
}
