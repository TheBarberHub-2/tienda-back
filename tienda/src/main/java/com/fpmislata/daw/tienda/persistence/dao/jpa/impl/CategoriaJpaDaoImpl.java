package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.CategoriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.CategoriaJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CategoriaJpaDaoImpl implements CategoriaJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoriaJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT c FROM CategoriaJpaEntity c ORDER BY c.id";
        TypedQuery<CategoriaJpaEntity> query = entityManager.createQuery(sql, CategoriaJpaEntity.class)
                .setFirstResult(pageIndex * size).setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<CategoriaJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(CategoriaJpaEntity.class, id));
    }

    @Override
    public CategoriaJpaEntity insert(CategoriaJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public CategoriaJpaEntity update(CategoriaJpaEntity entity) {
        CategoriaJpaEntity managed = entityManager.find(CategoriaJpaEntity.class, entity.getId());
        if (managed == null) {
            throw new RuntimeException("Categoria with id " + entity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(CategoriaJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(c) FROM CategoriaJpaEntity c", Long.class)
                .getSingleResult();
    }
}
