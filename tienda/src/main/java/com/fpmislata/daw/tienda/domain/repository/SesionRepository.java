package com.fpmislata.daw.tienda.domain.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.entity.SesionEntity;

public interface SesionRepository {
    Optional<SesionEntity> findByToken(String token);

    SesionEntity save(SesionEntity sesionEntity);

    void deleteByToken(String token);

}
