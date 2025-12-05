package com.fpmislata.daw.tienda.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.CategoriaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.CategoriaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.CategoriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.CategoriaJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.CategoriaMapper;

public class CategoriaRepositoryImpl implements CategoriaRepository {

    private final CategoriaJpaDao categoriaJpaDao;

    public CategoriaRepositoryImpl(CategoriaJpaDao categoriaJpaDao) {
        this.categoriaJpaDao = categoriaJpaDao;
    }

    @Override
    public Page<CategoriaEntity> findAll(int page, int size) {
        List<CategoriaEntity> categorias = categoriaJpaDao.findAll(page, size).stream()
                .map(CategoriaMapper.getInstance()::fromJpaToEntity)
                .toList();
        long totalElements = categoriaJpaDao.count();
        return new Page<>(categorias, page, size, totalElements);
    }

    @Override
    public Optional<CategoriaEntity> findById(long id) {
        return categoriaJpaDao.findById(id)
                .map(CategoriaMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public CategoriaEntity save(CategoriaEntity categoriaEntity) {
        CategoriaJpaEntity jpaEntity = CategoriaMapper.getInstance().fromEntityToJpa(categoriaEntity);
        if (categoriaEntity.id() == null) {
            return CategoriaMapper.getInstance().fromJpaToEntity(categoriaJpaDao.insert(jpaEntity));
        }
        return CategoriaMapper.getInstance().fromJpaToEntity(categoriaJpaDao.update(jpaEntity));
    }

    @Override
    public void deleteById(long id) {
        categoriaJpaDao.delete(id);
    }
}
