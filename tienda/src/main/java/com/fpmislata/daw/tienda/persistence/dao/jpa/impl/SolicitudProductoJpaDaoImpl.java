package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudProductoJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class SolicitudProductoJpaDaoImpl implements SolicitudProductoJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SolicitudProductoJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(SolicitudProductoJpaEntity.class, id));
    }

    @Override
    @Transactional
    public SolicitudProductoJpaEntity insert(SolicitudProductoJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

}
