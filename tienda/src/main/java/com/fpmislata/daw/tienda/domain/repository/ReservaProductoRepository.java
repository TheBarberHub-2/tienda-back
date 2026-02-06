package com.fpmislata.daw.tienda.domain.repository;

import java.util.List;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;

public interface ReservaProductoRepository {

    ReservaProductoEntity save(ReservaProductoEntity entity);

    List<ReservaProductoEntity> findByReserva(long reservaId);
}
