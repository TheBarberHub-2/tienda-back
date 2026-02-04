package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.ProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ProductoJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class ProductoJpaDaoImpl implements ProductoJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductoJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT p FROM ProductoJpaEntity p ORDER BY p.id";
        TypedQuery<ProductoJpaEntity> query = entityManager.createQuery(sql, ProductoJpaEntity.class)
                .setFirstResult(pageIndex * size).setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<ProductoJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(ProductoJpaEntity.class, id));
    }

    @Override
    @Transactional
    public ProductoJpaEntity insert(ProductoJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public ProductoJpaEntity update(ProductoJpaEntity entity) {
        ProductoJpaEntity managed = entityManager.find(ProductoJpaEntity.class, entity.getId());
        if (managed == null) {
            throw new RuntimeException("Producto with id " + entity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(entityManager.find(ProductoJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(p) FROM ProductoJpaEntity p", Long.class)
                .getSingleResult();
    }

}
