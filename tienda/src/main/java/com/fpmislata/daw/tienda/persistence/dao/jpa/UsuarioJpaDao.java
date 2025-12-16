package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.UsuarioJpaEntity;

public interface UsuarioJpaDao extends GenericJpaDao<UsuarioJpaEntity> {
    Optional<UsuarioJpaEntity> findByEmail(String email);
}
