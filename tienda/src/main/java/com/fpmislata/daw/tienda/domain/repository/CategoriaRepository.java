package com.fpmislata.daw.tienda.domain.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.entity.CategoriaEntity;

public interface CategoriaRepository {

    Page<CategoriaEntity> findAll(int page, int size);

    Optional<CategoriaEntity> findById(long id);

    CategoriaEntity save(CategoriaEntity categoriaEntity);

    void deleteById(long id);
}
