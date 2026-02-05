package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.time.LocalDate;
import java.util.List;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaJpaEntity;

public interface ReservaJpaDao extends GenericJpaDao<ReservaJpaEntity> {

    List<ReservaJpaEntity> findByPeluqueriaAndFecha(long peluqueriaId, LocalDate fecha);
}
