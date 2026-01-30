package com.fpmislata.daw.tienda.domain.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudPeluqueriaEntity;

public interface SolicitudPeluqueriaRepository {

    Optional<SolicitudPeluqueriaEntity> findById(long id);

    SolicitudPeluqueriaEntity save(SolicitudPeluqueriaEntity solicitudPeluqueria);
}