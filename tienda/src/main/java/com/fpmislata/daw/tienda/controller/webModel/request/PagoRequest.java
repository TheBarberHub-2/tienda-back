package com.fpmislata.daw.tienda.controller.webModel.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record PagoRequest(
        @DecimalMin(value = "0.01", inclusive = true, message = "El importe debe ser mayor que 0") BigDecimal importe,
        @Size(min = 3, message = "El tamaño del concepto debe ser superior a 3") String concepto) {

}
