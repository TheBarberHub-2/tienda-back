package com.fpmislata.daw.tienda.controller.webModel.request;

public record PagoTarjetaRequest(
        AutorizacionRequest autorizacion,
        OrigenPagoTarjetaRequest origen,
        DestinoRequest destino,
        PagoRequest pago) {
}
