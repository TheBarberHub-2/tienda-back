package com.fpmislata.daw.tienda.persistence.dao.jpa.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.enums.EstadoReserva;
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

    @Override
    public List<ReservaJpaEntity> findByCliente(long clienteId) {
        String sql = "SELECT r FROM ReservaJpaEntity r WHERE r.cliente.id = :clienteId ORDER BY r.fechaReserva, r.horaInicio";

        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setParameter("clienteId", clienteId);

        return query.getResultList();
    }

    @Override
    public List<ReservaJpaEntity> findByClienteAndEstado(long clienteId, EstadoReserva estado) {
        String sql = "SELECT r FROM ReservaJpaEntity r WHERE r.cliente.id = :clienteId AND r.estado = :estado ORDER BY r.fechaReserva, r.horaInicio";

        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setParameter("clienteId", clienteId)
                .setParameter("estado", estado);

        return query.getResultList();
    }

    @Override
    public List<ReservaJpaEntity> findByPeluqueria(long peluqueriaId) {
        String sql = "SELECT r FROM ReservaJpaEntity r WHERE r.peluqueria.id = :peluqueriaId ORDER BY r.fechaReserva, r.horaInicio";

        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setParameter("peluqueriaId", peluqueriaId);

        return query.getResultList();
    }

    @Override
    public List<ReservaJpaEntity> findByPeluqueriaAndEstado(long peluqueriaId, EstadoReserva estado) {
        String sql = "SELECT r FROM ReservaJpaEntity r WHERE r.peluqueria.id = :peluqueriaId AND r.estado = :estado ORDER BY r.fechaReserva, r.horaInicio";

        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setParameter("peluqueriaId", peluqueriaId)
                .setParameter("estado", estado);

        return query.getResultList();
    }

    @Override
    public Optional<ReservaJpaEntity> findByIdAndClienteId(long reservaId, long clienteId) {
        String sql = "SELECT r FROM ReservaJpaEntity r WHERE r.id = :reservaId AND r.cliente.id = :clienteId";

        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setParameter("reservaId", reservaId)
                .setParameter("clienteId", clienteId);

        List<ReservaJpaEntity> results = query.getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<ReservaJpaEntity> findByIdAndPeluqueriaId(long reservaId, long peluqueriaId) {
        String sql = "SELECT r FROM ReservaJpaEntity r " +
                "WHERE r.id = :reservaId AND r.peluqueria.id = :peluqueriaId";

        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setParameter("reservaId", reservaId)
                .setParameter("peluqueriaId", peluqueriaId);

        List<ReservaJpaEntity> results = query.getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<ReservaJpaEntity> findByPeluqueriaAndDia(long peluqueriaId, int diaSemana) {
        String sql = "SELECT r FROM ReservaJpaEntity r WHERE r.peluqueria.id = :peluqueriaId AND r.diaSemana = :diaSemana AND r.estado = 'Pendiente'";

        TypedQuery<ReservaJpaEntity> query = entityManager.createQuery(sql, ReservaJpaEntity.class)
                .setParameter("peluqueriaId", peluqueriaId)
                .setParameter("diaSemana", diaSemana);

        List<ReservaJpaEntity> results = query.getResultList();

        return results;
    }
}
