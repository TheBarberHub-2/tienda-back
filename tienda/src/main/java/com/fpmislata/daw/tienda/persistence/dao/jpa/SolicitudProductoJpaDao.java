package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudProductoJpaEntity;

public interface SolicitudProductoJpaDao {

    Optional<SolicitudProductoJpaEntity> findById(long id);

    SolicitudProductoJpaEntity insert(SolicitudProductoJpaEntity entity);
}