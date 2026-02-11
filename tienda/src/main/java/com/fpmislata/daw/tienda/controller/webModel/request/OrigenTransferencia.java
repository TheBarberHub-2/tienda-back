package com.fpmislata.daw.tienda.controller.webModel.request;

import jakarta.validation.constraints.Pattern;

public record OrigenTransferencia(
        @Pattern(regexp = "^ES\\d{2}(?:\\s?\\d{4}){5}$", message = "El IBAN debe comenzar con 'ES' y contener 22 dígitos.") String iban) {

}