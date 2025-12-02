package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.exception.ResourceNotFoundException;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class PeluqueriaJpaDaoImpl implements PeluqueriaJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PeluqueriaJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT p FROM PeluqueriaJpaEntity p ORDER BY p.id";
        TypedQuery<PeluqueriaJpaEntity> query = entityManager.createQuery(sql, PeluqueriaJpaEntity.class)
                .setFirstResult(pageIndex * size).setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<PeluqueriaJpaEntity> findById(int id) {
        return Optional.ofNullable(entityManager.find(PeluqueriaJpaEntity.class, id));
    }

    @Override
    public PeluqueriaJpaEntity insert(PeluqueriaJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public PeluqueriaJpaEntity update(PeluqueriaJpaEntity entity) {
        PeluqueriaJpaEntity managed = entityManager.find(PeluqueriaJpaEntity.class, entity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Peluqueria with id " + entity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(PeluqueriaJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(p) FROM PeluqueriaJpaEntity p", Long.class)
                .getSingleResult();
    }

}