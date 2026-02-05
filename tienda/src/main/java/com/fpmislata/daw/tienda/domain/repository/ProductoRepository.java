package com.fpmislata.daw.tienda.domain.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;

public interface ProductoRepository {

    Page<ProductoEntity> findAll(int page, int size);

    Optional<ProductoEntity> findById(long id);

    List<ProductoEntity> findByIds(List<Long> ids);

    List<ProductoEntity> findByPeluqueria(long peluqueriaId);

    ProductoEntity save(ProductoEntity productoEntity);

    void deleteById(long id);
}
