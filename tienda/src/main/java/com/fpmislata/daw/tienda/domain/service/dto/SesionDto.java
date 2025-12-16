package com.fpmislata.daw.tienda.domain.service.dto;

import java.time.LocalDateTime;

public record SesionDto(
        Long id,

        UsuarioDto usuario,

        String token,

        LocalDateTime expiredDate) {
    public SesionDto(
            Long id,
            UsuarioDto usuario,
            String token,
            LocalDateTime expiredDate) {
        this.id = id;
        this.usuario = usuario;
        this.token = token;
        this.expiredDate = expiredDate;
    }
}
