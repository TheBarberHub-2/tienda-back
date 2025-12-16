package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.SesionEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SesionJpaEntity;

public class SesionMapper {

    private static SesionMapper INSTANCE;

    private SesionMapper() {
    }

    public SesionMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SesionMapper();
        }
        return INSTANCE;
    }

    public SesionJpaEntity fromEntityToJpa(SesionEntity sesionEntity) {
        if (sesionEntity == null) {
            return null;
        }
        return new SesionJpaEntity(
                sesionEntity.id(),
                UsuarioMapper.getInstance().fromEntityToJpa(sesionEntity.usuario()),
                sesionEntity.token(),
                sesionEntity.expiredDate());
    }

    public SesionEntity fromJpaToEntity(SesionJpaEntity sesionJpaEntity) {
        if (sesionJpaEntity == null) {
            return null;
        }
        return new SesionEntity(
                sesionJpaEntity.getId(),
                UsuarioMapper.getInstance().fromJpaToEntity(sesionJpaEntity.getUsuario()),
                sesionJpaEntity.getToken(),
                sesionJpaEntity.getExpiredDate());
    }
}
