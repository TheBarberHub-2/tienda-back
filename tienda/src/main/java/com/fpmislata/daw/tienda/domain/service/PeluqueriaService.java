package com.fpmislata.daw.tienda.domain.service;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;

public interface PeluqueriaService {

    Page<PeluqueriaDto> findAll(int page, int size);

    List<PeluqueriaDto> getAll();

    Optional<List<UsuarioDto>> findAvailablePeluquerias();

    PeluqueriaDto getById(long id);

    Optional<PeluqueriaDto> findById(long id);

    PeluqueriaDto create(PeluqueriaDto peluqueriaDto);

    PeluqueriaDto update(PeluqueriaDto peluqueriaDto);

    void delete(long id);
}
