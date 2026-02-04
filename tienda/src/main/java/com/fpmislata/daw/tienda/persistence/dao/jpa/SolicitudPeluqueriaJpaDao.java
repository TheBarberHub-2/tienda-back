package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudPeluqueriaJpaEntity;

public interface SolicitudPeluqueriaJpaDao {

    Optional<SolicitudPeluqueriaJpaEntity> findById(long id);

    SolicitudPeluqueriaJpaEntity insert(SolicitudPeluqueriaJpaEntity entity);
}