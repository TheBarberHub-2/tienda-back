package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.SolicitudPeluqueria;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudPeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudPeluqueriaDto;

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

    // ENTITY → MODEL
    public SolicitudPeluqueria fromEntityToModel(SolicitudPeluqueriaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new SolicitudPeluqueria(
                entity.id(),
                SolicitudMapper.getInstance().fromEntityToModel(entity.solicitud()),
                entity.municipio(),
                entity.direccion(),
                entity.telefono());
    }

    // MODEL → ENTITY
    public SolicitudPeluqueriaEntity fromModelToEntity(SolicitudPeluqueria model) {
        if (model == null) {
            return null;
        }

        return new SolicitudPeluqueriaEntity(
                model.getId(),
                SolicitudMapper.getInstance().fromModelToEntity(model.getSolicitud()),
                model.getMunicipio(),
                model.getDireccion(),
                model.getTelefono());
    }

    // DTO → MODEL
    public SolicitudPeluqueria fromDtoToModel(SolicitudPeluqueriaDto dto) {
        if (dto == null) {
            return null;
        }

        return new SolicitudPeluqueria(
                dto.id(),
                SolicitudMapper.getInstance().fromDtoToModel(dto.solicitud()),
                dto.municipio(),
                dto.direccion(),
                dto.telefono());
    }

    // MODEL → DTO
    public SolicitudPeluqueriaDto fromModelToDto(SolicitudPeluqueria model) {
        if (model == null) {
            return null;
        }

        return new SolicitudPeluqueriaDto(
                model.getId(),
                SolicitudMapper.getInstance().fromModelToDto(model.getSolicitud()),
                model.getMunicipio(),
                model.getDireccion(),
                model.getTelefono());
    }
}