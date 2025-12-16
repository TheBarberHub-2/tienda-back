package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;
import com.fpmislata.daw.tienda.persistence.dao.jpa.UsuarioJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.UsuarioJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class UsuarioJpaDaoImpl implements UsuarioJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UsuarioJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT u FROM UsuarioJpaEntity u ORDER BY u.id";
        TypedQuery<UsuarioJpaEntity> query = entityManager.createQuery(sql, UsuarioJpaEntity.class)
                .setFirstResult(pageIndex * size).setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<UsuarioJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(UsuarioJpaEntity.class, id));
    }

    @Override
    public UsuarioJpaEntity insert(UsuarioJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public UsuarioJpaEntity update(UsuarioJpaEntity entity) {
        UsuarioJpaEntity managed = entityManager.find(UsuarioJpaEntity.class, entity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Usuario with id " + entity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(UsuarioJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(u) FROM UsuarioJpaEntity u", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<UsuarioJpaEntity> findByEmail(String email) {
        String sql = "SELECT u FROM UsuarioJpaEntity u WHERE u.email = :email";
        TypedQuery<UsuarioJpaEntity> query = entityManager.createQuery(sql, UsuarioJpaEntity.class);
        query.setParameter("email", email);

        List<UsuarioJpaEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }
}
