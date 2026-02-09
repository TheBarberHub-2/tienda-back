package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;

public interface EmailService {

    void enviarCancelacionPeluqueria(ReservaEntity reserva);

    void enviarCancelacionCliente(ReservaEntity reserva);
}