package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaHorarioJpaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaJpaEntity;

public class PeluqueriaHorarioMapper {

    private static PeluqueriaHorarioMapper INSTANCE;

    private PeluqueriaHorarioMapper() {
    }

    public static PeluqueriaHorarioMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeluqueriaHorarioMapper();
        }
        return INSTANCE;
    }

    public PeluqueriaHorarioJpaEntity fromEntityToJpa(PeluqueriaHorarioEntity peluqueriaHorarioEntity) {
        if (peluqueriaHorarioEntity == null) {
            return null;
        }

        PeluqueriaEntity peluqueriaEntity = peluqueriaHorarioEntity.peluqueria();
        PeluqueriaJpaEntity peluqueriaJpa = null;
        if (peluqueriaEntity != null) {
            peluqueriaJpa = new PeluqueriaJpaEntity(
                    peluqueriaEntity.id(),
                    UsuarioMapper.getInstance().fromEntityToJpa(peluqueriaEntity.usuario()),
                    peluqueriaEntity.municipio(),
                    peluqueriaEntity.direccion(),
                    peluqueriaEntity.telefono(),
                    peluqueriaEntity.iban(),
                    null,
                    null);
        }

        return new PeluqueriaHorarioJpaEntity(
                peluqueriaHorarioEntity.id(),
                peluqueriaJpa,
                peluqueriaHorarioEntity.diaSemana(),
                peluqueriaHorarioEntity.horaApertura(),
                peluqueriaHorarioEntity.horaCierre());
    }

    public PeluqueriaHorarioEntity fromJpaToEntity(PeluqueriaHorarioJpaEntity peluqueriaHorarioJpaEntity) {
        if (peluqueriaHorarioJpaEntity == null) {
            return null;
        }

        PeluqueriaJpaEntity peluqueriaJpa = peluqueriaHorarioJpaEntity.getPeluqueria();
        PeluqueriaEntity peluqueriaEntity = null;
        if (peluqueriaJpa != null) {
            peluqueriaEntity = new PeluqueriaEntity(
                    peluqueriaJpa.getId(),
                    UsuarioMapper.getInstance().fromJpaToEntity(peluqueriaJpa.getUsuario()),
                    peluqueriaJpa.getMunicipio(),
                    peluqueriaJpa.getDireccion(),
                    peluqueriaJpa.getTelefono(),
                    peluqueriaJpa.getIban(),
                    null,
                    null);
        }

        return new PeluqueriaHorarioEntity(
                peluqueriaHorarioJpaEntity.getId(),
                peluqueriaEntity,
                peluqueriaHorarioJpaEntity.getDiaSemana(),
                peluqueriaHorarioJpaEntity.getHoraApertura(),
                peluqueriaHorarioJpaEntity.getHoraCierre());
    }
}
