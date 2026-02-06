package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.List;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaProductoJpaEntity;

public interface ReservaProductoJpaDao {

    ReservaProductoJpaEntity insert(ReservaProductoJpaEntity entity);

    List<ReservaProductoJpaEntity> findByReserva(long reservaId);
}
