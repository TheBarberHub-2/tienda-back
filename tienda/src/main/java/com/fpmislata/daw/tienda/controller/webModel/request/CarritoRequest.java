package com.fpmislata.daw.tienda.controller.webModel.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

public record CarritoRequest(
                @NotNull(message = "La peluquería no puede ser nula") Long peluqueriaId,

                @NotEmpty(message = "Debe seleccionar al menos un producto") List<Long> productoIds) {
}