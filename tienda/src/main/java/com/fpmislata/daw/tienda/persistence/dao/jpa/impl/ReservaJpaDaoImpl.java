package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.ReservaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class ReservaJpaDaoImpl implements ReservaJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReservaJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT r FROM ReservaJpaEntity r ORDER BY r.id";
        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setFirstResult(pageIndex * size).setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<ReservaJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(ReservaJpaEntity.class, id));
    }

    @Override
    @Transactional
    public ReservaJpaEntity insert(ReservaJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public ReservaJpaEntity update(ReservaJpaEntity entity) {
        ReservaJpaEntity managed = entityManager.find(ReservaJpaEntity.class, entity.getId());
        if (managed == null) {
            throw new RuntimeException("Reserva no encontrada");
        }
        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ReservaJpaEntity entity = entityManager.find(ReservaJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(r) FROM ReservaJpaEntity r", Long.class).getSingleResult();
    }

    @Override
    public List<ReservaJpaEntity> findByPeluqueriaAndFecha(long peluqueriaId, LocalDate fecha) {
        String sql = "SELECT r FROM ReservaJpaEntity r WHERE r.peluqueria.id = :peluqueriaId AND r.fechaReserva = :fecha";
        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class);
        query.setParameter("peluqueriaId", peluqueriaId);
        query.setParameter("fecha", fecha);
        return query.getResultList();
    }
}
