package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.List;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ProductoJpaEntity;

public interface ProductoJpaDao extends GenericJpaDao<ProductoJpaEntity> {

    List<ProductoJpaEntity> findByIds(List<Long> ids);

    List<ProductoJpaEntity> findByPeluqueria(long peluqueriaId);
}
