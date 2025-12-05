package com.fpmislata.daw.tienda.domain.service;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;

public interface CategoriaService {

    Page<CategoriaDto> findAll(int page, int size);

    CategoriaDto getById(long id);

    Optional<CategoriaDto> findById(long id);

    CategoriaDto create(CategoriaDto categoriaDto);

    CategoriaDto update(CategoriaDto categoriaDto);

    void delete(long id);
}
