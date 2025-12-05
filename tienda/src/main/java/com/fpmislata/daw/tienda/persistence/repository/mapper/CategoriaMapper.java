package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.CategoriaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.CategoriaJpaEntity;

public class CategoriaMapper {

    private static CategoriaMapper INSTANCE;

    private CategoriaMapper() {
    }

    public static CategoriaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoriaMapper();
        }
        return INSTANCE;
    }

    public CategoriaJpaEntity fromEntityToJpa(CategoriaEntity categoriaEntity) {
        if (categoriaEntity == null) {
            return null;
        }
        return new CategoriaJpaEntity(
                categoriaEntity.id(),
                categoriaEntity.nombre(),
                categoriaEntity.descripcion());
    }

    public CategoriaEntity fromJpaToEntity(CategoriaJpaEntity categoriaJpaEntity) {
        if (categoriaJpaEntity == null) {
            return null;
        }
        return new CategoriaEntity(
                categoriaJpaEntity.getId(),
                categoriaJpaEntity.getNombre(),
                categoriaJpaEntity.getDescripcion());
    }
}
