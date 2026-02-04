package com.fpmislata.daw.tienda.persistence.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.SolicitudPeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudPeluqueriaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudPeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.repository.mapper.SolicitudPeluqueriaMapper;

public class SolicitudPeluqueriaRepositoryImpl implements SolicitudPeluqueriaRepository {

    private final SolicitudPeluqueriaJpaDao solicitudPeluqueriaJpaDao;

    public SolicitudPeluqueriaRepositoryImpl(SolicitudPeluqueriaJpaDao solicitudPeluqueriaJpaDao) {
        this.solicitudPeluqueriaJpaDao = solicitudPeluqueriaJpaDao;
    }

    @Override
    public Optional<SolicitudPeluqueriaEntity> findById(long id) {
        return solicitudPeluqueriaJpaDao.findById(id)
                .map(SolicitudPeluqueriaMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public SolicitudPeluqueriaEntity save(SolicitudPeluqueriaEntity entity) {
        var jpa = SolicitudPeluqueriaMapper.getInstance().fromEntityToJpa(entity);
        return SolicitudPeluqueriaMapper.getInstance()
                .fromJpaToEntity(solicitudPeluqueriaJpaDao.insert(jpa));
    }
}