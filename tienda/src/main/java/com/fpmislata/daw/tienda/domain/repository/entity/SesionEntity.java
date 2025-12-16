package com.fpmislata.daw.tienda.domain.repository.entity;

import java.time.LocalDateTime;

public record SesionEntity(
        Long id,
        UsuarioEntity usuario,
        String token,
        LocalDateTime expiredDate) {
    public SesionEntity(Long id, UsuarioEntity usuario, String token, LocalDateTime expiredDate) {
        this.id = id;
        this.usuario = usuario;
        this.token = token;
        this.expiredDate = expiredDate;
    }
}
