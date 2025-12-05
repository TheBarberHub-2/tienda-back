package com.fpmislata.daw.tienda.domain.service;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;

public interface UsuarioService {

    Page<UsuarioDto> findAll(int page, int size);

    UsuarioDto getById(long id);

    Optional<UsuarioDto> findById(long id);

    UsuarioDto create(UsuarioDto usuarioDto);

    UsuarioDto update(UsuarioDto usuarioDto);

    void delete(long id);
}
