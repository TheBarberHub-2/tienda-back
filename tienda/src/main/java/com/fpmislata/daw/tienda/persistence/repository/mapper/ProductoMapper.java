package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ProductoJpaEntity;

public class ProductoMapper {

    private static ProductoMapper INSTANCE;

    private ProductoMapper() {
    }

    public static ProductoMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductoMapper();
        }
        return INSTANCE;
    }

    public ProductoJpaEntity fromEntityToJpa(ProductoEntity productoEntity) {
        if (productoEntity == null) {
            return null;
        }
        return new ProductoJpaEntity(
                productoEntity.id(),
                CategoriaMapper.getInstance().fromEntityToJpa(productoEntity.categoria()),
                PeluqueriaMapper.getInstance().fromEntityToJpa(productoEntity.peluqueria()),
                productoEntity.nombre(),
                productoEntity.precio(),
                productoEntity.duracion());
    }

    public ProductoEntity fromJpaToEntity(ProductoJpaEntity productoJpaEntity) {
        if (productoJpaEntity == null) {
            return null;
        }
        return new ProductoEntity(
                productoJpaEntity.getId(),
                CategoriaMapper.getInstance().fromJpaToEntity(productoJpaEntity.getCategoria()),
                PeluqueriaMapper.getInstance().fromJpaToEntity(productoJpaEntity.getPeluqueria()),
                productoJpaEntity.getNombre(),
                productoJpaEntity.getPrecio(),
                productoJpaEntity.getDuracion());
    }
}
