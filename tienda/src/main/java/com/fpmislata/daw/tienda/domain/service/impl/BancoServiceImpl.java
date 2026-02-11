package com.fpmislata.daw.tienda.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.fpmislata.daw.tienda.controller.webModel.request.PagoTarjetaRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.TransferenciaRequest;
import com.fpmislata.daw.tienda.domain.service.BancoService;

public class BancoServiceImpl implements BancoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${banco.api.url}")
    private String baseUrl;

    @Override
    public void pagoTarjeta(PagoTarjetaRequest pagoTarjeta) {
        restTemplate.postForObject(baseUrl + "/pagoTarjeta", pagoTarjeta, Void.class);
    }

    @Override
    public void transferencia(TransferenciaRequest transferencia) {
        restTemplate.postForObject(baseUrl + "/transferencia", transferencia, Void.class);
    }

    @Override
    public String getIbanByNumeroTarjeta(String numeroTarjeta) {
        String url = baseUrl + "/cuentas/tarjeta/" + numeroTarjeta;
        return restTemplate.getForObject(url, String.class);
    }

}
