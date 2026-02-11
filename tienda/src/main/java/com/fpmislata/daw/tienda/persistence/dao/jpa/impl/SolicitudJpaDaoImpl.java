package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class SolicitudJpaDaoImpl implements SolicitudJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SolicitudJpaEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(SolicitudJpaEntity.class, id));
    }

    @Override
    @Transactional
    public SolicitudJpaEntity insert(SolicitudJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public SolicitudJpaEntity update(SolicitudJpaEntity entity) {
        SolicitudJpaEntity managed = entityManager.find(SolicitudJpaEntity.class, entity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Solicitud no encontrada");
        }
        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    public List<SolicitudJpaEntity> findByUsuario(long usuarioId) {
        String sql = "SELECT s FROM SolicitudJpaEntity s WHERE s.usuario.id = :usuarioId";
        TypedQuery<SolicitudJpaEntity> query = entityManager.createQuery(sql, SolicitudJpaEntity.class)
                .setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }

    @Override
    public List<SolicitudJpaEntity> findByTipoAndEstado(TipoSolicitud tipo, EstadoSolicitud estado) {
        String sql = "SELECT s FROM SolicitudJpaEntity s WHERE s.tipo = :tipo AND s.estado = :estado";
        TypedQuery<SolicitudJpaEntity> query = entityManager.createQuery(sql, SolicitudJpaEntity.class)
                .setParameter("tipo", tipo)
                .setParameter("estado", estado);
        return query.getResultList();
    }

    @Override
    public List<SolicitudJpaEntity> findPendientes() {
        String sql = "SELECT s FROM SolicitudJpaEntity s WHERE s.estado = 'Pendiente'";
        return entityManager.createQuery(sql, SolicitudJpaEntity.class).getResultList();
    }

    @Override
    public List<SolicitudJpaEntity> findAprobadas() {
        String sql = "SELECT s FROM SolicitudJpaEntity s WHERE s.estado = 'Aprobada'";
        return entityManager.createQuery(sql, SolicitudJpaEntity.class).getResultList();
    }

    @Override
    public List<SolicitudJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT s FROM SolicitudJpaEntity s ORDER BY s.id";
        TypedQuery<SolicitudJpaEntity> query = entityManager.createQuery(sql, SolicitudJpaEntity.class)
                .setFirstResult(pageIndex * size).setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(s) FROM SolicitudJpaEntity s", Long.class)
                .getSingleResult();
    }

}
