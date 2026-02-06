package com.fpmislata.daw.tienda.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ProductoJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.ProductoMapper;

public class ProductoRepositoryImpl implements ProductoRepository {

    private final ProductoJpaDao productoJpaDao;

    public ProductoRepositoryImpl(ProductoJpaDao productoJpaDao) {
        this.productoJpaDao = productoJpaDao;
    }

    @Override
    public Page<ProductoEntity> findAll(int page, int size) {
        List<ProductoEntity> productos = productoJpaDao.findAll(page, size).stream()
                .map(ProductoMapper.getInstance()::fromJpaToEntity)
                .toList();
        long totalElements = productoJpaDao.count();
        return new Page<>(productos, page, size, totalElements);
    }

    @Override
    public Optional<ProductoEntity> findById(long id) {
        return productoJpaDao.findById(id)
                .map(ProductoMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public ProductoEntity save(ProductoEntity productoEntity) {
        ProductoJpaEntity jpaEntity = ProductoMapper.getInstance().fromEntityToJpa(productoEntity);
        if (productoEntity.id() == null) {
            return ProductoMapper.getInstance().fromJpaToEntity(productoJpaDao.insert(jpaEntity));
        }
        return ProductoMapper.getInstance().fromJpaToEntity(productoJpaDao.update(jpaEntity));
    }

    @Override
    public void deleteById(long id) {
        productoJpaDao.delete(id);
    }

    @Override
    public List<ProductoEntity> findByIds(List<Long> ids) {
        return productoJpaDao.findByIds(ids).stream()
                .map(ProductoMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<ProductoEntity> findByPeluqueria(long peluqueriaId) {
        return productoJpaDao.findByPeluqueria(peluqueriaId).stream()
                .map(ProductoMapper.getInstance()::fromJpaToEntity)
                .toList();
    }
}
