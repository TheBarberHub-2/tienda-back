package com.fpmislata.daw.tienda.domain.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;

public interface UsuarioRepository {

    Page<UsuarioEntity> findAll(int page, int size);

    Optional<UsuarioEntity> findById(long id);

    Optional<UsuarioEntity> findByEmail(String email);

    UsuarioEntity save(UsuarioEntity usuarioEntity);

    void deleteById(long id);
}
