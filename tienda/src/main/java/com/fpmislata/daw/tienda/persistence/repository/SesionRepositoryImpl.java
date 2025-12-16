package com.fpmislata.daw.tienda.persistence.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.SesionRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SesionEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SesionJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SesionJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.SesionMapper;

public class SesionRepositoryImpl implements SesionRepository {

    private final SesionJpaDao sesionJpaDao;

    public SesionRepositoryImpl(SesionJpaDao sesionJpaDao) {
        this.sesionJpaDao = sesionJpaDao;
    }

    @Override
    public Optional<SesionEntity> findByToken(String token) {

        return sesionJpaDao.findByToken(token).map(SesionMapper.getInstance()::fromJpaToEntity);

    }

    @Override
    public SesionEntity save(SesionEntity sesionEntity) {
        SesionJpaEntity jpaEntity = SesionMapper.getInstance().fromEntityToJpa(sesionEntity);
        return SesionMapper.getInstance().fromJpaToEntity(sesionJpaDao.insert(jpaEntity));

    }

    @Override
    public void deleteByToken(String token) {
        sesionJpaDao.deleteByToken(token);
    }
}
