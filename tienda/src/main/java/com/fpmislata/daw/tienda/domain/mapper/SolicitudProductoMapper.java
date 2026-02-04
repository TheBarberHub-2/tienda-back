package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.SolicitudProducto;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudProductoDto;

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

    // ENTITY → MODEL
    public SolicitudProducto fromEntityToModel(SolicitudProductoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new SolicitudProducto(
                entity.id(),
                SolicitudMapper.getInstance().fromEntityToModel(entity.solicitud()),
                CategoriaMapper.getInstance().fromEntityToModel(entity.categoria()),
                entity.nombre(),
                entity.precio(),
                entity.duracion());
    }

    // MODEL → ENTITY
    public SolicitudProductoEntity fromModelToEntity(SolicitudProducto model) {
        if (model == null) {
            return null;
        }

        return new SolicitudProductoEntity(
                model.getId(),
                SolicitudMapper.getInstance().fromModelToEntity(model.getSolicitud()),
                CategoriaMapper.getInstance().fromModelToEntity(model.getCategoria()),
                model.getNombre(),
                model.getPrecio(),
                model.getDuracion());
    }

    // DTO → MODEL
    public SolicitudProducto fromDtoToModel(SolicitudProductoDto dto) {
        if (dto == null) {
            return null;
        }

        return new SolicitudProducto(
                dto.id(),
                SolicitudMapper.getInstance().fromDtoToModel(dto.solicitud()),
                CategoriaMapper.getInstance().fromDtoToModel(dto.categoria()),
                dto.nombre(),
                dto.precio(),
                dto.duracion());
    }

    // MODEL → DTO
    public SolicitudProductoDto fromModelToDto(SolicitudProducto model) {
        if (model == null) {
            return null;
        }

        return new SolicitudProductoDto(
                model.getId(),
                SolicitudMapper.getInstance().fromModelToDto(model.getSolicitud()),
                CategoriaMapper.getInstance().fromModelToDto(model.getCategoria()),
                model.getNombre(),
                model.getPrecio(),
                model.getDuracion());
    }
}