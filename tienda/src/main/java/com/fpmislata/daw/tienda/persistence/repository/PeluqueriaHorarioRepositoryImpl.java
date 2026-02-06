package com.fpmislata.daw.tienda.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.PeluqueriaHorarioRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaHorarioJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaHorarioJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.PeluqueriaHorarioMapper;

public class PeluqueriaHorarioRepositoryImpl implements PeluqueriaHorarioRepository {

    private final PeluqueriaHorarioJpaDao peluqueriaHorarioJpaDao;

    public PeluqueriaHorarioRepositoryImpl(PeluqueriaHorarioJpaDao peluqueriaHorarioJpaDao) {
        this.peluqueriaHorarioJpaDao = peluqueriaHorarioJpaDao;
    }

    @Override
    public PeluqueriaHorarioEntity save(PeluqueriaHorarioEntity peluqueriaHorarioEntity) {
        PeluqueriaHorarioJpaEntity jpaEntity = PeluqueriaHorarioMapper.getInstance()
                .fromEntityToJpa(peluqueriaHorarioEntity);
        if (peluqueriaHorarioEntity.id() == null) {
            return PeluqueriaHorarioMapper.getInstance().fromJpaToEntity(peluqueriaHorarioJpaDao.insert(jpaEntity));
        }
        return PeluqueriaHorarioMapper.getInstance().fromJpaToEntity(peluqueriaHorarioJpaDao.update(jpaEntity));
    }

    @Override
    public List<PeluqueriaHorarioEntity> findByPeluqueria(long peluqueriaId) {
        List<PeluqueriaHorarioJpaEntity> jpaEntities = peluqueriaHorarioJpaDao.findByPeluqueria(peluqueriaId);
        return jpaEntities.stream()
                .map(PeluqueriaHorarioMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public Optional<PeluqueriaHorarioEntity> findById(long id) {
        return peluqueriaHorarioJpaDao.findById(id)
                .map(PeluqueriaHorarioMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public void deleteById(long id) {
        peluqueriaHorarioJpaDao.delete(id);
    }

    @Override
    public List<PeluqueriaHorarioEntity> findByPeluqueriaAndDia(long peluqueriaId, int diaSemana) {
        return peluqueriaHorarioJpaDao.findByPeluqueriaAndDiaSemana(peluqueriaId, diaSemana).stream()
                .map(PeluqueriaHorarioMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

}
