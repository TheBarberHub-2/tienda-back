package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudPeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudPeluqueriaJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class SolicitudPeluqueriaJpaDaoImpl implements SolicitudPeluqueriaJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SolicitudPeluqueriaJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(SolicitudPeluqueriaJpaEntity.class, id));
    }

    @Override
    @Transactional
    public SolicitudPeluqueriaJpaEntity insert(SolicitudPeluqueriaJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }
}
