package com.fpmislata.daw.tienda.domain.service;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;

public interface ProductoService {

    Page<ProductoDto> findAll(int page, int size);

    ProductoDto getById(long id);

    Optional<ProductoDto> findById(long id);

    ProductoDto create(ProductoDto productoDto);

    ProductoDto update(ProductoDto productoDto);

    void delete(long id);

    List<ProductoDto> findByIds(List<Long> ids);
}
