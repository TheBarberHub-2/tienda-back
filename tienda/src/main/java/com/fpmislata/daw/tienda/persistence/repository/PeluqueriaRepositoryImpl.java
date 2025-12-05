package com.fpmislata.daw.tienda.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.PeluqueriaMapper;

public class PeluqueriaRepositoryImpl implements PeluqueriaRepository {

    private final PeluqueriaJpaDao peluqueriaJpaDao;

    public PeluqueriaRepositoryImpl(PeluqueriaJpaDao peluqueriaJpaDao) {
        this.peluqueriaJpaDao = peluqueriaJpaDao;
    }

    @Override
    public Page<PeluqueriaEntity> findAll(int page, int size) {
        List<PeluqueriaEntity> peluquerias = peluqueriaJpaDao.findAll(page, size).stream()
                .map(PeluqueriaMapper.getInstance()::fromJpaToEntity)
                .toList();
        long totalElements = peluqueriaJpaDao.count();
        return new Page<>(peluquerias, page, size, totalElements);
    }

    @Override
    public Optional<PeluqueriaEntity> findById(long id) {
        return peluqueriaJpaDao.findById(id)
                .map(PeluqueriaMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public PeluqueriaEntity save(PeluqueriaEntity peluqueriaEntity) {
        PeluqueriaJpaEntity jpaEntity = PeluqueriaMapper.getInstance().fromEntityToJpa(peluqueriaEntity);
        if (peluqueriaEntity.id() == null) {
            return PeluqueriaMapper.getInstance().fromJpaToEntity(peluqueriaJpaDao.insert(jpaEntity));
        }
        return PeluqueriaMapper.getInstance().fromJpaToEntity(peluqueriaJpaDao.update(jpaEntity));
    }

    @Override
    public void deleteById(long id) {
        peluqueriaJpaDao.delete(id);
    }

    @Override
    public Optional<PeluqueriaEntity> findByUsuario(long usuarioId) {
        return peluqueriaJpaDao.findByUsuario(usuarioId)
                .map(PeluqueriaMapper.getInstance()::fromJpaToEntity);
    }
}
