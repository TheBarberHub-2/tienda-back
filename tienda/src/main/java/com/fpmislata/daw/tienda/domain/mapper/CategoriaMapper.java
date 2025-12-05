package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Categoria;
import com.fpmislata.daw.tienda.domain.repository.entity.CategoriaEntity;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;

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

    public CategoriaEntity fromModelToEntity(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoriaEntity(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion());
    }

    public Categoria fromEntityToModel(CategoriaEntity categoriaEntity) {
        if (categoriaEntity == null) {
            return null;
        }
        return new Categoria(
                categoriaEntity.id(),
                categoriaEntity.nombre(),
                categoriaEntity.descripcion());
    }

    public Categoria fromDtoToModel(CategoriaDto categoriaDto) {
        if (categoriaDto == null) {
            return null;
        }
        return new Categoria(
                categoriaDto.id(),
                categoriaDto.nombre(),
                categoriaDto.descripcion());
    }

    public CategoriaDto fromModelToDto(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion());
    }
}
