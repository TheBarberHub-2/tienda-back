package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudProductoJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    @Override
    public Optional<SolicitudProductoJpaEntity> findBySolicitud(long solicitudId) {
        String sql = "SELECT sp FROM SolicitudProductoJpaEntity sp WHERE sp.solicitud.id = :solicitudId";
        TypedQuery<SolicitudProductoJpaEntity> query = entityManager.createQuery(sql, SolicitudProductoJpaEntity.class);
        query.setParameter("solicitudId", solicitudId);

        List<SolicitudProductoJpaEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

}
