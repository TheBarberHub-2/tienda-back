package com.fpmislata.daw.tienda.domain.repository;

import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;

public interface SolicitudProductoRepository {

    Optional<SolicitudProductoEntity> findById(long id);

    SolicitudProductoEntity save(SolicitudProductoEntity solicitudProducto);
}