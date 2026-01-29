package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.List;

import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaHorarioJpaEntity;

public interface PeluqueriaHorarioJpaDao extends GenericJpaDao<PeluqueriaHorarioJpaEntity> {
    List<PeluqueriaHorarioJpaEntity> findByPeluqueria(long peluqueriaId);
}