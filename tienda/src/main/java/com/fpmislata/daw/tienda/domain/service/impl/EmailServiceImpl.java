package com.fpmislata.daw.tienda.domain.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.service.EmailService;

public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarCancelacionPeluqueria(ReservaEntity reserva) {
        String emailPeluqueria = reserva.peluqueria().usuario().email();
        String peluqueria = reserva.peluqueria().usuario().nombre();
        String cliente = reserva.cliente().nombre();

        String asunto = "Reserva cancelada";
        String cuerpo = """
                Hola %s,

                El cliente %s ha cancelado su reserva programada para el día %s a las %s.

                Un saludo,
                TheBarberHub
                """.formatted(
                peluqueria,
                cliente,
                reserva.fechaReserva(),
                reserva.horaInicio());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailPeluqueria);
        message.setSubject(asunto);
        message.setText(cuerpo);

        mailSender.send(message);
    }

    @Override
    public void enviarCancelacionCliente(ReservaEntity reserva) {

        String emailCliente = reserva.cliente().email();
        String peluqueria = reserva.peluqueria().usuario().nombre();
        String cliente = reserva.cliente().nombre();

        String asunto = "Reserva cancelada";
        String cuerpo = """
                Hola %s,

                La peluquería %s ha cancelado tu reserva programada para el día %s a las %s.

                Disculpa las molestias,
                TheBarberHub
                """.formatted(
                cliente,
                peluqueria,
                reserva.fechaReserva(),
                reserva.horaInicio());

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(emailCliente);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
    }
}