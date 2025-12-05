package com.fpmislata.daw.tienda.domain.service;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;

public interface PeluqueriaService {

    Page<PeluqueriaDto> findAll(int page, int size);

    PeluqueriaDto getById(long id);

    Optional<PeluqueriaDto> findById(long id);

    PeluqueriaDto create(PeluqueriaDto peluqueriaDto);

    PeluqueriaDto update(PeluqueriaDto peluqueriaDto);

    void delete(long id);
}
