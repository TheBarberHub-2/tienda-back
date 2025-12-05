package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaJpaEntity;

public interface PeluqueriaJpaDao extends GenericJpaDao<PeluqueriaJpaEntity> {
    Optional<PeluqueriaJpaEntity> findByUsuario(long usuarioId);
}
