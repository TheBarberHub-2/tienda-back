package com.fpmislata.daw.tienda.domain.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;

public interface PeluqueriaRepository {

    Page<PeluqueriaEntity> findAll(int page, int size);

    Optional<PeluqueriaEntity> findById(long id);

    Optional<PeluqueriaEntity> findByUsuario(long usuarioId);

    PeluqueriaEntity save(PeluqueriaEntity peluqueriaEntity);

    void deleteById(long id);
}
