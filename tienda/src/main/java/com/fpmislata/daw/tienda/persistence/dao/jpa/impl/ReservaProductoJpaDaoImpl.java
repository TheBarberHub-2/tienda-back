package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;

import com.fpmislata.daw.tienda.persistence.dao.jpa.ReservaProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaProductoJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class ReservaProductoJpaDaoImpl implements ReservaProductoJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ReservaProductoJpaEntity insert(ReservaProductoJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<ReservaProductoJpaEntity> findByReserva(long reservaId) {
        String sql = "SELECT rp FROM ReservaProductoJpaEntity rp WHERE rp.reserva.id = :reservaId";

        TypedQuery<ReservaProductoJpaEntity> query = entityManager.createQuery(sql, ReservaProductoJpaEntity.class)
                .setParameter("reservaId", reservaId);

        return query.getResultList();
    }
}
