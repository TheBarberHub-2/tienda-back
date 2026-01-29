package com.fpmislata.daw.tienda.domain.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;

public interface PeluqueriaHorarioRepository {

    Optional<PeluqueriaHorarioEntity> findById(long id);

    PeluqueriaHorarioEntity save(PeluqueriaHorarioEntity peluqueriaHorarioEntity);

    List<PeluqueriaHorarioEntity> findByPeluqueria(long peluqueriaId);

    void deleteById(long id);
}
