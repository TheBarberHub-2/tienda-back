package com.fpmislata.daw.tienda.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;

public interface ReservaRepository {

    Optional<ReservaEntity> findById(long id);

    ReservaEntity save(ReservaEntity reservaEntity);

    void deleteById(long id);

    List<ReservaEntity> findByPeluqueriaAndFecha(long peluqueriaId, LocalDate fecha);
}
