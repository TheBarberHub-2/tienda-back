package com.fpmislata.daw.tienda.persistence.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.SolicitudProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.repository.mapper.SolicitudProductoMapper;

public class SolicitudProductoRepositoryImpl implements SolicitudProductoRepository {

    private final SolicitudProductoJpaDao solicitudProductoJpaDao;

    public SolicitudProductoRepositoryImpl(SolicitudProductoJpaDao solicitudProductoJpaDao) {
        this.solicitudProductoJpaDao = solicitudProductoJpaDao;
    }

    @Override
    public Optional<SolicitudProductoEntity> findById(long id) {
        return solicitudProductoJpaDao.findById(id)
                .map(SolicitudProductoMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public SolicitudProductoEntity save(SolicitudProductoEntity entity) {
        var jpa = SolicitudProductoMapper.getInstance().fromEntityToJpa(entity);
        return SolicitudProductoMapper.getInstance()
                .fromJpaToEntity(solicitudProductoJpaDao.insert(jpa));
    }

    @Override
    public Optional<SolicitudProductoEntity> findBySolicitud(long solicitudId) {
        return solicitudProductoJpaDao.findBySolicitud(solicitudId)
                .map(SolicitudProductoMapper.getInstance()::fromJpaToEntity);
    }
}