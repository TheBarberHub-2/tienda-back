package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudPeluqueriaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudPeluqueriaJpaEntity;

public class SolicitudPeluqueriaMapper {

    private static SolicitudPeluqueriaMapper INSTANCE;

    private SolicitudPeluqueriaMapper() {
    }

    public static SolicitudPeluqueriaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SolicitudPeluqueriaMapper();
        }
        return INSTANCE;
    }

    public SolicitudPeluqueriaEntity fromJpaToEntity(SolicitudPeluqueriaJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new SolicitudPeluqueriaEntity(
                jpa.getId(),
                SolicitudMapper.getInstance().fromJpaToEntity(jpa.getSolicitud()),
                jpa.getMunicipio(),
                jpa.getDireccion(),
                jpa.getTelefono());
    }

    public SolicitudPeluqueriaJpaEntity fromEntityToJpa(SolicitudPeluqueriaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new SolicitudPeluqueriaJpaEntity(
                entity.id(),
                SolicitudMapper.getInstance().fromEntityToJpa(entity.solicitud()),
                entity.municipio(),
                entity.direccion(),
                entity.telefono());
    }
}