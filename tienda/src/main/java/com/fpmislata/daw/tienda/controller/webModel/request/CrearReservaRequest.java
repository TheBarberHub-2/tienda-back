package com.fpmislata.daw.tienda.controller.webModel.request;

public record CrearReservaRequest(
                ReservaInsertRequest reserva,
                OrigenPagoTarjetaRequest origen) {

}
