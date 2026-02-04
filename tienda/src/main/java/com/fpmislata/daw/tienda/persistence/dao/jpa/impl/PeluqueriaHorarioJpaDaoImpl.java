package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaHorarioJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaHorarioJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class PeluqueriaHorarioJpaDaoImpl implements PeluqueriaHorarioJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PeluqueriaHorarioJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT p FROM PeluqueriaHorarioJpaEntity p ORDER BY p.id";
        TypedQuery<PeluqueriaHorarioJpaEntity> query = entityManager.createQuery(sql, PeluqueriaHorarioJpaEntity.class)
                .setFirstResult(pageIndex * size).setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<PeluqueriaHorarioJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(PeluqueriaHorarioJpaEntity.class, id));
    }

    @Override
    @Transactional
    public PeluqueriaHorarioJpaEntity insert(PeluqueriaHorarioJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public PeluqueriaHorarioJpaEntity update(PeluqueriaHorarioJpaEntity entity) {
        PeluqueriaHorarioJpaEntity managed = entityManager.find(PeluqueriaHorarioJpaEntity.class, entity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Peluqueria Horario with id " + entity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String sql = "DELETE FROM PeluqueriaHorarioJpaEntity p WHERE p.id = :id";
        entityManager.createQuery(sql)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(p) FROM PeluqueriaHorarioJpaEntity p", Long.class)
                .getSingleResult();
    }

    @Override
    public List<PeluqueriaHorarioJpaEntity> findByPeluqueria(long peluqueriaId) {
        String sql = "SELECT p FROM PeluqueriaHorarioJpaEntity p WHERE p.peluqueria.id = :peluqueriaId";
        return entityManager.createQuery(sql, PeluqueriaHorarioJpaEntity.class)
                .setParameter("peluqueriaId", peluqueriaId)
                .getResultList();
    }

}
