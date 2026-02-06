package com.fpmislata.daw.tienda.persistence.repository;

import java.util.List;

import com.fpmislata.daw.tienda.domain.repository.ReservaProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ReservaProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaProductoJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.ReservaProductoMapper;

public class ReservaProductoRepositoryImpl implements ReservaProductoRepository {

    private final ReservaProductoJpaDao reservaProductoJpaDao;

    public ReservaProductoRepositoryImpl(ReservaProductoJpaDao reservaProductoJpaDao) {
        this.reservaProductoJpaDao = reservaProductoJpaDao;
    }

    @Override
    public ReservaProductoEntity save(ReservaProductoEntity entity) {
        ReservaProductoJpaEntity jpaEntity = ReservaProductoMapper.getInstance().fromEntityToJpa(entity);

        return ReservaProductoMapper.getInstance().fromJpaToEntity(reservaProductoJpaDao.insert(jpaEntity));
    }

    @Override
    public List<ReservaProductoEntity> findByReserva(long reservaId) {
        List<ReservaProductoJpaEntity> jpaEntities = reservaProductoJpaDao.findByReserva(reservaId);

        return jpaEntities.stream()
                .map(ReservaProductoMapper.getInstance()::fromJpaToEntity)
                .toList();
    }
}
