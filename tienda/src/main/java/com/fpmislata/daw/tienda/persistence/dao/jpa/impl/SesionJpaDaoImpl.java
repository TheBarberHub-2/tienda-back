package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.persistence.dao.jpa.SesionJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SesionJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class SesionJpaDaoImpl implements SesionJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SesionJpaEntity> findByToken(String token) {
        String sql = "SELECT s FROM SesionJpaEntity s WHERE s.token = :token";
        TypedQuery<SesionJpaEntity> query = entityManager.createQuery(sql, SesionJpaEntity.class);
        query.setParameter("token", token);

        List<SesionJpaEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public SesionJpaEntity insert(SesionJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void deleteByToken(String token) {
        String sql = "DELETE FROM SesionJpaEntity s WHERE s.token = :token";
        entityManager.createQuery(sql)
                .setParameter("token", token)
                .executeUpdate();
    }
}
