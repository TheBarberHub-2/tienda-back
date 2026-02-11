package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.controller.webModel.request.PagoTarjetaRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.TransferenciaRequest;

public interface BancoService {
    void pagoTarjeta(PagoTarjetaRequest pagoTarjeta);

    void transferencia(TransferenciaRequest transferencia);

    String getIbanByNumeroTarjeta(String numeroTarjeta);
}
