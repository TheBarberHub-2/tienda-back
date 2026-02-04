package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudProductoJpaEntity;

public class SolicitudProductoMapper {

    private static SolicitudProductoMapper INSTANCE;

    private SolicitudProductoMapper() {
    }

    public static SolicitudProductoMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SolicitudProductoMapper();
        }
        return INSTANCE;
    }

    public SolicitudProductoEntity fromJpaToEntity(SolicitudProductoJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new SolicitudProductoEntity(
                jpa.getId(),
                SolicitudMapper.getInstance().fromJpaToEntity(jpa.getSolicitud()),
                CategoriaMapper.getInstance().fromJpaToEntity(jpa.getCategoria()),
                jpa.getNombre(),
                jpa.getPrecio(),
                jpa.getDuracion());
    }

    public SolicitudProductoJpaEntity fromEntityToJpa(SolicitudProductoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new SolicitudProductoJpaEntity(
                entity.id(),
                SolicitudMapper.getInstance().fromEntityToJpa(entity.solicitud()),
                CategoriaMapper.getInstance().fromEntityToJpa(entity.categoria()),
                entity.nombre(),
                entity.precio(),
                entity.duracion());
    }
}