package com.fpmislata.daw.tienda.domain.service;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.Rol;

public interface UsuarioService {

    Page<UsuarioDto> findAll(int page, int size);

    List<UsuarioDto> getAll();

    UsuarioDto getById(long id);

    UsuarioDto getByEmail(String email);

    Optional<UsuarioDto> findById(long id);

    UsuarioDto create(UsuarioDto usuarioDto);

    UsuarioDto update(UsuarioDto usuarioDto);

    void delete(long id);

    UsuarioDto updateRol(long usuarioId, Rol rol);
}
