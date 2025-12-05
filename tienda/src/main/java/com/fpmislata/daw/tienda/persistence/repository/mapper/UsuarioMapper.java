package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.UsuarioJpaEntity;

public class UsuarioMapper {

    private static UsuarioMapper INSTANCE;

    private UsuarioMapper() {
    }

    public static UsuarioMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsuarioMapper();
        }
        return INSTANCE;
    }

    public UsuarioJpaEntity fromEntityToJpa(UsuarioEntity usuarioEntity) {
        if (usuarioEntity == null) {
            return null;
        }
        return new UsuarioJpaEntity(
                usuarioEntity.id(),
                usuarioEntity.email(),
                usuarioEntity.nombre(),
                usuarioEntity.rol());
    }

    public UsuarioEntity fromJpaToEntity(UsuarioJpaEntity usuarioJpaEntity) {
        if (usuarioJpaEntity == null) {
            return null;
        }
        return new UsuarioEntity(
                usuarioJpaEntity.getId(),
                usuarioJpaEntity.getEmail(),
                usuarioJpaEntity.getNombre(),
                usuarioJpaEntity.getRol());
    }
}
