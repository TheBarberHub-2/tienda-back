package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaProductoJpaEntity;

public class ReservaProductoMapper {

    private static ReservaProductoMapper INSTANCE;

    private ReservaProductoMapper() {
    }

    public static ReservaProductoMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservaProductoMapper();
        }
        return INSTANCE;
    }

    public ReservaProductoJpaEntity fromEntityToJpa(ReservaProductoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ReservaProductoJpaEntity(
                entity.id(),
                ReservaMapper.getInstance().fromEntityToJpa(entity.reserva()),
                ProductoMapper.getInstance().fromEntityToJpa(entity.producto()));
    }

    public ReservaProductoEntity fromJpaToEntity(ReservaProductoJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new ReservaProductoEntity(
                jpa.getId(),
                ReservaMapper.getInstance().fromJpaToEntity(jpa.getReserva()),
                ProductoMapper.getInstance().fromJpaToEntity(jpa.getProducto()));
    }
}