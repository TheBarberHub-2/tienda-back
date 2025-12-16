package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Sesion;
import com.fpmislata.daw.tienda.domain.repository.entity.SesionEntity;
import com.fpmislata.daw.tienda.domain.service.dto.SesionDto;

public class SesionMapper {

    private static SesionMapper INSTANCE;

    private SesionMapper() {
    }

    public static SesionMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SesionMapper();
        }
        return INSTANCE;
    }

    public SesionEntity fromModelToEntity(Sesion sesion) {
        if (sesion == null) {
            return null;
        }
        return new SesionEntity(
                sesion.getId(),
                UsuarioMapper.getInstance().fromModelToEntity(sesion.getUsuario()),
                sesion.getToken(),
                sesion.getExpiredDate());
    }

    public Sesion fromEntityToModel(SesionEntity sesionEntity) {
        if (sesionEntity == null) {
            return null;
        }
        return new Sesion(
                sesionEntity.id(),
                UsuarioMapper.getInstance().fromEntityToModel(sesionEntity.usuario()),
                sesionEntity.token(),
                sesionEntity.expiredDate());
    }

    public Sesion fromDtoToModel(SesionDto sesionDto) {
        if (sesionDto == null) {
            return null;
        }
        return new Sesion(
                sesionDto.id(),
                UsuarioMapper.getInstance().fromDtoToModel(sesionDto.usuario()),
                sesionDto.token(),
                sesionDto.expiredDate());
    }

    public SesionDto fromModelToDto(Sesion sesion) {
        if (sesion == null) {
            return null;
        }
        return new SesionDto(
                sesion.getId(),
                UsuarioMapper.getInstance().fromModelToDto(sesion.getUsuario()),
                sesion.getToken(),
                sesion.getExpiredDate());
    }
}
