package com.fpmislata.daw.tienda.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.ReservaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.enums.EstadoReserva;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ReservaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.ReservaMapper;

public class ReservaRepositoryImpl implements ReservaRepository {
    private final ReservaJpaDao reservaJpaDao;

    public ReservaRepositoryImpl(ReservaJpaDao reservaJpaDao) {
        this.reservaJpaDao = reservaJpaDao;
    }

    @Override
    public Optional<ReservaEntity> findById(long id) {
        return reservaJpaDao.findById(id)
                .map(ReservaMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public ReservaEntity save(ReservaEntity reservaEntity) {
        ReservaJpaEntity jpaEntity = ReservaMapper.getInstance().fromEntityToJpa(reservaEntity);

        if (reservaEntity.id() == null) {
            return ReservaMapper.getInstance().fromJpaToEntity(reservaJpaDao.insert(jpaEntity));
        }

        return ReservaMapper.getInstance().fromJpaToEntity(reservaJpaDao.update(jpaEntity));
    }

    @Override
    public void deleteById(long id) {
        reservaJpaDao.delete(id);
    }

    @Override
    public List<ReservaEntity> findByPeluqueriaAndFecha(long peluqueriaId, LocalDate fecha) {
        return reservaJpaDao.findByPeluqueriaAndFecha(peluqueriaId, fecha).stream()
                .map(ReservaMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<ReservaEntity> findByCliente(long clienteId) {
        return reservaJpaDao.findByCliente(clienteId).stream()
                .map(ReservaMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<ReservaEntity> findByClienteAndEstado(long clienteId, EstadoReserva estado) {
        return reservaJpaDao.findByClienteAndEstado(clienteId, estado).stream()
                .map(ReservaMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<ReservaEntity> findByPeluqueria(long peluqueriaId) {
        return reservaJpaDao.findByPeluqueria(peluqueriaId).stream()
                .map(ReservaMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<ReservaEntity> findByPeluqueriaAndEstado(long peluqueriaId, EstadoReserva estado) {
        return reservaJpaDao.findByPeluqueriaAndEstado(peluqueriaId, estado).stream()
                .map(ReservaMapper.getInstance()::fromJpaToEntity)
                .toList();
    }
}
