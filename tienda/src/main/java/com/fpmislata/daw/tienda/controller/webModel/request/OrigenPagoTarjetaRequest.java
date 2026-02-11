package com.fpmislata.daw.tienda.controller.webModel.request;

public record OrigenPagoTarjetaRequest(
        String numeroTarjeta,
        String fechaCaducidad,
        String cvc,
        String nombreCompleto) {

}